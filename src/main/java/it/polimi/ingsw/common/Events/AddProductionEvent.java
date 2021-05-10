package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

/**
 * Event that signals that the player wants to add a production to the productions to do
 */
public class AddProductionEvent extends Event {
    private final Production production;

    public AddProductionEvent(Production production) {
        eventType = Events_Enum.ADD_PRODUCTION;
        this.production = production;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = ((HumanPlayer) playerObj);

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You already did a main action in this round!"));
            return;
        }

        player.addProduction(production);
    }
}
