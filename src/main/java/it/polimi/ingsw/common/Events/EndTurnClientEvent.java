package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event that signals the starting of the turn of a player
 */
public class EndTurnClientEvent extends Event {

    public EndTurnClientEvent() {
        eventType = Events_Enum.END_TURN_CLIENT;
    }

    @Override
    public void handle(Object clientController) {
        // TODO develop better (control if player did a main action)
        ((ClientController)clientController).endTurn();
    }
}
