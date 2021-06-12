package it.polimi.ingsw.server.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

// TODO Restore tests for deck of cards!
public class DeckOfCardsTest {

    /**
     * Testing if opens a json files and parses the content correctly
     */
    // @Test
    public void readFromJsonFile() throws FileNotFoundException {

        // reading the json file
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");

        assertEquals(10, concreteDeck.getDeck().size());
        List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .forEach(i ->
                        assertTrue(concreteDeck.getDeck().contains(i))
                );
    }

    /**
     * Testing if, passing a non existing file, the constructor throws the exception
     */
    // @Test(expected = FileNotFoundException.class)
    public void readFromAbsentFile() throws FileNotFoundException {
        String absentPathName = "src/test/java/resources/AbsentFile.48";
        new MockDeck(absentPathName);
        assert false;
    }

    /**
     * Testing if opens a json files and parses the content correctly
     */
    // @Test(expected = BadFormatException.class)
    public void readFromEmptyFile() throws FileNotFoundException {
        // reading the json file
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCardBadFormat.json");
        assert false;
    }

    /**
     * Testing if removeCardsFromDeck returns the top n elements of the original deck
     */
    // @Test
    public void NormalRemoveCardsFromDeckTest() throws FileNotFoundException {
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");
        // parameter of the cards to take
        int cardsToTake = 3;

        // asserting that the list have to start with 10 elements
        assertEquals(10, concreteDeck.getDeck().size());

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the cards from the deck
        List<Integer> takenCards = concreteDeck.removeCardsFromDeck(cardsToTake);

        // asserting that the cards taken are of the number we wanted
        assertEquals(cardsToTake, takenCards.size());

        // asserting that from the old deck have been removed exactly the cards we wanted to take
        assertEquals(old_deck.size() - cardsToTake, concreteDeck.getDeck().size());

        // asserting that the cards removed are the top n cards we wanted to take
        assertEquals(old_deck.subList(0, cardsToTake), takenCards);

        // asserting that the cards removed are no longer in the new deck
        takenCards.forEach(takenCard -> assertFalse(concreteDeck.getDeck().contains(takenCard)));
    }

    /**
     * Testing if removeCardsFromDeck returns all the deck if the parameter passed is greater then the size of the deck
     */
    // @Test
    public void AboveSizeIndexRemoveCardsFromDeckTest() throws FileNotFoundException {
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");

        // parameter of the cards to take
        int cardsToTake = 11;

        // asserting that the list have to start with 10 elements
        assertEquals(10, concreteDeck.getDeck().size());

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the cards from the deck
        List<Integer> takenCards = concreteDeck.removeCardsFromDeck(cardsToTake);

        // asserting that the cards taken are the size of the complete deck
        assertEquals(old_deck.size(), takenCards.size());

        // asserting that in the old deck there are no more cards to be taken
        assertEquals(0, concreteDeck.getDeck().size());

        // asserting that the cards returned are all the list of the old deck
        assertEquals(old_deck, takenCards);
    }

    /**
     * Testing if removeCardsFromDeck returns all the deck if the parameter passed is lower then 0
     */
    // @Test
    public void BelowZeroIndexRemoveCardsFromDeckTest() throws FileNotFoundException {
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");

        // parameter of the cards to take
        int cardsToTake = -1;

        // asserting that the list have to start with 10 elements
        assertEquals(10, concreteDeck.getDeck().size());

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the cards from the deck
        List<Integer> takenCards = concreteDeck.removeCardsFromDeck(cardsToTake);

        // asserting that the list of cards returned is empty
        assertTrue(takenCards.isEmpty());

        // asserting that the old deck is left unchanged
        assertEquals(old_deck, concreteDeck.getDeck());
    }

    /**
     * Testing if removeCardsFromDeck returns all the deck if the parameter passed is lower then 0
     */
    // @Test
    public void MoreRemoveCardsFromDeckTest() throws FileNotFoundException {
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");

        // parameter of the cards to take
        int cardsToTake = 5;

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // asserting that the list have to start with 10 elements
        assertEquals(10, old_deck.size());

        // taking the cards from the deck
        List<Integer> takenCards1 = concreteDeck.removeCardsFromDeck(cardsToTake);

        // asserting that the list of cards returned is long the exact amount
        assertEquals(takenCards1.size(), cardsToTake);

        // asserting that the cards returned are exactly the first n cards wanted
        assertEquals(old_deck.subList(0, cardsToTake), takenCards1);

        // taking the cards from the deck another time
        List<Integer> takenCards2 = concreteDeck.removeCardsFromDeck(cardsToTake);

        // asserting that the list of cards returned is long the exact amount
        assertEquals(takenCards2.size(), cardsToTake);

        // asserting that the cards returned are exactly the next n cards of the old_deck
        assertEquals(old_deck.subList(cardsToTake, 2 * cardsToTake), takenCards2);

        // asserting that there are no cards left in the actual deck
        assertTrue(concreteDeck.getDeck().isEmpty());
    }

