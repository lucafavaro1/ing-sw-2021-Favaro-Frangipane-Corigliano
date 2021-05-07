package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.common.viewEvents.PrintObjects_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

// TODO add javadoc
public class ClientController extends Thread implements EventHandler {
    private boolean playing = false;
    private boolean gameEnded = false;
    private final ClientMessageBroker clientMessageBroker;
    private final UserInterface userInterface;
    private final EventBroker eventBroker;

    public ClientController(ClientMessageBroker clientMessageBroker, UserInterface userInterface, EventBroker eventBroker) {
        this.clientMessageBroker = clientMessageBroker;
        this.userInterface = userInterface;
        this.eventBroker = eventBroker;

        eventBroker.subscribe(this, EnumSet.of(Events_Enum.START_TURN, Events_Enum.END_TURN_CLIENT));
    }

    public EventBroker getEventBroker() {
        return eventBroker;
    }

    @Override
    public void run() {
        while (!gameEnded) {
            chooseOptions();
        }
    }

    private void chooseOptions() {
        List<PlayerRequest> eventList = new ArrayList<>(Arrays.asList(PrintObjects_Enum.values()));
        System.out.println(playing);
        if (playing) {
            eventList.addAll(Arrays.asList(PlayerActionOptions.values()));
        }

        System.out.println("[CLIENT] choose among: " + eventList);
        int option = userInterface.makePlayerChoose(
                new MakePlayerChoose<>("Choose what do you want to do", eventList)
        );

        clientMessageBroker.sendEvent(eventList.get(option).getRelativeEvent(userInterface));
    }

    public void startTurn() {
        playing = true;
        userInterface.printMessage("\nYOUR TURN STARTED!\n");
    }

    public void endTurn() {
        playing = false;
        userInterface.printMessage("\nYOUR TURN ENDED!\n");
    }
}

// TODO continue developing, add javadoc
enum PlayerActionOptions implements PlayerRequest {
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
    }/*,
    ADD_PRODUCTION("add a production"){
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new AddProductionEvent(userInterface);
        }
    },
    ACTIVATE_PRODUCTION("add a production"){
        @Override
        public Event getRelativeEvent(UserInterface userInterface) {
            return new ActivateProductionEvent(userInterface);
        }
    }
    */,
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
