package it.polimi.ingsw.ActionCards;

import it.polimi.ingsw.Development.TypeDevCards_Enum;
import it.polimi.ingsw.Events.Events_Enum;
import it.polimi.ingsw.Game;

/**
 * Enumeration that models the different effects of the action cards
 */
public enum Effect {
    DISCARD_TWO_CARDS {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            if (devCard == TypeDevCards_Enum.BLUE)
                game.getEventBroker().post(Events_Enum.DISCARD_TWO_BLUE, false);
            else if (devCard == TypeDevCards_Enum.GREEN)
                game.getEventBroker().post(Events_Enum.DISCARD_TWO_GREEN, false);
            else if (devCard == TypeDevCards_Enum.YELLOW)
                game.getEventBroker().post(Events_Enum.DISCARD_TWO_YELLOW, false);
            else if (devCard == TypeDevCards_Enum.PURPLE)
                game.getEventBroker().post(Events_Enum.DISCARD_TWO_PURPLE, false);
            else
                throw new IllegalArgumentException();
        }
    },
    PLUS_TWO_FAITH {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(Events_Enum.PLUS_TWO_FAITH, false);
        }
    },
    PLUS_ONE_FAITH_SHUFFLE {
        @Override
        public void applyEffect(Game game, TypeDevCards_Enum devCard) {
            game.getEventBroker().post(Events_Enum.PLUS_ONE_FAITH, false);
            game.getEventBroker().post(Events_Enum.SHUFFLE_ACTION, false);
        }
    };
    
    public abstract void applyEffect(Game game, TypeDevCards_Enum devCard);
}
