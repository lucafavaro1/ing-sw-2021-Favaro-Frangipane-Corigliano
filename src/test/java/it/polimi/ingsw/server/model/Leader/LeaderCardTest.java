package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class LeaderCardTest {
    /**
     * Trying to enable a leader card for a player with no resources
     */
    @Test
    public void enableFalse() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        LeaderCard leaderCard = new LeaderCard(
                null,
                null,
                new ResRequirements(List.of(Res_Enum.STONE)),
                0);

        assertFalse(leaderCard.enable(player));
    }

    /**
     * Trying to enable a leader card for a player correctly
     */
    @Test
    public void enableTrue() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        LeaderCard leaderCard = new LeaderCard(
                null,
                null,
                new ResRequirements(List.of(Res_Enum.STONE)),
                0);

        player.getStrongBox().putRes(Res_Enum.STONE, 1);

        assertTrue(leaderCard.enable(player));

        assertEquals(1, player.getStrongBox().getRes(Res_Enum.STONE));
    }

    /**
     * Trying to enable a leader card two consecutive times having the required resources
     */
    @Test
    public void enableTwice() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        LeaderCard leaderCard = new LeaderCard(
                null,
                null,
                new ResRequirements(List.of(Res_Enum.STONE)),
                0);

        player.getStrongBox().putRes(Res_Enum.STONE, 1);

        assertTrue(leaderCard.enable(player));
        assertTrue(leaderCard.enable(player));

        assertEquals(1, player.getStrongBox().getRes(Res_Enum.STONE));
    }

}