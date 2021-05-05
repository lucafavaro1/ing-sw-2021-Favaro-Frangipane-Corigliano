package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.EventBroker;

public class ClientController {
    public EventBroker getEventBroker() {
        return eventBroker;
    }

    private final EventBroker eventBroker = new EventBroker();

}
