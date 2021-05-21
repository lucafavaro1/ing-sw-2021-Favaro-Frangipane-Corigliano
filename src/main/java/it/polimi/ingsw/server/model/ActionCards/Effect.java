package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.common.Events.DiscardTwoCardsEvent;
import it.polimi.ingsw.common.Events.PlusFaithCardEvent;
import it.polimi.ingsw.common.Events.ShuffleActionEvent;
import it.polimi.ingsw.common.viewEvents.PrintEvent;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;

/**
 * Enumeration that models the different effects of the action cards
 * TODO: send card drawn
 */
public enum Effect {
    DISCARD_TWO_CARDS {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new DiscardTwoCardsEvent(devCard), false);
            game.getEventBroker().post(new PrintEvent<>("Lorenzo discarded two " + devCard + " development cards from the board"), false);
        }
    },
    PLUS_TWO_FAITH {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(2), false);
            game.getEventBroker().post(new PrintEvent<>("Lorenzo gained two faith points!"), false);
        }
    },
    PLUS_ONE_FAITH_SHUFFLE {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(new PlusFaithCardEvent(1), false);
            game.getEventBroker().post(new ShuffleActionEvent(), false);
            game.getEventBroker().post(new PrintEvent<>("Lorenzo gained a faith point and shuffled his cards!"), false);
        }
    };

    public abstract void applyEffect(Game game, TypeDevCards_Enum devCard);
}
