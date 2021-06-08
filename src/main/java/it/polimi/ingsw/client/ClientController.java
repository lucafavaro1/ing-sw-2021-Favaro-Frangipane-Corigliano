package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Thread that deals with the proceeding of the game and waits for the player to do an action
 */
public class ClientController extends Thread implements EventHandler {
    private boolean waitingForResponse = false;
    private boolean playing = false;
    private boolean gameRunning = false;
    private boolean gamePrepared = false;

    public ClientMessageBroker getClientMessageBroker() {
        return clientMessageBroker;
    }

    private final ClientMessageBroker clientMessageBroker;
    private final UserInterface userInterface;
    private final EventBroker eventBroker;
    private final Object lockPlaying = new Object();

    /**
     * Basic constructor that links the clientcontroller with the eventbroker and the client socket
     *
     * @param eventBroker the eventbroker
     * @param socket      client socket
     */
    public ClientController(EventBroker eventBroker, Socket socket) {
        this.clientMessageBroker = new ClientMessageBroker(eventBroker, socket);
        this.userInterface = UserInterface.getInstance();
        this.eventBroker = eventBroker;

        eventBroker.subscribe(this, EnumSet.of(
                Events_Enum.GAME_STARTED, Events_Enum.GAME_ENDED,
                Events_Enum.START_TURN, Events_Enum.END_TURN_CLIENT,
                Events_Enum.ACTION_DONE, Events_Enum.PREPARATION_ENDED
        ));

        (new Thread(clientMessageBroker)).start();
    }

    public EventBroker getEventBroker() {
        return eventBroker;
    }

    /**
     * Main method that deals with the player rounds and the actions he wants to do
     */
    @Override
    public void run() {
        // waiting for the beginning of the game
        if (UserInterface.getInstance().getClass() == CLIUserInterface.class) {
            synchronized (this) {
                while (!gameRunning) {
                    try {
                        System.out.println("Waiting for match to begin...");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                while (!gamePrepared) {
                    try {
                        System.out.println("Waiting for initial preparation...");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            // main cycle in which the player chooses the action he wants to do and waits for a response of the server
            while (gameRunning) {
                synchronized (this) {
                    chooseOptions();
                    while (waitingForResponse) {
                        System.out.println("WaitingForResponse (still true)");
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method used to notify the server that the player has done his action
     *
     * @param message the message field
     */
    public synchronized void notifyActionDone(String message) {
        waitingForResponse = false;
        notify();

        if (message != null && !message.isBlank()) {
            userInterface.printMessage(message);
        }
    }

    /**
     * Start Turn event send to every player at the begin of its turn
     */
    public void startTurn() {
        synchronized (lockPlaying) {
            playing = true; // added here
            userInterface.printMessage("\nIT'S YOUR TURN!\n");
            if (userInterface.getClass() == GUIUserInterface.class) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                        y.setOpacity(1);
                    }
                });
            }
        }
    }

    /**
     * End Turn event send to every player at the end of its turn
     */
    public void endTurn() {
        synchronized (lockPlaying) {
            playing = false;
            userInterface.printMessage("\nYOUR TURN ENDED!\n");
            if (userInterface.getClass() == GUIUserInterface.class) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        VBox right = (VBox) Controller.getProductions().lookup("#activateProduction");
                        VBox left = (VBox) Controller.getProductions().lookup("#addProduction");
                        right.getChildren().clear();
                        left.getChildren().clear();
                        Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                        y.setOpacity(0);
                    }
                });
            }
        }
    }

    /**
     * Start Game event send to every player at the begin of the game
     * This is also used to change view from waiting for players scene to the punchboard
     */
    public synchronized void gameStarted() {
        gameRunning = true;
        userInterface.printMessage("\nGAME STARTED!\n");
        if (userInterface.getClass() == GUIUserInterface.class) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Controller.getPrimarystage().setMinHeight(788);
                    Controller.getPrimarystage().setMaxHeight(788);
                    Controller.getPrimarystage().setMinWidth(1005);
                    Controller.getPrimarystage().setMaxWidth(1005);
                    Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
                    Controller.getPrimarystage().show();
                }
            });
        }
        notifyAll();
    }

    /**
     * End Game event send to every player at the end of the game
     * This is also used to change view from punchboard to the end game scene with the ranking
     */
    public void gameEnded() {
        gameRunning = false;
        userInterface.printMessage("\nGAME ENDED!\n");
        clientMessageBroker.endGame();
        // handle nel notifyRankingEvent per la gui
    }

    public synchronized void endPreparation() {
        gamePrepared = true;
        notifyAll();
        System.out.println("PREPARATION ENDED!");
    }

    /**
     * Method that makes the player do an action
     */
    private void chooseOptions() {
        List<Object> eventList = new ArrayList<>(Arrays.asList(PlayerViewOptions.values()));
        eventList.addAll(Arrays.asList(PlayerActionOptions.values()));

        if (userInterface.getLastActionCard() == null)
            eventList.remove(PlayerViewOptions.ACTION_CARD);

        Object request = eventList.get(userInterface.makePlayerChoose(
                new MakePlayerChoose<>(
                        userInterface.getMyNickname() +
                                (playing ?
                                        " YOU ARE PLAYING!" + (userInterface.getMyPlayer() != null && userInterface.getMyPlayer().isActionDone() ? " [main action done]" : " ") :
                                        ""
                                ),
                        eventList
                )
        ));

        if (!gameRunning) {
            return;
        }

        if (Arrays.asList(PlayerViewOptions.values()).contains(request)) {
            ((PlayerViewOptions) request).view();
            return;
        }

        try {
            PlayerRequest action = (PlayerRequest) request;
            // checks that if the player choose an action, sends the event only if the player is playing
            if (!playing /* && !Arrays.asList(PrintObjects_Enum.values()).contains(action)*/) {
                userInterface.printFailMessage("Can't do this action: it's not your turn!");
                return;
            }

            Event event = action.getRelativeEvent(userInterface);

            clientMessageBroker.sendEvent(event);
            System.out.println("WaitingForResponse: true");
            waitingForResponse = true;

        } catch (IllegalArgumentException e) {
            userInterface.printFailMessage("Action aborted");
        }
    }
}

