package it.polimi.ingsw.server.model.Development;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.server.model.BadFormatException;
import it.polimi.ingsw.server.model.DeckOfCards;

import java.io.FileNotFoundException;

/**
 * Class that describes a deck made of developments cards
 */
public class DevelopmentCardDeck extends DeckOfCards<DevelopmentCard> {

    /**
     * Constructor that loads the deck from a JSON file in the repo (resource directory)
     */
    protected DevelopmentCardDeck() throws FileNotFoundException, BadFormatException {
        super("src/main/java/resources/server/DevelopmentCards.json");
    }

    @Override
    public DevelopmentCard parseJsonCard(JsonElement jsonCard) throws BadFormatException {
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
