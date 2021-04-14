package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

public class DeleteProductionEvent extends Event {
    private final Production production;

    public DeleteProductionEvent(Production production) {
        eventType = Events_Enum.DELETE_PRODUCTION;
        this.production = production;
    }

    @Override
    public void handle(Object player) {
        //TODO: add javadoc, test
        ((HumanPlayer) player).deleteProduction(production);
    }
}
