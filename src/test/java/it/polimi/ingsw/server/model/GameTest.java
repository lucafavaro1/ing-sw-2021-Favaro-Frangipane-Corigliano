package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    /**
     * testing if the single player game is created correctly
     */
    @Test
    public void instantiatingGameSinglePlayer() {
        Game game = new Game(1);

        // checking if the instances of the players are right
        assertTrue(game.getPlayers().get(0) instanceof HumanPlayer);
        assertTrue(game.getPlayers().get(1) instanceof CPUPlayer);

        // checking if the action card is created correctly
        assertEquals(7, ((CPUPlayer) game.getPlayers().get(1)).getActionCardDeck().getDeckSize());
        for (ActionCard actionCard : ((CPUPlayer) game.getPlayers().get(1)).getActionCardDeck().getDeck())
            assertTrue(actionCard.isAllowed());

        // checking if the DcBoard is created correctly
        assertEquals(12, game.getDcBoard().getAllCards().keySet().size());
        for (Tuple tuple : game.getDcBoard().getAllCards().keySet()) {
            assertEquals(4, game.getDcBoard().getTupleCards(tuple).size());
            for (DevelopmentCard developmentCard : game.getDcBoard().getTupleCards(tuple))
                assertTrue(developmentCard.isAllowed());
        }

        // checking if the leader card deck is created correctly
        assertEquals(16, game.getLeaderCardDeck().getDeckSize());
        for (LeaderCard leaderCard : game.getLeaderCardDeck().getDeck()) {
            assertTrue(leaderCard.isAllowed());
        }

        // checking if the marketTray has been created
        assertNotNull(game.getMarketTray());

        // checking if the eventBroker has been created
        assertNotNull(game.getEventBroker());

        // checking if the last round flag is false
        assertFalse(game.isLastRound());
    }

    /**
     * testing if the multiplayer game is created correctly
     */
    @Test
    public void instantiatingGameMultiPlayer() {
        Game game = new Game(2);

        // checking if the instances of the players are right
        assertTrue(game.getPlayers().get(0) instanceof HumanPlayer);
        assertTrue(game.getPlayers().get(1) instanceof HumanPlayer);

        // checking if the DcBoard is created correctly
        assertEquals(12, game.getDcBoard().getAllCards().keySet().size());
        for (Tuple tuple : game.getDcBoard().getAllCards().keySet()) {
            assertEquals(4, game.getDcBoard().getTupleCards(tuple).size());
            for (DevelopmentCard developmentCard : game.getDcBoard().getTupleCards(tuple))
                assertTrue(developmentCard.isAllowed());
        }

        // checking if the leader card deck is created correctly
        assertEquals(16, game.getLeaderCardDeck().getDeckSize());
        for (LeaderCard leaderCard : game.getLeaderCardDeck().getDeck()) {
            assertTrue(leaderCard.isAllowed());
        }

        // checking if the marketTray has been created
        assertNotNull(game.getMarketTray());

        // checking if the eventBroker has been created
        assertNotNull(game.getEventBroker());

        // checking if the last round flag is false
        assertFalse(game.isLastRound());
    }
}