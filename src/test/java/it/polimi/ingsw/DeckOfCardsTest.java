package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

public class DeckOfCardsTest {

    /**
     * Testing if, passing a non existing file, the constructor throws the exception
     */
    @Test(expected = FileNotFoundException.class)
    public void readFromAbsentFile() throws FileNotFoundException {
        ConcreteDeck concreteDeck;
        String absentPathName = "src/test/java/resources/AbsentFile.48";
        concreteDeck = new ConcreteDeck(absentPathName);
    }

    /**
     * Testing if opens a json files and parses the content correctly
     */
    @Test
    public void readFromJsonFile() throws FileNotFoundException {

        // reading the json file
        ConcreteDeck concreteDeck = new ConcreteDeck("src/test/java/resources/TestConcreteCard1.json");

        assertEquals(10, concreteDeck.getDeck().size());
        List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .forEach(i ->
                        assertTrue(concreteDeck.getDeck().contains(i))
                );
    }

    /**
     * testing if getCardsFromDeck returns the top n elements of the original deck
     */
    @Test
    public void NormalGetCardsFromDeckTest() throws FileNotFoundException {
        ConcreteDeck concreteDeck;
        concreteDeck = new ConcreteDeck("src/test/java/resources/TestConcreteCard1.json");

        // parameter of the cards to take
        int cardsToTake = 3;

        // asserting that the list have to start with 10 elements
        assertEquals(10, concreteDeck.getDeck().size());

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the cards from the deck
        List<Integer> takenCards = concreteDeck.getCardsFromDeck(cardsToTake);

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
     * testing if getCardsFromDeck returns all the deck if the parameter passed is greater then the size of the deck
     */
    @Test
    public void AboveSizeIndexGetCardsFromDeckTest() throws FileNotFoundException {
        ConcreteDeck concreteDeck;
        concreteDeck = new ConcreteDeck("src/test/java/resources/TestConcreteCard1.json");

        // parameter of the cards to take
        int cardsToTake = 11;

        // asserting that the list have to start with 10 elements
        assertEquals(10, concreteDeck.getDeck().size());

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the cards from the deck
        List<Integer> takenCards = concreteDeck.getCardsFromDeck(cardsToTake);

        // asserting that the cards taken are the size of the complete deck
        assertEquals(old_deck.size(), takenCards.size());

        // asserting that in the old deck there are no more cards to be taken
        assertEquals(0, concreteDeck.getDeck().size());

        // asserting that the cards returned are all the list of the old deck
        assertEquals(old_deck, takenCards);
    }

    /**
     * testing if getCardsFromDeck returns all the deck if the parameter passed is lower then 0
     */
    @Test
    public void BelowZeroIndexGetCardsFromDeckTest() throws FileNotFoundException {
        ConcreteDeck concreteDeck;
        concreteDeck = new ConcreteDeck("src/test/java/resources/TestConcreteCard1.json");

        // parameter of the cards to take
        int cardsToTake = -1;

        // asserting that the list have to start with 10 elements
        assertEquals(10, concreteDeck.getDeck().size());

        // memorizing the old deck
        List<Integer> old_deck = List.copyOf(concreteDeck.getDeck());

        // taking the cards from the deck
        List<Integer> takenCards = concreteDeck.getCardsFromDeck(cardsToTake);

        // asserting that the list of cards returned is empty
        assertTrue(takenCards.isEmpty());

        // asserting that in the old deck is left unchanged
        assertEquals(old_deck, concreteDeck.getDeck());
    }

    /**
     * TODO
     */
    @Test
    public void shuffle() {
    }

    /**
     * TODO
     */
    @Test
    public void takeFirstPutLast() {
    }
}

class ConcreteDeck extends DeckOfCards<Integer> {
    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     *
     * @param fileName name of the Json file where the cards are stored
     */
    public ConcreteDeck(String fileName) throws FileNotFoundException {
        super(fileName);
    }

    @Override
    public Integer parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        // parsing the signle jsonElement to an Integer class
        return gson.fromJson(jsonCard, Integer.class);
    }

    public List<Integer> getDeck() {
        return deck;
    }
}