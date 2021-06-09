package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.common.Events.DiscardTwoCardsEvent;
import it.polimi.ingsw.common.Events.PlusFaithCardEvent;
import it.polimi.ingsw.common.Events.ShuffleActionEvent;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;

/**
 * Enumeration that models the different effects of the action cards
 */
public enum Effect {
    DISCARD_TWO_CARDS("Lorenzo has discarted two development cards") {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new DiscardTwoCardsEvent(devCard), false);
        }
    },
    PLUS_TWO_FAITH("Lorenzo gained two faith points") {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(2), false);
        }
    },
    PLUS_ONE_FAITH_SHUFFLE("Lorenzo gained a faith point and shuffled his cards") {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(1), false);
            game.getEventBroker().post(new ShuffleActionEvent(), false);
        }
    };

    private final String text;

    Effect(String text) {
        this.text = text;
    }

    public abstract void applyEffect(Game game, TypeDevCards_Enum devCard);

    @Override
    public String toString() {
        return text;
    }
}
