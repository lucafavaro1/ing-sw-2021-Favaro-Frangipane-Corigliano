package it.polimi.ingsw.server.model.Leader;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.server.model.BadFormatException;
import it.polimi.ingsw.server.model.DeckOfCards;

import java.io.FileNotFoundException;

/**
 * Class that describes a deck made of leader cards only
 */
public class LeaderCardDeck extends DeckOfCards<LeaderCard> {
    /**
     * Constructor that loads the deck from a JSON file in the repo
     */
    public LeaderCardDeck() throws FileNotFoundException {
        super("src/main/java/resources/leaderCards.json");
    }

    @Override
    public LeaderCard parseJsonCard(JsonElement jsonCard) {


        Gson gson = new Gson();

        // parsing the single jsonElement to a DevelopmentCard class
        LeaderCard leaderCard = gson.fromJson(jsonCard, LeaderCard.class);

        if (leaderCard.isAllowed())
            return leaderCard;
        else
            throw new BadFormatException();
    }
}
