package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

import java.util.List;

// TODO add javadoc
public class PrintProductionsAvailableEvent extends PrintEvent<List<Production>> {
    public PrintProductionsAvailableEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.PRODUCTIONS_AVAILABLE;
        toPrint = player.getAvailableProductions();
        /*toPrint = "The available productions are:\n";

        for (Production p : player.getAvailableProductions())
            toPrint = toPrint.concat(p.toString() + "\n");*/
    }
}
