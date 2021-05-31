package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;

public class PreparationEndedEvent extends Event {

    public PreparationEndedEvent() {
        eventType = Events_Enum.PREPARATION_ENDED;
    }

    @Override
    public void handle(Object clientController) {
        ((ClientController)clientController).endPreparation();
    }
}
