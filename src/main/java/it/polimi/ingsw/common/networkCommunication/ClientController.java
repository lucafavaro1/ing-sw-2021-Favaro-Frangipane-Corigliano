package it.polimi.ingsw.common.networkCommunication;

import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.io.IOException;

public class ClientController {
    public EventBroker getEventBroker() {
        return eventBroker;
    }

    private final EventBroker eventBroker = new EventBroker();

}
