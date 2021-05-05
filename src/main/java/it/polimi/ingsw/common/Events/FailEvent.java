package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;

/**
 * Event used to signal the client that an error occurred processing something on the server
 */
public class FailEvent extends Event {
    private final String message;

    public FailEvent(String message) {
        eventType = Events_Enum.FAIL;
        this.message = message;
    }

    @Override
    public void handle(Object userInterface) {
        ((UserInterface) userInterface).printMessage(message);
    }
}
