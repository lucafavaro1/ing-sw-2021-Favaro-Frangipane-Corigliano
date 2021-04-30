package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.server.model.BadFormatException;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;

/**
 * Object that represents the action cards used by the CPUPlayer in single player
 */
final public class ActionCard {
    final private Effect effect;
    final private TypeDevCards_Enum devCardToDiscard;

    /**
     * Constructor for an action card
     *
     * @param effect           the effect of the action card
     * @param devCardToDiscard the type of card to be discarded (used only for Effect.DISCARD_TWO_CARDS)
     */
    public ActionCard(Effect effect, TypeDevCards_Enum devCardToDiscard) {
        this.effect = effect;
        this.devCardToDiscard = devCardToDiscard;
    }

    /**
     * Constructor for an action card with an effect different from Effect.DISCARD_TWO_CARDS
     *
     * @param effect Effect of card, different from Effect.DISCARD_TWO_CARDS
     */
    public ActionCard(Effect effect) {
        if (effect == Effect.DISCARD_TWO_CARDS)
            throw new BadFormatException();

        this.effect = effect;
        this.devCardToDiscard = null;
    }

    /**
     * checks if the card is a well formatted card
     *
     * @return true if is an allowed card, false otherwise
     */
    public boolean isAllowed() {
        return effect != Effect.DISCARD_TWO_CARDS || devCardToDiscard != null;
    }

    /**
     * returns the cards to discard only if the effect is Effect.DISCARD_TWO_CARDS
     *
     * @return the type of the cards to discard
     */
    public TypeDevCards_Enum getDevCardToDiscard() {
        return devCardToDiscard;
    }

    public Effect getEffect() {
        return effect;
    }

    @Override
    public String toString() {
        String string = "Action card effect: " + effect;
        if (effect == Effect.DISCARD_TWO_CARDS)
            string += " " + devCardToDiscard;

        return string;
    }
}
