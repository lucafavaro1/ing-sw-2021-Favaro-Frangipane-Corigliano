package it.polimi.ingsw.Development;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.ActionCards.ActionCard;
import it.polimi.ingsw.BadFormatException;
import it.polimi.ingsw.DeckOfCards;
import it.polimi.ingsw.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Class that describes a deck made of developments cards only
 */
public class DevelopmentCardDeck extends DeckOfCards<DevelopmentCard> {
    /**
     * Constructor that loads the deck from a JSON file in the repo
     */
    protected DevelopmentCardDeck() throws FileNotFoundException {
        super("src/main/java/resources/DevelopmentCards.json");
    }

    @Override
    public DevelopmentCard parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        // parsing the single jsonElement to a DevelopmentCard class
        DevelopmentCard developmentCard = gson.fromJson(jsonCard, DevelopmentCard.class);

        // checking if the card is well formatted
        if (developmentCard.isAllowed())
            return developmentCard;
        else
            throw new BadFormatException();
    }
}
