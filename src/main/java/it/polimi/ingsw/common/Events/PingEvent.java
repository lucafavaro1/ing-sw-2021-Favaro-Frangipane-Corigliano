package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.networkCommunication.Pingable;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

public class PingEvent extends Event {
    public PingEvent() {
        eventType = Events_Enum.PING;
    }

    @Override
    public void handle(Object pingable) {
        ((Pingable) pingable).ping();
    }
}
