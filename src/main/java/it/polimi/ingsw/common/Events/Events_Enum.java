package it.polimi.ingsw.common.Events;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

/**
 * Enum of all the events used in the game
 */
public enum Events_Enum {
    TEST1(null), TEST2(null),
    FAIL(FailEvent.class),
    // Stampare informazioni ricevute dal controller
    PRINT_MESSAGE(null),
    // Player
    VATICAN_REPORT(VaticanReportEvent.class),
    LAST_ROUND(LastRoundEvent.class),
    ADD_FAITH(AddFaithEvent.class),
    // Human Player
    START_TURN(StartTurnEvent.class),
    BUY_DEV_CARD(BuyDevCardEvent.class),
    GET_MARKET_RES(GetMarketResEvent.class),
    ADD_PRODUCTION(AddProductionEvent.class),
    DELETE_PRODUCTION(DeleteProductionEvent.class),
    ACTIVATE_PRODUCTION(ActivateProductionEvent.class),
    END_TURN(EndTurnEvent.class),
    // CPU Player
    SHUFFLE_ACTION(ShuffleActionEvent.class),
    DISCARD_TWO(DiscardTwoCardsEvent.class),
    PLUS_FAITH_CARD(PlusFaithCardEvent.class);

    public Class<?> getEventClass() {
        return equivalentClass;
    }

    private final Class<?> equivalentClass;

    Events_Enum(Class<?> equivalentClass) {
        this.equivalentClass = equivalentClass;
    }


}
