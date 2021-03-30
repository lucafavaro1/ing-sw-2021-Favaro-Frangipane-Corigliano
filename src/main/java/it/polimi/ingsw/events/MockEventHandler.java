package it.polimi.ingsw.events;

import java.util.ArrayList;

/**
 * MockEventHandler, used for testing purposes
 */
public class MockEventHandler implements EventHandler {
    String message;

    ArrayList<Events> eventsHandled = new ArrayList<>();

    /**
     * constructor of the MockEventHandler
     *
     * @param message a unique message in order to identify different eventHandlers
     */
    public MockEventHandler(String message) {
        this.message = message;
    }

    /**
     * handles the event passed to the method
     *
     * @param event event to be handled
     */
    @Override
    public void handleEvent(Events event) {
        event.handle();
        eventsHandled.add(event);
    }

    public ArrayList<Events> getEventsHandled() {
        return eventsHandled;
    }
}
