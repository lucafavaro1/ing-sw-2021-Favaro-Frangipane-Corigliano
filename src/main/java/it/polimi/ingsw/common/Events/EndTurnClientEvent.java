package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event that signals the ending of the turn to the client
 */
public class EndTurnClientEvent extends Event {

    public EndTurnClientEvent() {
        eventType = Events_Enum.END_TURN_CLIENT;
    }

    @Override
    public void handle(Object clientController) {
        ((ClientController)clientController).endTurn();
        ((ClientController)clientController).getEventBroker().post(new ActionDoneEvent(""), true);
    }
}
