package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.common.viewEvents.PrintObjects_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * thread that deals with the proceeding of the game and waits for the player to do an action
 */
public class ClientController extends Thread implements EventHandler {
    private boolean waitingForResponse = false;
    private boolean playing = false;
    private boolean gameRunning = false;

    public ClientMessageBroker getClientMessageBroker() {
        return clientMessageBroker;
    }

    private final ClientMessageBroker clientMessageBroker;
    private final UserInterface userInterface;
    private final EventBroker eventBroker;
    private final Object lockPlaying = new Object();

    public ClientController(EventBroker eventBroker, Socket socket) {
        this.clientMessageBroker = new ClientMessageBroker(eventBroker, socket);
        this.userInterface = UserInterface.getInstance();
        this.eventBroker = eventBroker;

        eventBroker.subscribe(this, EnumSet.of(
                Events_Enum.GAME_STARTED, Events_Enum.GAME_ENDED,
                Events_Enum.START_TURN, Events_Enum.END_TURN_CLIENT,
                Events_Enum.ACTION_DONE
        ));

        clientMessageBroker.start();
    }

    public EventBroker getEventBroker() {
        return eventBroker;
    }

    /**
     * main method that deals with the player rounds and the actions he wants to do
     */
    @Override
    public void run() {
        // waiting for the beginning of the game
        if (UserInterface.getInstance().getClass() == CLIUserInterface.class) {

            synchronized (this) {
                while (!gameRunning) {
                    try {
                        System.out.println("Waiting for game to start");
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // main cycle in which the player chooses the action he wants to do and waits for a response of the server
        if (UserInterface.getInstance().getClass() == CLIUserInterface.class) {
            while (gameRunning) {
                synchronized (this) {
                    System.out.println("Choosing option");
                    chooseOptions();
                    System.out.println("option chosen");
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

    // TODO javadoc
    public synchronized void notifyActionDone(String message) {
        waitingForResponse = false;
        notify();

        if (message != null && !message.isBlank()) {
            userInterface.printMessage(message);
        }
    }

    // TODO javadoc
    public void startTurn() {
        synchronized (lockPlaying) {
            playing = true; // added here
            userInterface.printMessage("\nYOUR TURN STARTED!\n");
            if(userInterface.getClass() == GUIUserInterface.class) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Label x = (Label) Controller.getPersonalpunchboard().lookup("#yourTurn");
                        Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                        x.setOpacity(1);
                        y.setOpacity(1);
                    }
                });
            }
        }
    }

    // TODO javadoc
    public void endTurn() {
        synchronized (lockPlaying) {
            playing = false;
            userInterface.printMessage("\nYOUR TURN ENDED!\n");
            if(userInterface.getClass() == GUIUserInterface.class) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Label x = (Label) Controller.getPersonalpunchboard().lookup("#yourTurn");
                        Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                        x.setOpacity(0);
                        y.setOpacity(0);
                    }
                });
            }
        }
    }

    // TODO javadoc
    public synchronized void gameStarted() {
        gameRunning = true;
        userInterface.printMessage("\nGAME STARTED!\n");
        if(userInterface.getClass() == GUIUserInterface.class) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Controller.getPrimarystage().setMaxHeight(788);
                    Controller.getPrimarystage().setMaxWidth(1005);
                    Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
                    Controller.getPrimarystage().show();
                }
            });
        }
        notifyAll();
    }

    // TODO javadoc
    public void gameEnded() {
        gameRunning = false;
        userInterface.printMessage("\nGAME ENDED!\n");
        //@TODO: fare schermata gui anche per la fine partita con classifica
    }

    /**
     * method that makes the player do an action
     */
    private void chooseOptions() {
        List<PlayerRequest> eventList = new ArrayList<>(Arrays.asList(PrintObjects_Enum.values()));

        eventList.addAll(Arrays.asList(PlayerActionOptions.values()));

        int option = userInterface.makePlayerChoose(
                new MakePlayerChoose<>(eventList)
        );

        if (!gameRunning) {
            return;
        }

        try {
            if (!playing && !Arrays.asList(PrintObjects_Enum.values()).contains(eventList.get(option))) {
                userInterface.printFailMessage("Can't do this action: it's not your turn!");
                return;
            }

            Event event = eventList.get(option).getRelativeEvent(userInterface);

            clientMessageBroker.sendEvent(event);
            if (event.getEventType() != Events_Enum.GET_PRINT) {
                System.out.println("WaitingForResponse: true");
                waitingForResponse = true;
            }

        } catch (IllegalArgumentException e) {
            userInterface.printMessage("Action aborted");
        }
    }
}

// TODO add javadoc
enum PlayerActionOptions implements PlayerRequest {
    ACTIVATE_LEADER("Activate a leader card") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new ActivateLeaderEvent();
        }
    },
    DISCARD_LEADER("Discard a leader card") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new DiscardLeaderEvent();
        }
    },
    GET_MARKET_RESOURCES("Take resources from the market") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new GetMarketResEvent(userInterface);
        }
    },
    BUY_DEV_CARD("buy a development card") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new BuyDevCardEvent(userInterface);
        }
    },
    ADD_PRODUCTION("add a production") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new AddProductionEvent();
        }
    },
    DELETE_PRODUCTION("delete an already added production") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new DeleteProductionEvent();
        }
    },
    ACTIVATE_PRODUCTION("activate the production") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new ActivateProductionEvent();
        }
    },
    END_TURN("End your turn") {
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new EndTurnEvent();
        }
    };

    private final String text;

    PlayerActionOptions(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
