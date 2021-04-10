package it.polimi.ingsw.Events;

/**
 * abstract class that implements the Events used in the program
 */
public abstract class Event {
    protected Events_Enum eventType;

    public Events_Enum getEventType() {
        return eventType;
    }

    /**
     * In this method we put the logic to handle the event
     * @param target object on which the handle has to be applied
     */
    public abstract void handle(Object target);
}
