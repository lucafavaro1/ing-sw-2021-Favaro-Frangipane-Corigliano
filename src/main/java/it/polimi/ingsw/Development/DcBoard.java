package it.polimi.ingsw.Development;

import it.polimi.ingsw.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Class representing the Development Card Board that is in common for all players
 */
public class DcBoard {
    private HashMap<Tuple, List<DevelopmentCard>> allCards = new HashMap<>();

    /**
     * Constructor that loads all the cards in the board
     *
     * @throws FileNotFoundException if the file doesn't exist
     */
    public DcBoard() throws FileNotFoundException {
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();

        // put the cards from the developmentCardDeck to the DcBoard map
        developmentCardDeck.getCardsFromDeck(developmentCardDeck.getDeckSize())
                .forEach(
                        card ->
                        {
                            if (allCards.containsKey(card.getCardType()))
                                allCards.get(card.getCardType()).add(card);
                            else
                                allCards.put(card.getCardType(), List.of(card));
                        }
                );
    }

    /**
     * Takes the first card of the deck identified from the tuple and removes it
     * m
     * * @param tuple the type of developent card to take
     *
     * @return the first development card of the deck
     * @throws NoCardsInDeckException if there is no card in the deck
     */
    public DevelopmentCard takeFirstCard(Tuple tuple) throws NoCardsInDeckException {
        if (allCards.get(tuple) == null || allCards.get(tuple).isEmpty())
            throw new NoCardsInDeckException();

        return allCards.get(tuple).remove(0);
    }

    /**
     * Takes the first card of the deck identified from the tuple without removing it
     *
     * @param tuple the type of development card to take
     * @return the first development card of the deck
     * @throws NoCardsInDeckException if there is no card in the deck
     */
    public DevelopmentCard getFirstCard(Tuple tuple) throws NoCardsInDeckException {
        if (allCards.get(tuple) == null || allCards.get(tuple).isEmpty())
            throw new NoCardsInDeckException();

        return allCards.get(tuple).get(0);
    }

    /**
     * Method to remove (consequence of buying) a development card from the DcBoard
     *
     * @param t unique identifier of the card you want to take
     */
    public void removeFirstCard(Tuple t) {
        if (allCards.get(t).size() > 0)
            allCards.get(t).remove(1);
    }

    /**
     * Method to get a deck in the DCBoard
     *
     * @param t tuple identifying the deck
     * @return the list of cards of that specific deck
     */
    public List<DevelopmentCard> takeCards(Tuple t) {
        return allCards.get(t);
    }

    /**
     * Method to shuffle the decks of development cards divided by type and level
     */
    public void shuffle() {
        allCards.keySet().forEach(tuple -> Collections.shuffle(allCards.get(tuple)));
    }
}
