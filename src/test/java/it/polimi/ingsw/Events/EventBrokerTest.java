package it.polimi.ingsw.Events;

import it.polimi.ingsw.MockGame;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class EventBrokerTest {

    /**
     * Testing if resetInstances() clears all the previous definitions
     */
    @Test
    public void resetInstancesTest() {
        EventBrokerSingleton.resetInstances();

        // asserting that there shouldn't be any instance
        assertEquals(Set.of(), EventBrokerSingleton.getInstances().keySet());

        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // asserting that there is a key in the map
        assertEquals(Set.of(game), EventBrokerSingleton.getInstances().keySet());

        // asserting that the key contains a non null value
        assertNotNull(EventBrokerSingleton.getInstances().get(game));

        EventBrokerSingleton.resetInstances();
        assertTrue(EventBrokerSingleton.getInstances().keySet().isEmpty());
    }

    /**
     * Testing if removeInstance() deletes the right instance
     */
    @Test
    public void removeInstanceTest() {
        EventBrokerSingleton.resetInstances();
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();

        EventBrokerSingleton eventBroker1 = EventBrokerSingleton.getInstance(game1);
        EventBrokerSingleton eventBroker2 = EventBrokerSingleton.getInstance(game2);

        // asserting that there have to be two instances
        assertEquals(2, EventBrokerSingleton.getInstances().keySet().size());

        // removing a valid instance
        EventBrokerSingleton.removeInstance(game1);

        // asserting that the only key present is the one not removed
        assertEquals(Set.of(game2), EventBrokerSingleton.getInstances().keySet());
    }

    /**
     * Testing if EventBroker returns the instance memorized
     */
    @Test
    public void getInstanceTestOnce() {
        EventBrokerSingleton.resetInstances();
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);
        assertSame(EventBrokerSingleton.getInstances().get(game), eventBroker);
    }

    /**
     * Testing if EventBroker is returning always the same instance
     */
    @Test
    public void getInstanceTestTwice() {
        EventBrokerSingleton.resetInstances();
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);
        EventBrokerSingleton eventBrokerBis = EventBrokerSingleton.getInstance(game);
        assertSame(eventBrokerBis, eventBroker);
    }

    /**
     * Testing if EventBroker is returning different instances for different Games
     */
    @Test
    public void getInstanceTestDifferents() {
        EventBrokerSingleton.resetInstances();
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();
        EventBrokerSingleton eventBroker1 = EventBrokerSingleton.getInstance(game1);
        EventBrokerSingleton eventBroker2 = EventBrokerSingleton.getInstance(game2);

        // asserts that there have to be two different instantiations of the EventBroker
        assertSame(2, EventBrokerSingleton.getInstances().keySet().size());

        // asserts that the wto instantiations must be different
        assertNotSame(eventBroker1, eventBroker2);
    }

    /**
     * Testing if EventBroker is returning different instances for different Games
     */
    @Test
    public void getInstanceTestDifferentTwice() {
        EventBrokerSingleton.resetInstances();
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();

        EventBrokerSingleton eventBroker1 = EventBrokerSingleton.getInstance(game1);
        EventBrokerSingleton eventBroker2 = EventBrokerSingleton.getInstance(game2);

        // repeats the getInstance in order to get again the two instances
        EventBrokerSingleton eventBroker1Bis = EventBrokerSingleton.getInstance(game1);
        EventBrokerSingleton eventBroker2Bis = EventBrokerSingleton.getInstance(game2);

        // asserts that there have to be two different instantiations of the EventBroker
        assertSame(2, EventBrokerSingleton.getInstances().keySet().size());

        // asserts that the two instantiations must be returned at the second getInstance() call
        assertSame(eventBroker1, eventBroker1Bis);
        assertSame(eventBroker2, eventBroker2Bis);

        // asserts that the two instantiations must be different
        assertNotSame(eventBroker1Bis, eventBroker2Bis);
    }

    /**
     * Testing if the subscribe method subscribes the eventHandler to the right event
     */
    @Test
    public void OneSubscribeOnceOneEvent() {
        EventBrokerSingleton.resetInstances();

        // getting  (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler");

        //subscribing the eventHandler to the Event Events.TEST1
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // asserting that there must be only one event with registration
        assertEquals(1, eventBroker.getSubscribers().size());

        ArrayList<EventHandler> eventHandlers = eventBroker.getSubscribers().get(Events.TEST1);

        // asserting that there must be only this eventHandler in the list and must be the same of the eventHandler instanciated
        assertEquals(1, eventHandlers.size());
        assertEquals(eventHandler, eventHandlers.get(0));
    }

    /**
     * Testing if the subscribe method subscribes the eventHandler to the right event even if already another EventHandler
     * is already subscribed to it
     */
    @Test
    public void TwoSubscribeOnceOneEvent() {
        EventBrokerSingleton.resetInstances();

        // getting  (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating TWO eventHandler object
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        //subscribing the eventHandler to the Event Events.TEST1
        eventBroker.subscribe(eventHandler1, EnumSet.of(Events.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events.TEST1));

        // asserting that there must be only one event with registrations
        assertEquals(1, eventBroker.getSubscribers().size());

        ArrayList<EventHandler> eventHandlers = eventBroker.getSubscribers().get(Events.TEST1);

        // asserting that there must be TWO eventHandlers in the list
        assertEquals(2, eventHandlers.size());

        // asserting that the eventHandlers subscribed must be present in the ArrayList of subscribers to that Event
        assertTrue(eventHandlers.contains(eventHandler1));
        assertTrue(eventHandlers.contains(eventHandler2));
    }

    /**
     * Testing if the subscribe method subscribes the eventHandler to more than one EVENT
     */
    @Test
    public void OneSubscribeOnceTwoEvent() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating One eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");

        //subscribing the eventHandler to TWO Events: Events.TEST1, Events.TEST2
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1, Events.TEST2));

        // asserting that there must be TWO events with registrations
        assertEquals(2, eventBroker.getSubscribers().size());


        Map<Events, ArrayList<EventHandler>> mapEventHandlers = eventBroker.getSubscribers();

        // asserting that there must be one eventHandlers in the list for each EVENT subscribed to
        assertEquals(1, mapEventHandlers.get(Events.TEST1).size());
        assertEquals(1, mapEventHandlers.get(Events.TEST2).size());

        // asserting that the eventHandlers subscribed must be present in the ArrayList of subscribers to that Event
        assertTrue(mapEventHandlers.get(Events.TEST1).contains(eventHandler));
        assertTrue(mapEventHandlers.get(Events.TEST2).contains(eventHandler));
    }

    /**
     * Testing if the subscribe method doesn't subscribes the eventHandler to the event if he is already subscribed to it
     */
    @Test
    public void OneSubscribeTwiceOneEvent() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating One eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");

        //subscribing the eventHandler to One Event TWICE
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // asserting that there must be TWO events with registrations
        assertEquals(1, eventBroker.getSubscribers().size());


        Map<Events, ArrayList<EventHandler>> mapEventHandlers = eventBroker.getSubscribers();

        // asserting that there must be one eventHandlers in the list
        assertEquals(1, mapEventHandlers.get(Events.TEST1).size());

        // asserting that the eventHandlers subscribed must be present in the ArrayList of subscribers to that Event
        assertTrue(mapEventHandlers.get(Events.TEST1).contains(eventHandler));
    }

    @Test
    public void twoBrokersDifferentSubscribers() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) TWO instances of the event broker
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();

        EventBrokerSingleton eventBroker1 = EventBrokerSingleton.getInstance(game1);
        EventBrokerSingleton eventBroker2 = EventBrokerSingleton.getInstance(game2);

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");

        // asserting that the instances of the subscribers map should be different
        assertNotSame(eventBroker1.getSubscribers(), eventBroker2.getSubscribers());

        // subscribing to the two different eventHandlers
        eventBroker1.subscribe(eventHandler, EnumSet.of(Events.TEST1));
        eventBroker2.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // asserting that the two maps should be different
        assertNotSame(eventBroker1.getSubscribers(), eventBroker2.getSubscribers());

        // asserting that the two maps should contain the eventHandler of before
        assertTrue(eventBroker1.getSubscribers().get(Events.TEST1).contains(eventHandler));
        assertTrue(eventBroker2.getSubscribers().get(Events.TEST1).contains(eventHandler));

        // subscribing to another event the event eventHandler on eventBroker1
        eventBroker1.subscribe(eventHandler, EnumSet.of(Events.TEST2));

        // asserting that the two maps shouldn't be equal anymore
        assertNotEquals(eventBroker1.getSubscribers(), eventBroker2.getSubscribers());
    }

    /**
     * Test to try posting an event with no subscribers
     */
    @Test
    public void postNonBlockingNoSubscribes() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        eventBroker.post(Events.TEST1, false);
    }

    /**
     * Test to try posting an event with no subscribers, verifying that the others eventHandlers aren't notified of it
     */
    @Test
    public void postNonBlockingEventNoSubscribes() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(Events.TEST2, false);

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(0, eventHandler.getEventsHandled().size());
    }

    /**
     * Test to try posting an event with one subscriber
     */
    @Test
    public void postNonBlockingEventOneSubscriber() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // posting the event the subscriber subscribed for
        eventBroker.post(Events.TEST1, false);

        // putthing a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(1, eventHandler.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events.TEST1), eventHandler.getEventsHandled());
    }

    /**
     * Test to try posting an event with one subscriber
     */
    @Test
    public void postNonBlockingEventTwoSubscribers() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events.TEST1));

        assertEquals(2, eventBroker.getSubscribers().get(Events.TEST1).size());

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(Events.TEST1, false);

        // putthing a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(1, eventHandler1.getEventsHandled().size());
        assertEquals(1, eventHandler2.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events.TEST1), eventHandler2.getEventsHandled());
    }

    /**
     * Test to try posting more events with more subscribers
     */
    @Test
    public void postTwoNonBlockingEventsTwoSubscribers() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events.TEST2));

        assertEquals(List.of(eventHandler1), eventBroker.getSubscribers().get(Events.TEST1));
        assertEquals(List.of(eventHandler2), eventBroker.getSubscribers().get(Events.TEST2));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(Events.TEST1, false);
        eventBroker.post(Events.TEST2, false);

        // putthing a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(List.of(Events.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events.TEST2), eventHandler2.getEventsHandled());
    }

    /**
     * Test to try posting an event with no subscribers (waiting till the complete handle)
     */
    @Test
    public void postBlockingNoSubscribes() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        eventBroker.post(Events.TEST1, true);
    }

    /**
     * Test to try posting an event with no subscribers, verifying that the others eventHandlers aren't notified of it
     * (waiting till the complete handle)
     */
    @Test
    public void postBlockingEventNoSubscribes() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(Events.TEST2, true);

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(0, eventHandler.getEventsHandled().size());
    }

    /**
     * Test to try posting an event with one subscriber (waiting till the complete handle)
     */
    @Test
    public void postBlockingEventOneSubscriber() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events.TEST1));

        // posting the event the subscriber subscribed for
        eventBroker.post(Events.TEST1, true);

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(1, eventHandler.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events.TEST1), eventHandler.getEventsHandled());
    }

    /**
     * Test to try posting an event with one subscriber (waiting till the complete handle)
     */
    @Test
    public void postBlockingEventTwoSubscribers() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events.TEST1));

        assertEquals(2, eventBroker.getSubscribers().get(Events.TEST1).size());

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(Events.TEST1, true);

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(1, eventHandler1.getEventsHandled().size());
        assertEquals(1, eventHandler2.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events.TEST1), eventHandler2.getEventsHandled());
    }

    /**
     * Test to try posting more events with more subscribers (waiting till the complete handle)
     */
    @Test
    public void postTwoBlockingEventsTwoSubscribers() {
        EventBrokerSingleton.resetInstances();

        // getting (creating) the instance of the event broker
        MockGame game = new MockGame();
        EventBrokerSingleton eventBroker = EventBrokerSingleton.getInstance(game);

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events.TEST2));

        assertEquals(List.of(eventHandler1), eventBroker.getSubscribers().get(Events.TEST1));
        assertEquals(List.of(eventHandler2), eventBroker.getSubscribers().get(Events.TEST2));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(Events.TEST1, true);
        eventBroker.post(Events.TEST2, true);

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(List.of(Events.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events.TEST2), eventHandler2.getEventsHandled());
    }

    /**
     * testing to pass the caller
     */
    @Test
    public void callerMemorization() {

    }

}


/**
 * MockEventHandler, used for testing purposes
 */
class MockEventHandler implements EventHandler {
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
