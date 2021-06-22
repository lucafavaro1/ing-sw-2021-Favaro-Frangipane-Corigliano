package it.polimi.ingsw.server.model.ActionCards;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.server.model.BadFormatException;
import it.polimi.ingsw.server.model.DeckOfCards;
import it.polimi.ingsw.common.Events.EventHandler;

import java.io.FileNotFoundException;

/**
 * Class that models the ActionCardDeck in a single player game
 */
public class ActionCardDeck extends DeckOfCards<ActionCard> implements EventHandler {

    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     * @throws FileNotFoundException in case json file is not found
     */
    public ActionCardDeck() throws FileNotFoundException {
        super("/Server/actionCards.json");
    }

    /**
     * Override of the method to parse a single json formatted card to a card object
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

