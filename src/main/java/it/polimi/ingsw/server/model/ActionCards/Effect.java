package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.common.Events.DiscardTwoCardsEvent;
import it.polimi.ingsw.common.Events.PlusFaithCardEvent;
import it.polimi.ingsw.common.Events.ShuffleActionEvent;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;

/**
 * Enumeration that models the different effects of the action cards
 * TODO: send card drawn
 */
public enum Effect {
    DISCARD_TWO_CARDS("Lorenzo ha scartato due carte sviluppo") {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new DiscardTwoCardsEvent(devCard), false);
        }
    },
    PLUS_TWO_FAITH("Lorenzo ha guadagnato due punti fede") {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(2), false);
        }
    },
    PLUS_ONE_FAITH_SHUFFLE("Lorenzo ha guadagnato un punto fede e ha mescolato le sue carte") {
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
