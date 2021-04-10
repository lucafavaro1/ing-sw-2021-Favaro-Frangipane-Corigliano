package it.polimi.ingsw.Events;

import it.polimi.ingsw.Development.DcBoard;
import it.polimi.ingsw.Development.TypeDevCards_Enum;

/**
 * Event that discards two cards from the DcBoard (used in single player mode only)
 */
public class DiscardTwoCardsEvent extends Event {
    private final TypeDevCards_Enum type;

    public DiscardTwoCardsEvent(TypeDevCards_Enum type) {
        eventType = Events_Enum.DISCARD_TWO;
        this.type = type;
    }

    @Override
    public void handle(Object dcBoard) {
        ((DcBoard) dcBoard).discardTwo(type);
    }
}