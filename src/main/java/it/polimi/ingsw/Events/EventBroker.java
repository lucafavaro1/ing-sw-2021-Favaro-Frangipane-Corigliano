package it.polimi.ingsw.Events;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that dispatches all the events generated to the different subscribers
 */
public class EventBroker {

    /**
     * A thread pool in order to execute the non-blocking handles of events
     */
    ExecutorService threadPool = Executors.newFixedThreadPool(5);

    /**
     * Map variable that memorizes all the subscribers to a particular event
     */
    private final Map<Events_Enum, ArrayList<EventHandler>> subscribers = new HashMap<>();

    /**
     * Subscribes the event handler passed to all the events in the passed set of Events
     *
     * @param eventHandler the object to be subscribed
     * @param events       the set of Events that the object will be notified on
     */
    public void subscribe(EventHandler eventHandler, EnumSet<Events_Enum> events) {
        if (!events.isEmpty()) {
            // creates the list for the events not present in the subscribers' map
            events.forEach(event -> subscribers.putIfAbsent(event, new ArrayList<>()));

            // adds the event handler to the list of the events it wants to subscribe to
            events.stream()
                    .filter(event -> !subscribers.get(event).contains(eventHandler))
                    .forEach(event -> subscribers.get(event).add(eventHandler));
        }
    }

    /**
     * Unsubscribes the event handler passed from all the events in the passed set of Events
     *
     * @param eventHandler the object to be unsubscribed
     * @param events       the set of Events on which the object will be unsubscribed from
     */
    public void unsubscribe(EventHandler eventHandler, EnumSet<Events_Enum> events) {
        if (!events.isEmpty()) {
            events.forEach(event -> subscribers.get(event).remove(eventHandler));
        }
    }

    /**
     * Posts an event; all the eventHandlers registered for this event will be notified.
     * There are two modes:
     * - blocking: when you have to wait till the event is completely handled
     * - non-blocking: when it's not necessary that you have to wait the complete handle of the event
     *
     * @param event    event to post to the EventBroker
     * @param blocking post an event in blocking or non-blocking mode
     */
    public synchronized void post(Event event, boolean blocking) {
        if (blocking) {
            Optional.ofNullable(subscribers.get(event.getEventType()))
                    .ifPresent(listEventHandlers ->
                            listEventHandlers.forEach(eventHandler -> eventHandler.handleEvent(event)));
        } else {
            // delegates the posting to another thread (Dispatcher)
            threadPool.execute(new Dispatcher(event, subscribers.get(event.getEventType())));
        }
    }

    /**
     * Posts an event to a specific eventHandler;
     * There are two modes:
     * - blocking: when you have to wait till the event is completely handled
     * - non-blocking: when it's not necessary that you have to wait the complete handle of the event
     *
     * @param eventHandler specific eventHandler that will be notified
     * @param event        event to post to the EventBroker
     * @param blocking     post an event in blocking or non-blocking mode
     */
    public synchronized void post(EventHandler eventHandler, Event event, boolean blocking) {
        if (blocking) {
            eventHandler.handleEvent(event);
        } else {
            // delegates the posting to another thread (Dispatcher)
            threadPool.execute(new Dispatcher(event, List.of(eventHandler)));
        }
    }

    /**
     * Posts an event to all the event handlers except for the one passed as parameter;
     * There are two modes:
     * - blocking: when you have to wait till the event is completely handled
     * - non-blocking: when it's not necessary that you have to wait the complete handle of the event
     *
     * @param eventHandlerNotHandle specific eventHandler that won't be notified
     * @param event        event to post to the EventBroker
     * @param blocking     post an event in blocking or non-blocking mode
     */
    public synchronized void postAllButMe(EventHandler eventHandlerNotHandle, Event event, boolean blocking) {
        if (blocking) {
            Optional.ofNullable(subscribers.get(event.getEventType()))
                    .ifPresent(listEventHandlers ->
                            listEventHandlers.stream()
                                    .filter(eventHandler -> !eventHandler.equals(eventHandlerNotHandle))
                                    .forEach(eventHandler -> eventHandler.handleEvent(event)));
        } else {
            List<EventHandler> eventHandlersToNotify = new ArrayList<>(subscribers.get(event.getEventType()));
            eventHandlersToNotify.remove(eventHandlerNotHandle);
            // delegates the posting to another thread (Dispatcher)
            threadPool.execute(new Dispatcher(event, eventHandlersToNotify));
        }
    }

    /**
     * Used for testing purposes
     *
     * @return the map of all subscribers
     */
    public Map<Events_Enum, ArrayList<EventHandler>> getSubscribers() {
        return subscribers;
    }
}

/**
 * runnable class that executes the dispatching of the event
 */
class Dispatcher implements Runnable {
    Event eventToHandle;
    List<EventHandler> eventHandlers;

    public Dispatcher(Event eventToHandle, List<EventHandler> eventHandlers) {
        this.eventToHandle = eventToHandle;
        this.eventHandlers = eventHandlers;
    }

    @Override
    public void run() {
        Optional.ofNullable(eventHandlers)
                .ifPresent(listEventHandlers ->
                        listEventHandlers.forEach(eventHandler -> eventHandler.handleEvent(eventToHandle)));
    }
}