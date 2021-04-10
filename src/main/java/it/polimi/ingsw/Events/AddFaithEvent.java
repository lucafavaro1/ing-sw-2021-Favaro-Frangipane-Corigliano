package it.polimi.ingsw.Events;

import it.polimi.ingsw.Player.FaithTrack;

/**
 * used to signal that we want to add an amount of faith points to the faith track
 */
public class AddFaithEvent extends Event {
    private final int faith;

    public AddFaithEvent(int faith) {
        eventType = Events_Enum.ADD_FAITH;
        this.faith = faith;
    }

    @Override
    public void handle(Object faithTrack) {
        ((FaithTrack) faithTrack).increasePos(faith);
    }
}
