package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.controller.ClientController;

/**
 * Event that signals the end of the game
 */
public class GameEndedEvent extends Event {
    public GameEndedEvent() {
        eventType = Events_Enum.GAME_ENDED;
    }

    @Override
    public void handle(Object clientController) {
        ((ClientController) clientController).gameEnded();
    }
}
