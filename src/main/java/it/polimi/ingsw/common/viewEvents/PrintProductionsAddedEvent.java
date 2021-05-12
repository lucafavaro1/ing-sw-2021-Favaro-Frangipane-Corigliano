package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

import java.util.List;

public class PrintProductionsAddedEvent extends PrintEvent<List<Production>> {
    public PrintProductionsAddedEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.PRODUCTIONS_ADDED;
        toPrint = player.getProductionsAdded();
        /*toPrint = "YOU HAVE ADDED THE FOLLOWING PRODUCTIONS: \n";

        for (Production p : player.getProductionsAdded())
            toPrint = toPrint.concat(p.toString() + "\n");*/
    }
}
