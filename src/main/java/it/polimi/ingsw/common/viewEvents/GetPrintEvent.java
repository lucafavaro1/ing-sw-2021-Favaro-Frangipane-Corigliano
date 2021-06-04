package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent from the client to the server to request the update of the relative piece of model
 */
public class GetPrintEvent extends Event {
    private final PrintObjects_Enum printEvent;

    public GetPrintEvent(PrintObjects_Enum printEvent) {
        eventType = Events_Enum.GET_PRINT;
        this.printEvent = printEvent;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = ((HumanPlayer) playerObj);
        player.getGameClientHandler().sendEvent(printEvent.getRelativePrintEvent(player));
    }
}

