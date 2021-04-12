package it.polimi.ingsw.server.model.Development;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class DevelopmentCardDeckTest {

    /**
     * testing if all cards are loaded in the deck and if all cards are well formatted
     */
    @Test
    public void loadCardsTest() throws FileNotFoundException {
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();

        assertEquals(48, developmentCardDeck.getDeckSize());
        developmentCardDeck.getDeck().forEach(card -> assertTrue(card.isAllowed()));
    }
}