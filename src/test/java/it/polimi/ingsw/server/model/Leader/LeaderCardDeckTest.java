package it.polimi.ingsw.server.model.Leader;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LeaderCardDeckTest {

    /**
     * Testing if the LeaderCardDeck.json file is correctly readable and every leader card is allowed
     */

    @Test
    public void readLeaderCardsFromFileTest() throws FileNotFoundException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();

        // memorize the deck
        List<LeaderCard> deck = leaderCardDeck.getDeck();

        // asserting that there have to be 7 cards in the deck
        assertEquals(16, deck.size());

        for (LeaderCard leaderCard : deck) {
            assertTrue(leaderCard.isAllowed());
        }
    }
}