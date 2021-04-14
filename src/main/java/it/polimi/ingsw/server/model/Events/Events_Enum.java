package it.polimi.ingsw.server.model.Events;

/**
 * Enum of all the events used in the game
 */
public enum Events_Enum {
    TEST1, TEST2,
    // Player
    VATICAN_REPORT,
    LAST_ROUND,
    ADD_FAITH,
    // Human Player
    BUY_DEV_CARD,
    GET_MARKET_RES,
    ADD_PRODUCTION,
    DELETE_PRODUCTION,
    ACTIVATE_PRODUCTION,
    // CPU Player
    SHUFFLE_ACTION,
    DISCARD_TWO,
    PLUS_FAITH_CARD
}
