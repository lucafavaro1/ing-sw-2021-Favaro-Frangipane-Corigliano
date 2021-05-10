package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

// TODO add javadoc
public class PrintProductionsAvailableEvent extends PrintEvent {
    public PrintProductionsAvailableEvent(HumanPlayer player) {
        textMessage = "The available productions are:\n";

        for (Production p : player.getAvailableProductions())
            textMessage = textMessage.concat(p.toString() + "\n");
    }
}
