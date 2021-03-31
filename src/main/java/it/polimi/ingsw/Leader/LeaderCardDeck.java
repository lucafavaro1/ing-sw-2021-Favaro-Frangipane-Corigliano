package it.polimi.ingsw.Leader;

import com.google.gson.JsonElement;
import it.polimi.ingsw.DeckOfCards;

import java.io.FileNotFoundException;

public class LeaderCardDeck extends DeckOfCards {
    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     *
     * @param fileName name of the Json file where the cards are stored
     */
    public LeaderCardDeck(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @Override
    public Object parseJsonCard(JsonElement jsonCard) {
        return null;
    }
}
