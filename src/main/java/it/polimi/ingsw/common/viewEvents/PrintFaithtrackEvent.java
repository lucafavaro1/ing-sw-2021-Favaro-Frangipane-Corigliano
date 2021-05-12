package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.FailEvent;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the faithtrack of the player
 */

public class PrintFaithtrackEvent extends PrintEvent<FaithTrack> {

    public PrintFaithtrackEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.FAITH_TRACK;
        toPrint = player.getFaithTrack();
    }

}
