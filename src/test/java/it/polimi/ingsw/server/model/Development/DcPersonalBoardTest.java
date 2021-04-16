package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import org.junit.Test;

import static org.junit.Assert.*;

public class DcPersonalBoardTest {
    /**
     * testing if the check for the slot works fine
     */
    @Test
    public void checkSlotTest() throws BadSlotNumberException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        dcPersonalBoard.getCardsFromSlot(0);
        dcPersonalBoard.getCardsFromSlot(1);
        dcPersonalBoard.getCardsFromSlot(2);

        try {
            dcPersonalBoard.getCardsFromSlot(3);
            assert false;
        } catch (BadSlotNumberException e) {
            try {
                dcPersonalBoard.getCardsFromSlot(-1);
                assert false;
            } catch (BadSlotNumberException f) {
                assert true;
            }
        }
    }

    /**
     * testing if the personal board is well constructed and if the adds well the first card to the slot
     */
    @Test
    public void validOneAddCardToSlotTest() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // getting the common board
        DcBoard dcBoard = game.getDcBoard();
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        // asserting that the slots should start with 0 cards inside
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(0).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(1).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(2).size());

        // adding a card to the personal board of level 1
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 1)));

        // asserting that the size of the first slot must be 1
        assertEquals(1, dcPersonalBoard.getCardsFromSlot(0).size());
    }

    /**
     * testing if the personal board is well constructed and if the add works fine with an add
     */
    @Test
    public void validThreeAddCardToSlotTest() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // getting the common board
        DcBoard dcBoard = game.getDcBoard();
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        // asserting that the slots should start with 0 cards inside
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(0).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(1).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(2).size());

        // adding a card to the personal board of level 1
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 1)));
        // adding a card to the personal board of level 2
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 2)));
        // adding a card to the personal board of level 2
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 3)));

        // asserting that the size of the first slot must be 1
        assertEquals(3, dcPersonalBoard.getCardsFromSlot(0).size());
    }

    /**
     * testing if the add throws a BadCardPositionException if we try to insert a level 2 card in empty slot
     */
    @Test(expected = BadCardPositionException.class)
    public void level2OnEmptySlotAddCardToSlotTest() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // getting the common board
        DcBoard dcBoard = game.getDcBoard();
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        // asserting that the slots should start with 0 cards inside
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(0).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(1).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(2).size());

        // adding a card to the personal board of level 2
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 2)));

        // asserting that the size of the first slot must be 1
        assert false;
    }

    /**
     * testing if the add throws a BadCardPositionException if we try to insert a level 1 on a level 1 card
     */
    @Test(expected = BadCardPositionException.class)
    public void level1OnLevel1SlotAddCardToSlotTest() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // getting the common board
        DcBoard dcBoard = game.getDcBoard();
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        // asserting that the slots should start with 0 cards inside
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(0).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(1).size());
        assertEquals(0, dcPersonalBoard.getCardsFromSlot(2).size());

        // adding a card to the personal board of level 1
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 1)));

        // trying to add a card of the same level
        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 1)));

        assert false;
    }

    /**
     * testing if the addCardToSlot posts the event LAST_ROUND when inserting the 7th card
     */
    @Test
    public void seventhCardInsertedTest() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // getting the common board
        DcBoard dcBoard = game.getDcBoard();
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        // inserting the first 6 cards in the personal board
        for (int slot = 0; slot < 2; slot++) {
            dcPersonalBoard.addCard(slot, dcBoard.getFirstCard(new Tuple(type, 1)));
            dcPersonalBoard.addCard(slot, dcBoard.getFirstCard(new Tuple(type, 2)));
            dcPersonalBoard.addCard(slot, dcBoard.getFirstCard(new Tuple(type, 3)));
        }

        // buying the seventh card, in order to trigger the LAST_ROUND event
        dcPersonalBoard.addCard(2, dcBoard.getFirstCard(new Tuple(type, 1)));

        // asserting that the
        assertTrue(game.isLastRound());
    }

    /**
     * testing if getTopCard returns null if the slot is empty
     */
    @Test
    public void getTopCardEmptyTest() throws BadSlotNumberException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        assertNull(dcPersonalBoard.getTopCard(0));
    }

    /**
     * testing if getTopCard returns the card with higher level in the slot
     */
    @Test
    public void getTopCardTest() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // getting the common board
        DcBoard dcBoard = game.getDcBoard();
        TypeDevCards_Enum type = TypeDevCards_Enum.BLUE;

        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 1)));
        assertEquals(dcBoard.getFirstCard(new Tuple(type, 1)), dcPersonalBoard.getTopCard(0));

        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 2)));
        assertEquals(dcBoard.getFirstCard(new Tuple(type, 2)), dcPersonalBoard.getTopCard(0));

        dcPersonalBoard.addCard(0, dcBoard.getFirstCard(new Tuple(type, 3)));
        assertEquals(dcBoard.getFirstCard(new Tuple(type, 3)), dcPersonalBoard.getTopCard(0));
    }

    /**
     * testing the isPlaceable method in order to check the normal functioning
     */
    @Test
    public void isPlaceableTest() throws BadSlotNumberException, BadCardPositionException {
        // getting the personal board
        Game game = new Game(2);
        DcPersonalBoard dcPersonalBoard = ((HumanPlayer) game.getPlayers().get(0)).getDevelopmentBoard();

        // creating three cards
        DevelopmentCard developmentCard1 = new DevelopmentCard(new Tuple(TypeDevCards_Enum.BLUE, 1), null, null, 0);
        DevelopmentCard developmentCard2 = new DevelopmentCard(new Tuple(TypeDevCards_Enum.GREEN, 2), null, null, 0);
        DevelopmentCard developmentCard3 = new DevelopmentCard(new Tuple(TypeDevCards_Enum.YELLOW, 3), null, null, 0);

        // at the beginning only the level 1 card is placeable
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard1));
        assertFalse(dcPersonalBoard.isPlaceable(developmentCard2));
        assertFalse(dcPersonalBoard.isPlaceable(developmentCard3));


        // adding a level 1 card
        dcPersonalBoard.addCard(0, developmentCard1);

        // after adding a card also the level 2 card is placeable
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard1));
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard2));
        assertFalse(dcPersonalBoard.isPlaceable(developmentCard3));


        // adding a level 2 card
        dcPersonalBoard.addCard(0, developmentCard2);

        // after adding a card also the level 2 card is placeable
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard1));
        assertFalse(dcPersonalBoard.isPlaceable(developmentCard2));
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard3));


        // adding a level 1 card
        dcPersonalBoard.addCard(1, developmentCard1);

        // after adding a card also the level 2 card is placeable
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard1));
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard2));
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard3));


        // adding a level 1 card
        dcPersonalBoard.addCard(2, developmentCard1);

        // after adding a card also the level 2 card is placeable
        assertFalse(dcPersonalBoard.isPlaceable(developmentCard1));
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard2));
        assertTrue(dcPersonalBoard.isPlaceable(developmentCard3));
    }
}