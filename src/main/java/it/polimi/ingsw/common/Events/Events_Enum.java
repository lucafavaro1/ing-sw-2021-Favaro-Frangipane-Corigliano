package it.polimi.ingsw.common.Events;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * Enum of all the events used in the game
 */
public enum Events_Enum {
    TEST1(null), TEST2(null),
    // Player
    VATICAN_REPORT(VaticanReportEvent.class),
    LAST_ROUND(LastRoundEvent.class),
    ADD_FAITH(AddFaithEvent.class),
    // Human Player
    BUY_DEV_CARD(BuyDevCardEvent.class),
    GET_MARKET_RES(GetMarketResEvent.class),
    ADD_PRODUCTION(AddProductionEvent.class),
    DELETE_PRODUCTION(DeleteProductionEvent.class),
    ACTIVATE_PRODUCTION(ActivateProductionEvent.class),
    // CPU Player
    SHUFFLE_ACTION(ShuffleActionEvent.class),
    DISCARD_TWO(DiscardTwoCardsEvent.class),
    PLUS_FAITH_CARD(PlusFaithCardEvent.class);

    public Class getEventClass() {
        return equivalentClass;
    }

    private final Class equivalentClass;

    Events_Enum(Class equivalentResource) {
        this.equivalentClass = equivalentResource;
    }

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

        // parsing the event
        Event event = (Event) gson.fromJson(jsonEvent, eventType.getEventClass());
        System.out.println(event);

        return event;
    }
}
