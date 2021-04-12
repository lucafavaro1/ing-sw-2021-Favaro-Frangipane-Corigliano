package it.polimi.ingsw.server.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class that models a deck, offering methods to take cards from the deck,
 * shuffle or take a card and put at the bottom
 *
 * @param <Card> type of card used in the deck
 */
public abstract class DeckOfCards<Card> {

    /**
     * List that contains all the cards of the deck with order index(0) = top; index(size()-1) = bottom
     */
    final private List<Card> deck = new ArrayList<>();

    /**
     * Constructor that initializes the deck with the List passed as parameter
     *
     * @param deck List of cards with witch the deck will be initialized
     * @throws NoCardsInDeckException thrown if we try to insert an empty list of cards
     */
    public DeckOfCards(List<Card> deck) throws NoCardsInDeckException {
        if (deck.isEmpty())
            throw new NoCardsInDeckException();

        this.deck.addAll(deck);
    }

    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     *
     * @param fileName name of the Json file where the cards are stored
     * @throws FileNotFoundException thrown if we try to read from a non existing file
     */
    public DeckOfCards(String fileName) throws FileNotFoundException {
        FileReader fileReader = new FileReader(fileName);
        JsonArray jsonCardList;

        // Reading the JSON file
        try {
            jsonCardList = (JsonArray) JsonParser.parseReader(fileReader);
        } catch (ClassCastException e) {
            throw new BadFormatException();
        }

        // translating the cards read from the file and adding them to the deck
        try {
            jsonCardList.forEach(jsonCard -> deck.add(parseJsonCard(jsonCard)));
        } catch (JsonParseException e) {
            throw new BadFormatException();
        }
        shuffle();
    }

    public List<Card> getDeck() {
        return deck;
    }

    /**
     * Gives the size of the deck
     *
     * @return an integer representing the size of the deck
     */
    public int getDeckSize() {
        return deck.size();
    }

    /**
     * removes from the deck the first n cards and returns them. if there are no sufficient cards, all cards are returned
     *
     * @param n number of cards to return and remove
     * @return the list of cards removed from the deck
     */
    public List<Card> removeCardsFromDeck(int n) {
        // controls and changes if necessary number of cards to take passed to the method
        n = Math.max(0, n);
        n = Math.min(deck.size(), n);

        // copies in a new List the cards that have to be returned and deletes from the deck the cards taken
        List<Card> taken = List.copyOf(deck.subList(0, n));
        deck.subList(0, n).clear();

        return taken;
    }

    /**
     * takes the first card from the deck without removing it from the deck
     *
     * @return the first card from the deck
     */
    public Card getFirstCard() {
        if (deck.isEmpty())
            return null;
        else
            return deck.get(0);
    }

    /**
     * adds a card on the bottom of the deck
     *
     * @param card card to be inserted in the deck
     */
    public void putCardInDeck(Card card) {
        deck.add(card);
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
     * @return the first card of the deck
     * @throws NoCardsInDeckException thrown if we try to take a card from an empty deck
     */
    public Card takeFirstPutLast() throws NoCardsInDeckException {
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
