package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import org.junit.Test;

import static org.junit.Assert.*;

public class DiscardTwoCardsEventTest {

    /**
     * testing if the normal discarding works
     */
    @Test
    public void discardTwoOnce() {
        Game game = new Game(1);
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        assertEquals(4, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(2, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
    }

    /**
     * testing if two discards works
     */
    @Test
    public void discardTwoTwice() {
        Game game = new Game(1);
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        assertEquals(4, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(2, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(0, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
    }

    /**
     * testing if three discards works
     */
    @Test
    public void discardThreeTwice() {
        Game game = new Game(1);
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        assertEquals(4, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(2, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(0, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        assertEquals(4, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(2, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
    }

    /**
     * testing if a discard works
     */
    @Test
    public void discardTwoLevels() throws NoCardsInDeckException {
        Game game = new Game(1);
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        game.getDcBoard().removeFirstCard(new Tuple(type, 1));
        assertEquals(3, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(1, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(0, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        assertEquals(3, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(1, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
    }

    /**
     * testing if a discard works
     */
    @Test
    public void lastRoundPostedTest() throws NoCardsInDeckException, InterruptedException {
        Game game = new Game(1);
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        // asserting that at the beginning of the round the lastRound variable shouldn't be set true
        assertFalse(game.isLastRound());

        // removing cards till all cards from the same level are removed
        game.getDcBoard().removeFirstCard(new Tuple(type, 1));
        assertEquals(3, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(1, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(0, game.getDcBoard().getTupleCards(new Tuple(type, 1)).size());
        assertEquals(3, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(1, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
        assertEquals(4, game.getDcBoard().getTupleCards(new Tuple(type, 3)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(0, game.getDcBoard().getTupleCards(new Tuple(type, 2)).size());
        assertEquals(3, game.getDcBoard().getTupleCards(new Tuple(type, 3)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(1, game.getDcBoard().getTupleCards(new Tuple(type, 3)).size());
        game.getEventBroker().post(new DiscardTwoCardsEvent(type), true);
        assertEquals(0, game.getDcBoard().getTupleCards(new Tuple(type, 3)).size());

        // waiting for the handling of the last round event
        Thread.sleep(10);
        assertTrue(game.isLastRound());
    }
}