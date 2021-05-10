package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

public class PrintProductionsAddedEvent extends PrintEvent {
    public PrintProductionsAddedEvent(HumanPlayer player) {
        textMessage = "YOU HAVE ADDED THE FOLLOWING PRODUCTIONS: \n";

        for (Production p : player.getProductionsAdded())
            textMessage = textMessage.concat(p.toString() + "\n");
    }
}
