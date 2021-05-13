package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.common.viewEvents.PrintObjects_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

// TODO add javadoc
public class ClientController extends Thread implements EventHandler {
    private boolean waitingForResponse = true;
    private boolean playing = false;
    private boolean gameRunning = false;
    private final ClientMessageBroker clientMessageBroker;
    private final UserInterface userInterface;
    private final EventBroker eventBroker;
    private final Object lockPlaying = new Object();

    public ClientController(UserInterface userInterface, EventBroker eventBroker, Socket socket) {
        this.clientMessageBroker = new ClientMessageBroker(this, eventBroker, userInterface, socket);
        this.userInterface = userInterface;
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

    @Override
    public void run() {
        synchronized (this) {
            while (!gameRunning) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        while (gameRunning) {
            synchronized (this) {
                chooseOptions();
                while (waitingForResponse) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // TODO javadoc
    private void chooseOptions() {
        List<PlayerRequest> eventList = new ArrayList<>(Arrays.asList(PrintObjects_Enum.values()));

        if (playing) {
            eventList.addAll(Arrays.asList(PlayerActionOptions.values()));
        }

        int option = userInterface.makePlayerChoose(
                new MakePlayerChoose<>(eventList)
        );

        try {
            clientMessageBroker.sendEvent(eventList.get(option).getRelativeEvent(userInterface));
            waitingForResponse = true;
        } catch (IllegalArgumentException e) {
            userInterface.printMessage("Action aborted");
        }
    }

    // TODO javadoc
    public synchronized void notifyActionDone(String message) {
        waitingForResponse = false;
        notify();
        userInterface.printMessage(message);
    }

    // TODO javadoc
    public void startTurn() {
        synchronized (lockPlaying) {
            userInterface.printMessage("\nYOUR TURN STARTED!\n");
            while (playing) {
                try {
                    lockPlaying.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            playing = true;
        }
    }

    // TODO javadoc
    public void endTurn() {
        synchronized (lockPlaying) {
            playing = false;
            userInterface.printMessage("\nYOUR TURN ENDED!\n");
            lockPlaying.notifyAll();
        }
    }

    // TODO javadoc
    public synchronized void gameStarted() {
        gameRunning = true;
        userInterface.printMessage("\nGAME STARTED!\n");
        notifyAll();
    }

    // TODO javadoc
    public void gameEnded() {
        gameRunning = false;
        userInterface.printMessage("\nGAME ENDED!\n");
    }
}

// TODO continue developing, add javadoc
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
