package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.LastRoundEvent;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.util.*;

import static it.polimi.ingsw.common.Events.Events_Enum.DISCARD_TWO;

/**
 * Class representing the Development Card Board, common for all the players
 */
public class DcBoard implements EventHandler {
    private final Map<Tuple, List<DevelopmentCard>> allCards = new HashMap<>();
    private final Game game;

    /**
     * Constructor that loads all the cards in the board
     *
     * @param game game to which the board belongs to
     * @throws FileNotFoundException if the file doesn't exist
     */
    public DcBoard(Game game) throws FileNotFoundException {
        this.game = game;

        // creating the deck of development cards
        DevelopmentCardDeck developmentCardDeck = new DevelopmentCardDeck();

        // put the cards from the developmentCardDeck to the DcBoard map
        developmentCardDeck.getDeck().forEach(
                card ->
                {
                    if (!allCards.containsKey(card.getCardType()))
                        allCards.put(card.getCardType(), new ArrayList<>());

                    allCards.get(card.getCardType()).add(card);
                }
        );

        // shuffles the board
        shuffle();

        // subscribing to the events
        game.getEventBroker().subscribe(this, EnumSet.of(DISCARD_TWO));
    }

    /**
     * Takes the first card of the deck identified from the tuple and removes it
     *
     * @param tuple the type of developent card to take
     * @return the first development card of the deck
     * @throws NoCardsInDeckException if there is no card in the deck
     */
    public DevelopmentCard removeFirstCard(Tuple tuple) throws NoCardsInDeckException {
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
     * Method to get a deck in the DCBoard
     *
     * @param t tuple identifying the deck
     * @return the list of cards of that specific deck
     */
    public List<DevelopmentCard> getTupleCards(Tuple t) {
        return allCards.get(t);
    }

    /**
     * Method to shuffle the decks of development cards divided by type and level
     */
    public void shuffle() {
        allCards.keySet().forEach(tuple -> {
            List<DevelopmentCard> old_tupleCards = List.copyOf(allCards.get(tuple));

            do {
                Collections.shuffle(allCards.get(tuple));
            } while (old_tupleCards.equals(allCards.get(tuple)));
        });
    }

    /**
     * discards two cards of the type passed and of minimum level available
     *
     * @param typeCard type of card to discard
     */
    public void discardTwo(TypeDevCards_Enum typeCard) {
        int level = Tuple.getMinLevel();
        int nTakenCards = 0;

        while (nTakenCards < 2) {
            try {
                removeFirstCard(new Tuple(typeCard, level));
                nTakenCards += 1;
            } catch (NoCardsInDeckException e) {
                // if there are no more cards in the last deck of that type the game is over
                if (level < Tuple.getMaxLevel())
                    level++;
                else {
                    game.getEventBroker().post(new LastRoundEvent(), false);
                    break;
                }
            }
        }

        // if there are no more cards in the last deck of that type the game is over
        if (getTupleCards(new Tuple(typeCard, Tuple.getMaxLevel())).isEmpty())
            game.getEventBroker().post(new LastRoundEvent(), false);
    }

    public Map<Tuple, List<DevelopmentCard>> getAllCards() {
        return allCards;
    }
}
