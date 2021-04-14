package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

public class AddProductionEvent extends Event {
    private final Production production;

    public AddProductionEvent(Production production) {
        eventType = Events_Enum.ADD_PRODUCTION;
        this.production = production;
    }

    @Override
    public void handle(Object player) {
        //TODO: add javadoc, test
        ((HumanPlayer)player).addProduction(production);
    }
}
