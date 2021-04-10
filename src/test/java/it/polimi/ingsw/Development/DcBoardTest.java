package it.polimi.ingsw.Development;

import it.polimi.ingsw.Events.DiscardTwoCardsEvent;
import it.polimi.ingsw.Events.EventBroker;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.NoCardsInDeckException;
import org.junit.Test;

import javax.swing.text.BadLocationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static it.polimi.ingsw.Events.Events_Enum.*;
import static org.junit.Assert.*;

public class DcBoardTest {

    /**
     * testing if the constructor uploads all the cards from the deck of cards and divides it well
     */
    @Test
    public void constructorTest() {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // asserting that there have to be 12 tuples (one for every type and level combination)
        assertEquals(12, dcBoard.getAllCards().keySet().size());

        // asserting that there have to be 4 cards per tuple
        dcBoard.getAllCards().keySet().forEach(tuple ->
                assertEquals(4, dcBoard.getAllCards().get(tuple).size()));
    }

    /**
     * testing if the constructor correctly subscribes to the events needed
     */
    @Test
    public void eventSubscribingTest() {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();
        EventBroker eventBroker = game.getEventBroker();

        // testing if the dcBoard is correctly subscribed
        assertTrue(eventBroker.getSubscribers().get(DISCARD_TWO).contains(dcBoard));
    }

    /**
     * testing if the method takes the correct card and removes it from the right deck
     */
    @Test
    public void removeFirstCardTest() throws NoCardsInDeckException {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // creating a tuple for testing purpose
        Tuple tuple = new Tuple(TypeDevCards_Enum.GREEN, 1);

        // storing the old board
        List<DevelopmentCard> old_tupleDeck = List.copyOf(dcBoard.getAllCards().get(tuple));

        // asserting that the card returned is the same of the top in the old board
        assertEquals(old_tupleDeck.get(0), dcBoard.removeFirstCard(tuple));

        assertEquals(old_tupleDeck.size() - 1, dcBoard.getAllCards().get(tuple).size());
    }

    /**
     * testing if the method throws the exception when there are no cards in the tuple
     */
    @Test(expected = NoCardsInDeckException.class)
    public void removeFirstCardExceptionTest() throws NoCardsInDeckException {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // creating a tuple for testing purpose
        Tuple tuple = new Tuple(TypeDevCards_Enum.GREEN, 1);

        for (int i = 0; i < 4; i++) {
            dcBoard.removeFirstCard(tuple);
        }

        // this call should throw the exception
        dcBoard.removeFirstCard(tuple);
        assert false;
    }

    @Test
    public void getFirstCardTest() throws NoCardsInDeckException {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // creating a tuple for testing purpose
        Tuple tuple = new Tuple(TypeDevCards_Enum.GREEN, 1);

        // storing the old board
        List<DevelopmentCard> old_tupleDeck = List.copyOf(dcBoard.getAllCards().get(tuple));

        // asserting that the card returned is the same of the top in the old board
        assertEquals(old_tupleDeck.get(0), dcBoard.getFirstCard(tuple));

        assertEquals(old_tupleDeck.size(), dcBoard.getAllCards().get(tuple).size());
    }

    /**
     * testing if the method throws the exception when there are no cards in the tuple
     */
    @Test(expected = NoCardsInDeckException.class)
    public void getFirstCardExceptionTest() throws NoCardsInDeckException {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // creating a tuple for testing purpose
        Tuple tuple = new Tuple(TypeDevCards_Enum.GREEN, 1);

        for (int i = 0; i < 4; i++) {
            dcBoard.removeFirstCard(tuple);
        }

        // this call should throw the exception
        dcBoard.getFirstCard(tuple);
        assert false;
    }

    /**
     * testing the shuffle of the board
     */
    @Test
    public void shuffle() {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();
        Map<Tuple, List<DevelopmentCard>> old_board = new HashMap<>();
        // creating a new map of all the previous states
        dcBoard.getAllCards().keySet().forEach(tuple ->
                old_board.put(tuple, List.copyOf(dcBoard.getTupleCards(tuple)))
        );

        // shuffling the board
        dcBoard.shuffle();

        // asserting that the two maps are not the same as before
        old_board.keySet().forEach(tuple ->
                assertNotEquals(old_board.get(tuple), dcBoard.getAllCards().get(tuple))
        );
    }

    /**
     * testing if the discardTwo discards two cards of the tuple passed
     */
    @Test
    public void discardTwoTest() {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // creating a tuple for testing purpose
        Tuple tuple = new Tuple(TypeDevCards_Enum.GREEN, 1);

        dcBoard.discardTwo(tuple.getType());

        assertEquals(2, dcBoard.getAllCards().get(tuple).size());

        dcBoard.discardTwo(tuple.getType());

        assertEquals(0, dcBoard.getAllCards().get(tuple).size());
    }

    /**
     * testing if the discardTwo discards all the cards of the tuple and posts the event of LAST_ROUND
     */
    @Test
    public void discardAllTest() {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        // creating a tuple for testing purpose
        TypeDevCards_Enum type = TypeDevCards_Enum.GREEN;

        for (int i = 0; i < 6; i++) {
            dcBoard.discardTwo(type);
        }

        // putting a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, dcBoard.getAllCards().get(new Tuple(type, 1)).size());
        assertEquals(0, dcBoard.getAllCards().get(new Tuple(type, 2)).size());
        assertEquals(0, dcBoard.getAllCards().get(new Tuple(type, 3)).size());

        assertTrue(game.isLastRound());
    }

    /**
     * testing if the events are correctly handled
     */
    @Test
    public void handleEventTest() {
        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();
        Tuple tuple;

        // testing the DISCARD_TWO BLUE event
        tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);
        dcBoard.handleEvent(new DiscardTwoCardsEvent(TypeDevCards_Enum.BLUE));
        assertEquals(2, dcBoard.getAllCards().get(tuple).size());

        // testing the DISCARD_TWO GREEN event
        tuple = new Tuple(TypeDevCards_Enum.GREEN, 1);
        dcBoard.handleEvent(new DiscardTwoCardsEvent(TypeDevCards_Enum.GREEN));
        assertEquals(2, dcBoard.getAllCards().get(tuple).size());

        // testing the DISCARD_TWO YELLOW event
        tuple = new Tuple(TypeDevCards_Enum.YELLOW, 1);
        dcBoard.handleEvent(new DiscardTwoCardsEvent(TypeDevCards_Enum.YELLOW));
        assertEquals(2, dcBoard.getAllCards().get(tuple).size());

        // testing the DISCARD_TWO PURPLE event
        tuple = new Tuple(TypeDevCards_Enum.PURPLE, 1);
        dcBoard.handleEvent(new DiscardTwoCardsEvent(TypeDevCards_Enum.PURPLE));
        assertEquals(2, dcBoard.getAllCards().get(tuple).size());
    }
}