package it.polimi.ingsw.common.Events;

import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EventBrokerTest {

    /**
     * Testing if the subscribe method subscribes the eventHandler to the right event
     */
    @Test
    public void OneSubscribeOnceOneEvent() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler");

        //subscribing the eventHandler to the Event Events.TEST1
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // asserting that there must be only one event with registration
        assertEquals(1, eventBroker.getSubscribers().size());

        ArrayList<EventHandler> eventHandlers = eventBroker.getSubscribers().get(Events_Enum.TEST1);

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
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating TWO eventHandler object
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        //subscribing the eventHandler to the Event Events.TEST1
        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST1));

        // asserting that there must be only one event with registrations
        assertEquals(1, eventBroker.getSubscribers().size());

        ArrayList<EventHandler> eventHandlers = eventBroker.getSubscribers().get(Events_Enum.TEST1);

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
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating One eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");

        //subscribing the eventHandler to TWO Events: Events.TEST1, Events.TEST2
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1, Events_Enum.TEST2));

        // asserting that there must be TWO events with registrations
        assertEquals(2, eventBroker.getSubscribers().size());

        Map<Events_Enum, ArrayList<EventHandler>> mapEventHandlers = eventBroker.getSubscribers();

        // asserting that there must be one eventHandlers in the list for each EVENT subscribed to
        assertEquals(1, mapEventHandlers.get(Events_Enum.TEST1).size());
        assertEquals(1, mapEventHandlers.get(Events_Enum.TEST2).size());

        // asserting that the eventHandlers subscribed must be present in the ArrayList of subscribers to that Event
        assertTrue(mapEventHandlers.get(Events_Enum.TEST1).contains(eventHandler));
        assertTrue(mapEventHandlers.get(Events_Enum.TEST2).contains(eventHandler));
    }

    /**
     * Testing if the subscribe method doesn't subscribes the eventHandler to the event if he is already subscribed to it
     */
    @Test
    public void OneSubscribeTwiceOneEvent() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating One eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");

        //subscribing the eventHandler to One Event TWICE
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // asserting that there must be TWO events with registrations
        assertEquals(1, eventBroker.getSubscribers().size());


        Map<Events_Enum, ArrayList<EventHandler>> mapEventHandlers = eventBroker.getSubscribers();

        // asserting that there must be one eventHandlers in the list
        assertEquals(1, mapEventHandlers.get(Events_Enum.TEST1).size());

        // asserting that the eventHandlers subscribed must be present in the ArrayList of subscribers to that Event
        assertTrue(mapEventHandlers.get(Events_Enum.TEST1).contains(eventHandler));
    }

    /**
     * testing if two different brokers interfere with each other
     */
    @Test
    public void twoBrokersDifferentSubscribers() {
        // getting the instance of TWO the event broker
        EventBroker eventBroker1 = new EventBroker();
        EventBroker eventBroker2 = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");

        // asserting that the instances of the subscribers map should be different
        assertNotSame(eventBroker1.getSubscribers(), eventBroker2.getSubscribers());

        // subscribing to the two different eventHandlers
        eventBroker1.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));
        eventBroker2.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // asserting that the two maps should be different
        assertNotSame(eventBroker1.getSubscribers(), eventBroker2.getSubscribers());

        // asserting that the two maps should contain the eventHandler of before
        assertTrue(eventBroker1.getSubscribers().get(Events_Enum.TEST1).contains(eventHandler));
        assertTrue(eventBroker2.getSubscribers().get(Events_Enum.TEST1).contains(eventHandler));

        // subscribing to another event the event eventHandler on eventBroker1
        eventBroker1.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST2));

        // asserting that the two maps shouldn't be equal anymore
        assertNotEquals(eventBroker1.getSubscribers(), eventBroker2.getSubscribers());
    }

    /**
     * testing the unsubscribe method
     */
    @Test
    public void unsubscribeFromEvents() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler");

        //subscribing the eventHandler to the Events TEST1 and TEST2
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1, Events_Enum.TEST2));

        // asserting that there must be two events with registration
        assertEquals(2, eventBroker.getSubscribers().size());

        assertEquals(List.of(eventHandler), eventBroker.getSubscribers().get(Events_Enum.TEST1));
        assertEquals(List.of(eventHandler), eventBroker.getSubscribers().get(Events_Enum.TEST2));

        // unsubscribe to the event TEST1
        eventBroker.unsubscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        //asserting that the eventHandler have to be subscribed only to the event TEST2
        assertEquals(List.of(), eventBroker.getSubscribers().get(Events_Enum.TEST1));
        assertEquals(List.of(eventHandler), eventBroker.getSubscribers().get(Events_Enum.TEST2));
    }

    /**
     * Test to try posting an event with no subscribers
     */
    @Test
    public void postNonBlockingNoSubscribes() {
        // getting the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        eventBroker.post(new Test1Event(), false);
    }

    /**
     * Test to try posting an event with no subscribers, verifying that the others eventHandlers aren't notified of it
     */
    @Test
    public void postNonBlockingEventNoSubscribes() {
        // getting the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(new Test1Event(), false);

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(0, eventHandler.getEventsHandled().size());
    }

    /**
     * Test to try posting an event with one subscriber
     */
    @Test
    public void postNonBlockingEventOneSubscriber() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // posting the event the subscriber subscribed for
        eventBroker.post(new Test1Event(), false);

        // putthing a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(1, eventHandler.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events_Enum.TEST1), eventHandler.getEventsHandled());
    }

    /**
     * Test to try posting an event with one subscriber
     */
    @Test
    public void postNonBlockingEventTwoSubscribers() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST1));

        assertEquals(2, eventBroker.getSubscribers().get(Events_Enum.TEST1).size());

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(new Test1Event(), false);

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
        assertEquals(List.of(Events_Enum.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events_Enum.TEST1), eventHandler2.getEventsHandled());
    }

    /**
     * Test to try posting more events with more subscribers
     */
    @Test
    public void postTwoNonBlockingEventsTwoSubscribers() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST2));

        assertEquals(List.of(eventHandler1), eventBroker.getSubscribers().get(Events_Enum.TEST1));
        assertEquals(List.of(eventHandler2), eventBroker.getSubscribers().get(Events_Enum.TEST2));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(new Test1Event(), false);
        eventBroker.post(new Test2Event(), false);

        // putting a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(List.of(Events_Enum.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events_Enum.TEST2), eventHandler2.getEventsHandled());
    }

    /**
     * Test to try posting an event with no subscribers (waiting till the complete handle)
     */
    @Test
    public void postBlockingNoSubscribes() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        eventBroker.post(new Test1Event(), true);
    }

    /**
     * Test to try posting an event with no subscribers, verifying that the others eventHandlers aren't notified of it
     * (waiting till the complete handle)
     */
    @Test
    public void postBlockingEventNoSubscribes() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(new Test2Event(), true);

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(0, eventHandler.getEventsHandled().size());
    }

    /**
     * Test to try posting an event with one subscriber (waiting till the complete handle)
     */
    @Test
    public void postBlockingEventOneSubscriber() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating an eventHandler object
        MockEventHandler eventHandler = new MockEventHandler("eventHandler1");
        eventBroker.subscribe(eventHandler, EnumSet.of(Events_Enum.TEST1));

        // posting the event the subscriber subscribed for
        eventBroker.post(new Test1Event(), true);

        // asserting that the eventHandler shouldn't be notified of the event he didn't subscribed on
        assertEquals(1, eventHandler.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events_Enum.TEST1), eventHandler.getEventsHandled());
    }

    /**
     * Test to try posting an event with one subscriber (waiting till the complete handle)
     */
    @Test
    public void postBlockingEventTwoSubscribers() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST1));

        assertEquals(2, eventBroker.getSubscribers().get(Events_Enum.TEST1).size());

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(new Test1Event(), true);

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(1, eventHandler1.getEventsHandled().size());
        assertEquals(1, eventHandler2.getEventsHandled().size());

        // asserting that the eventHandler should have handled exactly the event posted
        assertEquals(List.of(Events_Enum.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events_Enum.TEST1), eventHandler2.getEventsHandled());
    }

    /**
     * Test to try posting more events with more subscribers (waiting till the complete handle)
     */
    @Test
    public void postTwoBlockingEventsTwoSubscribers() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST2));

        assertEquals(List.of(eventHandler1), eventBroker.getSubscribers().get(Events_Enum.TEST1));
        assertEquals(List.of(eventHandler2), eventBroker.getSubscribers().get(Events_Enum.TEST2));

        // posting a different event from the one subscribed by the eventHandler
        eventBroker.post(new Test1Event(), true);
        eventBroker.post(new Test2Event(), true);

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(List.of(Events_Enum.TEST1), eventHandler1.getEventsHandled());
        assertEquals(List.of(Events_Enum.TEST2), eventHandler2.getEventsHandled());
    }

    /**
     * testing the posting the event to a specific eventHandler
     */
    @Test
    public void postDirectTest() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));

        assertEquals(List.of(eventHandler1), eventBroker.getSubscribers().get(Events_Enum.TEST1));

        // posting the event to a specific eventHandler
        eventBroker.post(eventHandler2, new Test1Event(), true);

        // asserting that the eventHandlers should have been notified of the events posted
        assertEquals(List.of(Events_Enum.TEST1), eventHandler2.getEventsHandled());
        assertEquals(List.of(), eventHandler1.getEventsHandled());
    }

    /**
     * testing to post an event for all but the eventHandler passed (blocking)
     */
    @Test
    public void postAllButMeBlockingTest() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");
        MockEventHandler eventHandler3 = new MockEventHandler("eventHandler3");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler3, EnumSet.of(Events_Enum.TEST1));

        assertEquals(List.of(eventHandler1, eventHandler2, eventHandler3),
                eventBroker.getSubscribers().get(Events_Enum.TEST1));

        // posting the event  event from the one subscribed by the eventHandler
        eventBroker.postAllButMe(eventHandler1, new Test1Event(), true);

        // asserting that the eventHandler passed shouldn't have been notified
        assertEquals(List.of(), eventHandler1.getEventsHandled());

        // asserting that the other eventHandlers should have been notified
        assertEquals(List.of(Events_Enum.TEST1), eventHandler2.getEventsHandled());
        assertEquals(List.of(Events_Enum.TEST1), eventHandler3.getEventsHandled());
    }

    /**
     * testing to post an event for all but the eventHandler passed (blocking)
     */
    @Test
    public void postAllButMeNonBlockingTest() {
        // creating the instance of the event broker
        EventBroker eventBroker = new EventBroker();

        // instantiating two eventHandler objects
        MockEventHandler eventHandler1 = new MockEventHandler("eventHandler1");
        MockEventHandler eventHandler2 = new MockEventHandler("eventHandler2");
        MockEventHandler eventHandler3 = new MockEventHandler("eventHandler3");

        eventBroker.subscribe(eventHandler1, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler2, EnumSet.of(Events_Enum.TEST1));
        eventBroker.subscribe(eventHandler3, EnumSet.of(Events_Enum.TEST1));

        assertEquals(List.of(eventHandler1, eventHandler2, eventHandler3),
                eventBroker.getSubscribers().get(Events_Enum.TEST1));

        // posting the event  event from the one subscribed by the eventHandler
        eventBroker.postAllButMe(eventHandler1, new Test1Event(), false);

        // putting a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // asserting that the eventHandler passed shouldn't have been notified
        assertEquals(List.of(), eventHandler1.getEventsHandled());

        // asserting that the other eventHandlers should have been notified
        assertEquals(List.of(Events_Enum.TEST1), eventHandler2.getEventsHandled());
        assertEquals(List.of(Events_Enum.TEST1), eventHandler3.getEventsHandled());
    }
}

/**
 * MockEventHandler, used for testing purposes
 */
class MockEventHandler implements EventHandler {
    String message;

    ArrayList<Events_Enum> eventsHandled = new ArrayList<>();

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
    public void handleEvent(Event event) {
        eventsHandled.add(event.getEventType());
    }

    public ArrayList<Events_Enum> getEventsHandled() {
        return eventsHandled;
    }
}
