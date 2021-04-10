package it.polimi.ingsw.ActionCards;

import it.polimi.ingsw.Development.TypeDevCards_Enum;
import it.polimi.ingsw.Events.DiscardTwoCardsEvent;
import it.polimi.ingsw.Events.PlusFaithCardEvent;
import it.polimi.ingsw.Events.ShuffleActionEvent;
import it.polimi.ingsw.Game;

/**
 * Enumeration that models the different effects of the action cards
 */
public enum Effect {
    DISCARD_TWO_CARDS {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new DiscardTwoCardsEvent(devCard), false);
        }
    },
    PLUS_TWO_FAITH {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(2), false);
        }
    },
    PLUS_ONE_FAITH_SHUFFLE {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(1), false);
            game.getEventBroker().post(new ShuffleActionEvent(), false);
        }
    };

    public abstract void applyEffect(Game game, TypeDevCards_Enum devCard);
}
