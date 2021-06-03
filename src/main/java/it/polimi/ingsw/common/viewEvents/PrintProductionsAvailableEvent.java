package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

import java.util.List;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the available productions
 */
public class PrintProductionsAvailableEvent extends PrintEvent<List<Production>> {
    public PrintProductionsAvailableEvent(HumanPlayer player) {
        super(player.getNickname(), player.getAvailableProductions());
        printType = PrintObjects_Enum.PRODUCTIONS_AVAILABLE;
    }
}
