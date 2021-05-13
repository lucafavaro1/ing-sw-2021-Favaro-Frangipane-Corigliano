package it.polimi.ingsw.common.Events;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import it.polimi.ingsw.common.viewEvents.PrintEvent;

import java.lang.reflect.Type;

/**
 * Abstract class that implements the Events used in the program
 */
public abstract class Event {
    protected Events_Enum eventType;

    /**
     * In this method we put the logic to handle the event
     *
     * @param target object on which the handle has to be applied
     */
    public abstract void handle(Object target);

    /**
     * Converts an Event serialized in JSON to the relative Event subclass
     * TODO test
     *
     * @param jsonEvent the string that represents the Event serialized
     * @return an event subclass
     */
    public static Event getEventFromJson(String jsonEvent) {
        Gson gson = new Gson();

        // getting the event type
        Events_Enum eventType = gson.fromJson(
                JsonParser.parseString(jsonEvent).getAsJsonObject().get("eventType"),
                Events_Enum.class
        );

        // if it's a print message then use the printEvent parser
        if (eventType == Events_Enum.PRINT_MESSAGE) {
            return PrintEvent.getEventFromJson(jsonEvent);
        }

        // parsing the event
        return gson.fromJson(jsonEvent, (Type) eventType.getEventClass());
    }

    // TODO add javadoc
    public String getJsonFromEvent() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public Events_Enum getEventType() {
        return eventType;
    }
}
