package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;

/**
 * Event that signals the starting of the game
 */
public class GameStartedEvent extends Event {
    public GameStartedEvent() {
        eventType = Events_Enum.GAME_STARTED;
    }

    @Override
    public void handle(Object clientController) {
        ((ClientController) clientController).gameStarted();
    }
}
