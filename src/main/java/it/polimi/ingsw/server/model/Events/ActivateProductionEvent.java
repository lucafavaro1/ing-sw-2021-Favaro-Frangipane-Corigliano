package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event that signals the activation of the production
 */
public class ActivateProductionEvent extends Event {

    public ActivateProductionEvent() {
        eventType = Events_Enum.ACTIVATE_PRODUCTION;
    }

    @Override
    public void handle(Object player) {
        ((HumanPlayer) player).activateProduction();
    }
}
