package it.polimi.ingsw.ActionCards;

import it.polimi.ingsw.MockGame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// TODO test the action card deck singleton
public class ActionCardDeckSingletonTest {

    /**
     * testing that all the cards are loaded
     */
    @Test
    public void loadingActionCardsTest() {
        MockGame game = new MockGame();
        ActionCardDeckSingleton actionCardDeck = ActionCardDeckSingleton.getInstance(game);
        assertEquals(7, actionCardDeck.getDeckSize());
    }

    // TODO add more tests

}