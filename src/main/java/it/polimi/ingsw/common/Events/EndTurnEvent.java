package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event that signals the starting of the turn of a player
 */
public class EndTurnEvent extends Event {

    public EndTurnEvent() {
        eventType = Events_Enum.END_TURN;
    }

    @Override
    public void handle(Object playerObj) {
        // TODO develop better (control if player did a main action)
        ((HumanPlayer)playerObj).endTurn();
    }
}
