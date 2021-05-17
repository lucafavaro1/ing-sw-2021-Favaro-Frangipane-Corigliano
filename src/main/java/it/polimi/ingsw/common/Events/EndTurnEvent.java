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

        HumanPlayer player = (HumanPlayer) playerObj;
        if (player.isActionDone())
            player.endTurn();
        else
            player.getGameClientHandler().sendEvent(new FailEvent("You haven't done a main action yet, please do one before ending your turn"));
    }

    @Override
    public String toString() {
        return "End turn";
    }
}
