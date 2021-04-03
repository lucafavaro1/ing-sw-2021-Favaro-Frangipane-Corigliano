package it.polimi.ingsw.Development;

import com.google.gson.JsonElement;
import it.polimi.ingsw.DeckOfCards;
import it.polimi.ingsw.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.util.List;

public class DevelopmentCardDeck extends DeckOfCards<DevelopmentCard> {

    protected DevelopmentCardDeck() throws FileNotFoundException {
        super("src/test/java/resources/DevelopmentCards.json");
    }

    @Override
    public DevelopmentCard parseJsonCard(JsonElement jsonCard) {
        return null;
    }
}
