package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.common.Events.ShuffleActionEvent;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActionCardDeckTest {
    /**
     * Testing if the actionCardDeck.json file is readable and correct
     */
    @Test
    public void readActionCardsFromFileTest() throws FileNotFoundException {
        ActionCardDeck actionCardDeck = new ActionCardDeck();

        // memorize the deck
        List<ActionCard> deck = actionCardDeck.getDeck();

        // asserting that there have to be 7 cards in the deck
        assertEquals(7, deck.size());
    }

    /**
     * Testing if it handles the event properly
     */
    @Test
    public void handleEventTest() throws FileNotFoundException {
        ActionCardDeck actionCardDeck = new ActionCardDeck();

        // memorize the old deck
        List<ActionCard> old_deck = actionCardDeck.getDeck();
        actionCardDeck.handleEvent(new ShuffleActionEvent());

        List<ActionCard> new_deck = actionCardDeck.getDeck();


        // asserting that the two decks must have the same cards
        old_deck.forEach(card -> assertTrue(new_deck.contains(card)));
        new_deck.forEach(card -> assertTrue(old_deck.contains(card)));

        // asserts that after the event handling the deck has to be in a different order
        assertEquals(old_deck, new_deck);
    }
}