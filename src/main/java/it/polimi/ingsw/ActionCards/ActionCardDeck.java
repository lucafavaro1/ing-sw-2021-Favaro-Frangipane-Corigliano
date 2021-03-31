package it.polimi.ingsw.ActionCards;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.DeckOfCards;

import java.io.FileNotFoundException;

public class ActionCardDeck extends DeckOfCards<ActionCard> {
    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     */
    public ActionCardDeck() throws FileNotFoundException {
        super("src/test/java/resources/ActionCards.json");
    }

    @Override
    public ActionCard parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        // parsing the signle jsonElement to an ActionCard class
        return gson.fromJson(jsonCard, ActionCard.class);
    }
}

