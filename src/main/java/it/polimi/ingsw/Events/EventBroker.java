package it.polimi.ingsw.Events;


import it.polimi.ingsw.MockGame;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Class that dispatches all the events generated to the different subscribers
 */
public class EventBroker {

    /**
     * Static map that memorizes all the instances of the eventBroker for every game played
     * key = Game, value = eventBroker associated to that Game
     * TODO: change MockGame to the real Game once it is available
     */
    static private Map<MockGame, EventBroker> eventBrokerInstances = new HashMap<>();

    /**
     * Method to get the single instance possible from the EventBroker. if there isn't an instance for the eventBroker,
     * a new one is created and runned
     * TODO: change MockGame to the real Game once it is available
     *
     * @return the instance of the EventBroker
     */
    public static EventBroker getInstance(MockGame game) {
        if (!eventBrokerInstances.containsKey(game)) {
            eventBrokerInstances.put(game, new EventBroker());
        }

        return eventBrokerInstances.get(game);
    }

    /**
     * A thread pool in order to execute non-blocking handles of events
     */
    ExecutorService threadPool = Executors.newFixedThreadPool(5);

    /**
     * Map variable that memorizes all the subscribers to a parcticular event
     */
    private Map<Events, ArrayList<EventHandler>> subscribers = new HashMap<>();

    /**
     * Private constructor in order to simulate the Singleton Pattern.
     * Called only by the {@link #getInstance(MockGame)} method
     * TODO: change MockGame to the real Game once it is available
     */
    private EventBroker() {

    }

    /**
     * Method used only for testing purposes, used to delete all the instances of EventBroker
     */
    protected static void resetInstances() {
        eventBrokerInstances.clear();
    }

    /**
     * Subscribes the event handler passed to all the events in the passed set of Events
     *
     * @param eventHandler the object to be subscribed
     * @param events       the set of Events that the object will be notified on
     */
    public void subscribe(EventHandler eventHandler, EnumSet<Events> events) {
        if (!events.isEmpty()) {
            // crea l'arrayList per gli eventi che non sono ancora stati inseriti nella mappa subscribers
            events.forEach(event -> subscribers.putIfAbsent(event, new ArrayList<>()));

            // aggiunge l'eventHandler all'arrayList
            events.stream()
                    .filter(event -> !subscribers.get(event).contains(eventHandler))
                    .forEach(event -> subscribers.get(event).add(eventHandler));
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
    public synchronized void post(Events event, boolean blocking) {
        // delegates the posting to another thread (Dispatcher)
        if (blocking) {
            Optional.ofNullable(subscribers.get(event))
                    .ifPresent(listEventHandlers ->
                            listEventHandlers.forEach(eventHandler -> eventHandler.handleEvent(event)));
        } else {
            threadPool.execute(new Dispatcher(event, subscribers.get(event)));
        }
    }

    /**
     * Used for testing purposes
     * TODO: change MockGame to the real Game once it is available
     *
     * @return map that associates to every game its EventBroker
     */
    public static Map<MockGame, EventBroker> getEventBrokerInstances() {
        return eventBrokerInstances;
    }

    /**
     * Used for testing purposes
     *
     * @return the map of all subscribers
     */
    public Map<Events, ArrayList<EventHandler>> getSubscribers() {
        return subscribers;
    }
}

/**
 * runnable class that executes the dispatching of the event
 */
class Dispatcher implements Runnable {
    Events eventToHandle;
    ArrayList<EventHandler> eventHandlers;

    public Dispatcher(Events eventToHandle, ArrayList<EventHandler> eventHandlers) {
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