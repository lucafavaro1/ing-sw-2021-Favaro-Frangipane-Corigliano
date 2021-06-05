package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.GetPrintEvent;
import it.polimi.ingsw.common.viewEvents.PrintEvent;

/**
 * Enum of all the events used in the game
 * TODO: add leader events
 */
public enum Events_Enum {
    TEST1(null), TEST2(null),
    FAIL(FailEvent.class),
    ACTION_DONE(ActionDoneEvent.class),
    PING(PingEvent.class),
    // Stampare informazioni ricevute dal controller
    PRINT_MESSAGE(PrintEvent.class),
    GET_PRINT(GetPrintEvent.class),
    RANKING(NotifyRankingEvent.class),
    // Player
    VATICAN_REPORT(VaticanReportEvent.class),
    LAST_ROUND(LastRoundEvent.class),
    ADD_FAITH(AddFaithEvent.class),
    PREPARATION_ENDED(PreparationEndedEvent.class),
    // Human Player
    FIRST_PLAYER(FirstPlayerEvent.class),
    GAME_STARTED(GameStartedEvent.class),
    START_TURN(StartTurnEvent.class),
    ACTIVATE_LEADER(ActivateLeaderEvent.class),
    DISCARD_LEADER(DiscardLeaderEvent.class),
    BUY_DEV_CARD(BuyDevCardEvent.class),
    GET_MARKET_RES(GetMarketResEvent.class),
    ADD_PRODUCTION(AddProductionEvent.class),
    DELETE_PRODUCTION(DeleteProductionEvent.class),
    ACTIVATE_PRODUCTION(ActivateProductionEvent.class),
    END_TURN(EndTurnEvent.class),
    END_TURN_CLIENT(EndTurnClientEvent.class),
    GAME_ENDED(GameEndedEvent.class),
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
