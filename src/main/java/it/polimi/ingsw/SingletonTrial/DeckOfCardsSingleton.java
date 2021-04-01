package it.polimi.ingsw.SingletonTrial;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.polimi.ingsw.BadFormatException;
import it.polimi.ingsw.MockGame;
import it.polimi.ingsw.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Abstract class that models a deck, offering methods to take cards from the deck,
 * shuffle or take a card and put at the bottom
 * TODO make singleton all classes that extend this
 * TODO test this class (singleton part)
 *
 * @param <Card> type of card used in the deck
 */
public abstract class DeckOfCardsSingleton<Card> extends SingletonClass{

    static Map<MockGame, DeckOfCardsSingleton> getInstances() {
        return SingletonClass.getInstances(DeckOfCardsSingleton.class);
    }

    static public void resetInstances() {
        SingletonClass.resetInstances(DeckOfCardsSingleton.class);
    }

    static public DeckOfCardsSingleton getInstance(MockGame game) {
        return SingletonClass.getInstance(game, DeckOfCardsSingleton.class);
    }

    static public void removeInstance(MockGame game){
        SingletonClass.removeInstance(game, DeckOfCardsSingleton.class);
    }

    /**
     * Constructor that initializes the deck with the List passed as parameter
     *
     * @param deck List of cards with witch the deck will be initialized
     */
    protected DeckOfCardsSingleton(List<Card> deck) {
        if (deck.isEmpty())
            throw new NoCardsInDeckException();

        this.deck.addAll(deck);
    }

    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     *
     * @param fileName name of the Json file where the cards are stored
     */
    protected DeckOfCardsSingleton(String fileName) throws FileNotFoundException {
        FileReader fileReader = new FileReader(fileName);
        JsonArray jsonCardList;

        // Reading the JSON file
        try {
            jsonCardList = (JsonArray) JsonParser.parseReader(fileReader);
        } catch (ClassCastException e) {
            throw new BadFormatException();
        }

        // translating the cards read from the file and adding them to the deck
        jsonCardList.forEach(jsonCard -> deck.add(parseJsonCard(jsonCard)));
        shuffle();
    }

    /**
     * List that contains all the cards of the deck with order index(0) = top; index(size()-1) = bottom
     */
    final protected List<Card> deck = new ArrayList<>();

    /**
     * Gives the size of the deck
     *
     * @return an integer representing the size of the deck
     */
    public int getDeckSize() {
        return deck.size();
    }

    /**
     * removes from the deck the first n cards and returns them
     * TODO: decide if throw the exception or return something anyway
     *
     * @param n number of cards to return and remove
     * @return the list of cards removed from the deck
     */
    public List<Card> getCardsFromDeck(int n) {
        // controls and changes if necessary number of cards to take passed to the method
        n = Math.max(0, n);
        n = Math.min(deck.size(), n);

        // copies in a new List the cards that have to be returned and deletes from the deck the cards taken
        List<Card> taken = List.copyOf(deck.subList(0, n));
        deck.subList(0, n).clear();

        return taken;
    }

    /**
     * shuffles the deck in a different order than before
     */
    public void shuffle() {
        if (deck.size() == 0)
            return;
        else if (deck.size() == 1)
            return;

        List<Card> prev_deck = deck;

        do {
            Collections.shuffle(deck);
        } while (!deck.equals(prev_deck));
    }

    /**
     * takes a card, puts it on the bottom of the deck and returns it
     *
     * @return the card taken
     */
    public Card takeFirstPutLast() {
        if (deck.size() <= 0)
            throw new NoCardsInDeckException();

        Card takenCard = deck.remove(0);
        deck.add(deck.size(), takenCard);
        return takenCard;
    }

    /**
     * Implements the logic for the parsing of the relative Cards
     *
     * @param jsonCard takes a JsonElement to be parsed in a Card instance
     * @return the Card object
     */
    public abstract Card parseJsonCard(JsonElement jsonCard);
}
