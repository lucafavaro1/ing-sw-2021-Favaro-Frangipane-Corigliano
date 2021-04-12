package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Events.DiscardTwoCardsEvent;
import it.polimi.ingsw.server.model.Events.PlusFaithCardEvent;
import it.polimi.ingsw.server.model.Events.ShuffleActionEvent;
import it.polimi.ingsw.server.model.Game;

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
