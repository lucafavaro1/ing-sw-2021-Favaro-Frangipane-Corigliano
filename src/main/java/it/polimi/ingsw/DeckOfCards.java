package it.polimi.ingsw;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class that models a deck, offering methods to take cards from the deck,
 * shuffle or take a card and put at the bottom
 * TODO: make it a Singleton
 *
 * @param <Card> type of card used in the deck
 */
public abstract class DeckOfCards<Card> {
    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     *
     * @param fileName name of the Json file where the cards are stored
     */
    public DeckOfCards(String fileName) throws FileNotFoundException {
        FileReader fileReader = new FileReader(fileName);

        // Reading the JSON file
        JsonArray jsonCardList = (JsonArray) JsonParser.parseReader(fileReader);

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
        n = Math.max(0, n);
        n = Math.min(deck.size(), n);
        List<Card> taken = List.copyOf(deck.subList(0, n));
        deck.subList(0, n).clear();

        return taken;
    }

    /**
     * shuffles the deck in a different order than before
     */
    public void shuffle() {
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
        Card takenCard = deck.remove(0);
        deck.add(deck.size() - 1, takenCard);
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
