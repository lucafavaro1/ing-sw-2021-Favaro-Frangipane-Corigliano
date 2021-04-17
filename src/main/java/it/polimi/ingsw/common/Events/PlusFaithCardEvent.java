package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Player.FaithTrack;

/**
 * Event that signals that we have to add an amount of faith (used in single player mode only)
 */
public class PlusFaithCardEvent extends Event {

    private final int faith;

    public PlusFaithCardEvent(int faith) {
        eventType = Events_Enum.PLUS_FAITH_CARD;
        this.faith = faith;
    }

    @Override
    public void handle(Object faithTrack) {
        ((FaithTrack) faithTrack).increasePos(faith);
    }
}
