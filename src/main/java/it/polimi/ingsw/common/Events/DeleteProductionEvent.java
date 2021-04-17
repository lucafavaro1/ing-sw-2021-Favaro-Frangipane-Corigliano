package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

/**
 * Event that deletes a production added in precedence
 * TODO: test
 */
public class DeleteProductionEvent extends Event {
    private final Production production;

    public DeleteProductionEvent(Production production) {
        eventType = Events_Enum.DELETE_PRODUCTION;
        this.production = production;
    }

    @Override
    public void handle(Object player) {
        ((HumanPlayer) player).deleteProduction(production);
    }
}
