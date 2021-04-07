package it.polimi.ingsw.Events;

/**
 * Interface to be implemented if we want to register to the {@link EventBroker} object. Permits to handle events
 */
public interface EventHandler {
    /**
     * in here the logic for the handling of the events is implemented
     *
     * @param event event to be handled
     */
    void handleEvent(Events_Enum event);
}
