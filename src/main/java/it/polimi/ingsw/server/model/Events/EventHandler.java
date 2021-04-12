package it.polimi.ingsw.server.model.Events;

/**
 * Interface to be implemented if we want to register to the {@link EventBroker} object. Permits to handle events
 */
public interface EventHandler {
    /**
     * in here the events are handled
     *
     * @param event event to be handled
     */
    default void handleEvent(Event event){
        event.handle(this);
    }
}
