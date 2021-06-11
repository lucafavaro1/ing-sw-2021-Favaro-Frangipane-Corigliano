package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.PlayerRequest;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.net.Socket;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Thread that deals with the proceeding of the game and waits for the player to do an action
 */
public abstract class ClientController extends Thread implements EventHandler {
    protected boolean waitingForResponse = false;
    protected boolean playing = false;
    protected boolean gameRunning = false;
    protected boolean gamePrepared = false;
    protected final ClientMessageBroker clientMessageBroker;
    protected final UserInterface userInterface;
    protected final EventBroker eventBroker;
    protected final Object lockPlaying = new Object();

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
     * Start Game event send to every player at the begin of the game
     * This is also used to change view from waiting for players scene to the punchboard
     */
    public abstract void gameStarted();

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
    
    /**
     * Method that get called when the preparation phase of the game has ended
     */
    public synchronized void endPreparation() {
        gamePrepared = true;
        notifyAll();
        System.out.println("PREPARATION ENDED!");
    }

    /**
     * Start Turn event send to every player at the begin of its turn
     */
    public abstract void startTurn();

    /**
     * End Turn event send to every player at the end of its turn
     */
    public abstract void endTurn();

    public ClientMessageBroker getClientMessageBroker() {
        return clientMessageBroker;
    }

    public EventBroker getEventBroker() {
        return eventBroker;
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
