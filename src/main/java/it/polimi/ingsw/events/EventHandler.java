package it.polimi.ingsw.events;

/**
 * Interface to be implemented if we want to register to the {@link EventBroker} object. Permits to handle events
 */
public interface EventHandler {
    void handleEvent(Events event);
}
