package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.controller.ClientController;

/**
 * Event that signals the starting of the turn of a player
 */
public class StartTurnEvent extends Event {

    public StartTurnEvent() {
        eventType = Events_Enum.START_TURN;
    }

    @Override
    public void handle(Object clientController) {
        ((ClientController) clientController).startTurn();
    }
}
