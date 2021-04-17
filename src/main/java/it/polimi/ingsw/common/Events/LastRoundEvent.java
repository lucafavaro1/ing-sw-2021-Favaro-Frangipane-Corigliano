package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Game;

/**
 * Event that signals the beginning of the last round
 */
public class LastRoundEvent extends Event {

    public LastRoundEvent() {
        eventType = Events_Enum.LAST_ROUND;
    }

    @Override
    public void handle(Object game) {
        ((Game) game).setLastRound(true);
    }
}
