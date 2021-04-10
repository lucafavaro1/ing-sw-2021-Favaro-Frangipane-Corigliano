package it.polimi.ingsw.Events;

import it.polimi.ingsw.ActionCards.ActionCardDeck;

/**
 * Event used to signal to the action card deck that has to be shuffled (used in single player mode only)
 */
public class ShuffleActionEvent extends Event {

    public ShuffleActionEvent() {
        eventType = Events_Enum.SHUFFLE_ACTION;
    }

    @Override
    public void handle(Object actionCardDeck) {
        ((ActionCardDeck) actionCardDeck).shuffle();
    }
}