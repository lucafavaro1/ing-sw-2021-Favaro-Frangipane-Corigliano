package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.networkCommunication.Pingable;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Ping Event used to check is player is still connected to the game
 */
public class PingEvent extends Event {
    /**
     * Basic constructor
     */
    public PingEvent() {
        eventType = Events_Enum.PING;
    }

    @Override
    public void handle(Object pingable) {
        ((Pingable) pingable).ping();
    }
}