    /**
     * Testing if the shuffle changes the order of the cards
     */
    // @Test
    public void shuffleTest() throws FileNotFoundException {
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");

        for (int i = 0; i < 20; i++) {
            List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());
            concreteDeck.shuffle();
            List<Integer> new_deck = List.copyOf(concreteDeck.getDeck());

            // asserting that the cards mustn't change among shuffles
            new_deck.forEach(card -> assertTrue(old_deck.contains(card)));
            old_deck.forEach(card -> assertTrue(new_deck.contains(card)));

            // asserting that the old and new order of cards must be different
            assertNotEquals(old_deck, new_deck);
        }
    }

    /**
     * Testing if the shuffle deals well with a one-element list
     */
    // @Test
    public void OneElementShuffleTest() throws NoCardsInDeckException {
        MockDeck concreteDeck;
        concreteDeck = new MockDeck(List.of(48));

        assertEquals(List.of(48), concreteDeck.getDeck());
    }

    /**
     * Testing if the shuffle deals well if the deck is empty
     */
    // @Test
    public void NoElementsShuffleTest() throws NoCardsInDeckException {
        MockDeck concreteDeck = new MockDeck(List.of(48));

        // emptying the deck of cards (we can't initialize a deck with 0 cards)
        concreteDeck.removeCardsFromDeck(1);

        // asserting that the deck is now empty
        assertTrue(concreteDeck.getDeck().isEmpty());

        // trying to shuffle an empty deck
        concreteDeck.shuffle();

        // asserting that the deck is now empty
        assertTrue(concreteDeck.getDeck().isEmpty());
    }

    /**
     * Testing if the function takeFirstPutLast returns the first card and puts it at the bottom of the deck
     */
    // @Test
    public void takeFirstPutLastTest() throws FileNotFoundException, NoCardsInDeckException {
        MockDeck concreteDeck = new MockDeck("../../test/java/resources/TestConcreteCard1.json");

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the first card
        Integer taken = concreteDeck.takeFirstPutLast();

        // asserting that the taken card should be the top one of the old deck
        assertEquals(taken, old_deck.get(0));

        List<Integer> new_deck = concreteDeck.getDeck();
        // asserting that the taken card should be the last one of the new deck
        assertEquals(old_deck.size() - 1, new_deck.indexOf(taken));

        // asserting that the new deck should have the same elements of the old one
        new_deck.forEach(card -> assertTrue(old_deck.contains(card)));
        old_deck.forEach(card -> assertTrue(new_deck.contains(card)));
    }

    /**
     * Testing if the function takeFirstPutLast deals well with a list of only one element
     */
    // @Test
    public void takeFirstPutLastOneElementTest() throws NoCardsInDeckException {
        MockDeck concreteDeck;
        concreteDeck = new MockDeck(List.of(48));

        // taking the first card
        Integer taken = concreteDeck.takeFirstPutLast();

        // asserting that the card returned is the only card available
        assertEquals(Integer.valueOf(48), taken);

        // asserting that the final deck is the same as the previous one
        assertEquals(List.of(48), concreteDeck.getDeck());
    }

    /**
     * Testing if the function takeFirstPutLast returns an exception if the deck is empty
     */
    // @Test(expected = NoCardsInDeckException.class)
    public void takeFirstPutLastEmptyTest() throws NoCardsInDeckException {
        MockDeck concreteDeck;
        concreteDeck = new MockDeck(List.of());

        // taking the first card
        Integer taken = concreteDeck.takeFirstPutLast();

        assert false;
    }

    /**
     * Testing if the function returns the first card in the deck
     */
    // @Test
    public void getFirstCardTest() throws NoCardsInDeckException {
        MockDeck concreteDeck;
        concreteDeck = new MockDeck(List.of(48, 42, 420));

        // taking the first card
        Integer taken = concreteDeck.getFirstCard();

        // asserting that the card returned is the first card
        assertEquals(Integer.valueOf(48), taken);

        // asserting that the final deck is the same as the previous one
        assertEquals(List.of(48, 42, 420), concreteDeck.getDeck());
    }

    /**
     * Testing if the method inserts a card into the deck
     */
    // @Test
    public void putCardTest() throws NoCardsInDeckException {
        MockDeck concreteDeck;
        concreteDeck = new MockDeck(List.of(48));

        // asserting that the first card is the only one inserted
        assertEquals(Integer.valueOf(48), concreteDeck.getFirstCard());

        // inserting a card
        concreteDeck.putCardInDeck(42);

        // asserting that the card returned is the card just inserted
        assertEquals(List.of(48, 42), concreteDeck.getDeck());
    }

    /**
     * Testing if the method deals with inserting the first card into the deck
     */
    // @Test
    public void putCardInEmptyDeckTest() throws NoCardsInDeckException {
        MockDeck concreteDeck;
        concreteDeck = new MockDeck(List.of(48));

        // deleting all the cards from the deck
        concreteDeck.removeCardsFromDeck(2);

        assertTrue(concreteDeck.getDeck().isEmpty());

        // trying to insert the first card in the deck
        concreteDeck.putCardInDeck(420);

        assertEquals(List.of(420), concreteDeck.getDeck());
    }

}

/**
 * A mock deck for testing purposes only
 */
class MockDeck extends DeckOfCards<Integer> {
    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     *
     * @param fileName name of the Json file where the cards are stored
     */
    protected MockDeck(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    /**
     * Contructor that loads a deck passed as parameter
     *
     * @param deck the deck to load
     */
    protected MockDeck(List<Integer> deck) throws NoCardsInDeckException {
        super(deck);
    }

    @Override
    public Integer parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        // parsing the signle jsonElement to an Integer class
        return gson.fromJson(jsonCard, Integer.class);
    }
}