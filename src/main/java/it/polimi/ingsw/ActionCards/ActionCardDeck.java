package it.polimi.ingsw.ActionCards;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.BadFormatException;
import it.polimi.ingsw.DeckOfCards;

import java.io.FileNotFoundException;

/**
 * Class that models the ActionCardDeck in a single player game
 */
public class ActionCardDeck extends DeckOfCards<ActionCard> {

    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     */
    public ActionCardDeck() throws FileNotFoundException {
        super("src/test/java/resources/ActionCards.json");
    }

    /**
     * overriding the method to parse a single json formatted card to a card object
     *
     * @param jsonCard takes a JsonElement to be parsed in a Card instance
     * @return the card object parsed from the json formatted card
     */
    @Override
    public ActionCard parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        // parsing the single jsonElement to an ActionCard class
        ActionCard actionCard = gson.fromJson(jsonCard, ActionCard.class);

        // checking if the card is well formatted
        if (actionCard.isAllowed())
            return actionCard;
        else
            throw new BadFormatException();
    }
}

