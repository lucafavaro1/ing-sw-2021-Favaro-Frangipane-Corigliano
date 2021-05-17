package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;

/**
 * Event that signals the starting of the turn of a player
 */
public class FirstPlayerEvent extends Event {

    public FirstPlayerEvent() {
        eventType = Events_Enum.FIRST_PLAYER;
    }

    @Override
    public void handle(Object userInterface) {
        ((UserInterface) userInterface).setFirstPlayer();
        ((UserInterface) userInterface).printMessage("You are the first player!");
    }
}
