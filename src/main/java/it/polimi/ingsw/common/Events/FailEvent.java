package it.polimi.ingsw.common.Events;

/**
 * Event used to signal that we want to add an amount of faith points to the faith track
 */
public class FailEvent extends Event {
    private final String message;

    public FailEvent(String message) {
        eventType = Events_Enum.FAIL;
        this.message = message;
    }

    // TODO: change printing method
    @Override
    public void handle(Object out) {
        System.out.println(message);
    }
}
