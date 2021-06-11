package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.controller.ClientController;

/**
 * Event that signals the starting of the turn of a player
 */
public class ActionDoneEvent extends Event {
    private final String message;
    public ActionDoneEvent(String message) {
        eventType = Events_Enum.ACTION_DONE;
        this.message = message;
    }

    @Override
    public void handle(Object clientController) {
        ((ClientController) clientController).notifyActionDone(message);
    }
}
