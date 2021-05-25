package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.ActionCards.ActionCardDeck;
import it.polimi.ingsw.server.model.ActionCards.Effect;

import java.util.Collections;
import java.util.List;

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
        List<ActionCard> actionCards = ((ActionCardDeck) actionCardDeck).getDeck();

        ActionCard shuffleCard = null;
        for (ActionCard card : actionCards) {
            if (card.getEffect() == Effect.PLUS_ONE_FAITH_SHUFFLE) {
                shuffleCard = card;
            }
        }

        Collections.swap(actionCards, actionCards.size() - 1, actionCards.indexOf(shuffleCard));
    }
}