/**
 * Enumeration of possible view options offered to the player: Punchboard, Dc Board, Market Tray, Action Card
 */
enum PlayerViewOptions {
    PLAYER("View the Punchboard") {
        @Override
        public void view() {
            UserInterface userInterface = UserInterface.getInstance();
            List<String> nicks = new ArrayList<>(userInterface.getPlayers().keySet());
            nicks.add("Go back");

            int chosen = userInterface.makePlayerChoose(new MakePlayerChoose<>(userInterface.getMyNickname() + ", choose a player", nicks));
            if (chosen == nicks.size() - 1)
                return;

            String nickChosen = nicks.get(chosen);

            userInterface.printMessage(userInterface.getPlayers().get(nickChosen).toString());
        }
    },
    DC_BOARD("View the Development Cards Board") {
        @Override
        public void view() {
            UserInterface userInterface = UserInterface.getInstance();
            userInterface.printMessage(userInterface.getDcBoard().toString());
        }
    },
    MARKET_TRAY("View the Market Tray") {
        @Override
        public void view() {
            UserInterface userInterface = UserInterface.getInstance();
            userInterface.printMessage(userInterface.getMarketTray().toString());
        }
    },
    ACTION_CARD("View the last Action Card drawn by Lorenzo") {
        @Override
        public void view() {
            UserInterface userInterface = UserInterface.getInstance();
            userInterface.printMessage(userInterface.getLastActionCard().toString());
        }
    };


    private final String text;

    PlayerViewOptions(String text) {
        this.text = text;
    }

    public abstract void view();

    @Override
    public String toString() {
        return text;
    }
}

/**
 * Enumeration of possible actions that the player can do: Activate Leader, Discard Leader, Get resources from Market,
 * Buy a development card, Add a production, Delete a production, Activate a production, End your turn.
 */
enum PlayerActionOptions implements PlayerRequest {
    ACTIVATE_LEADER("Activate a Leader Card") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);
            return new ActivateLeaderEvent();
        }
    },
    DISCARD_LEADER("Discard a Leader Card") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);
            return new DiscardLeaderEvent();
        }
    },
    GET_MARKET_RESOURCES("Get resources from the Market") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);
            return new GetMarketResEvent(userInterface);
        }
    },
    BUY_DEV_CARD("Buy a Development Card") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);
            return new BuyDevCardEvent(userInterface);
        }
    },
    ADD_PRODUCTION("Add a Production") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);

            userInterface.printMessage("Available Resources for Productions:\n" +
                    userInterface.getMyPlayer().getAvailableResources().toString()
            );

            return new AddProductionEvent();
        }
    },
    DELETE_PRODUCTION("Remove an already added Production") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);
            return new DeleteProductionEvent();
        }
    },
    ACTIVATE_PRODUCTION("Activate a Production") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            printSituation(userInterface);
            return new ActivateProductionEvent();
        }
    },
    END_TURN("End your turn") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new EndTurnEvent();
        }
    };

    /**
     * Method to print the current global situation of a player
     *
     * @param userInterface the userinterface of which the player is running the game (cli or gui)
     */
    private static void printSituation(UserInterface userInterface) {
        userInterface.printMessage(userInterface.getMyPlayer());
    }

    private final String text;

    PlayerActionOptions(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
