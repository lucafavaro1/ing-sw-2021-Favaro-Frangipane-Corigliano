package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;

/**
 * Event that signals the end of the game
 */
public class GameEndedEvent extends Event {
    public GameEndedEvent() {
        eventType = Events_Enum.GAME_ENDED;
    }

    @Override
    public void handle(Object clientController) {
        System.out.println("[GameEnded] handled");
        ((ClientController) clientController).gameEnded();
    }
}
