package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

public class ActivateProductionEvent extends Event {
    private final Production production;

    public ActivateProductionEvent(Production production) {
        eventType = Events_Enum.ACTIVATE_PRODUCTION;
        this.production = production;
    }

    @Override
    public void handle(Object player) {
        //TODO: add javadoc, test
        ((HumanPlayer)player).activateProduction();
    }
}
