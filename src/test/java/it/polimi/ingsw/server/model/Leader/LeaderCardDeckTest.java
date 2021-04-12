package it.polimi.ingsw.server.model.Leader;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

public class LeaderCardDeckTest {

    /**
     * Testing if the LeaderCardDeck.json file is correctly readable and that
     * every leader card is allowed
     */
    @Test
    public void readLeaderCardsFromFileTest() throws FileNotFoundException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();

        // memorize the deck
        List<LeaderCard> deck = leaderCardDeck.getDeck();

        // asserting that there have to be 7 cards in the deck
        assertEquals(16, deck.size());
        for(int i=0; i<deck.size();i++){
            assertTrue(deck.get(i).isAllowed());
        }
    }
}