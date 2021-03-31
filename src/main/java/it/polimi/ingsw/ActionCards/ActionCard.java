package it.polimi.ingsw.ActionCards;

import com.google.gson.Gson;
import it.polimi.ingsw.Development.TypeDevCards_Enum;

import java.lang.invoke.WrongMethodTypeException;
import java.util.Optional;


final public class ActionCard {
    final private Effect effect;
    final private TypeDevCards_Enum devCardToDiscard;

    public ActionCard(Effect effect, TypeDevCards_Enum devCardToDiscard) {
        this.effect = effect;
        this.devCardToDiscard = devCardToDiscard;
    }

    public ActionCard(Effect effect) throws WrongMethodTypeException{
        if (effect == Effect.DISCARD_TWO_CARDS)
            throw new WrongMethodTypeException();

        this.effect = effect;
        this.devCardToDiscard = null;
    }

    public Effect getEffect() {
        return effect;
    }

    public Optional<TypeDevCards_Enum> getCardToDiscard() {
        return Optional.ofNullable(devCardToDiscard);
    }
}


