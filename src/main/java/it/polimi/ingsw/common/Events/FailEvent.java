package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;

/**
 * Event used to signal the client that an error occurred processing something on the server
 */
public class FailEvent extends Event {
    private final String message;

    /**
     * Constructor that specifies the message to be shown when an error occurs
     * @param message string to be displayed when an error occurs
     */
    public FailEvent(String message) {
        eventType = Events_Enum.FAIL;
        this.message = message;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);
        userInterface.printFailMessage(message);
        userInterface.getEventBroker().post(new ActionDoneEvent("Action aborted"), true);
    }
}
