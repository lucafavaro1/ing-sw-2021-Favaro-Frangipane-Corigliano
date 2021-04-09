package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.Leader.LeaderAbility;
import it.polimi.ingsw.Leader.LeaderCard;
import it.polimi.ingsw.Leader.PlusSlot;
import it.polimi.ingsw.Player.HumanPlayer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ResRequirementsTest {

    /**
     * checks if a requirement is satisfiable by only the strongBox resources
     */
    @Test
    public void isSatisfiableOnlyStrongBox() {
        ResRequirements requirement = new ResRequirements(List.of(
                Res_Enum.STONE, Res_Enum.STONE,
                Res_Enum.COIN
        ));

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 2);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        assertTrue(requirement.isSatisfiable(player));
    }

    /**
     * checks if a requirement is satisfiable by only the WarehouseDepots resources
     */
    @Test
    public void isSatisfiableOnlyWarehouseDepots() throws Exception {
        ResRequirements requirement = new ResRequirements(List.of(
                Res_Enum.STONE, Res_Enum.STONE,
                Res_Enum.COIN
        ));

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getWarehouseDepots().add_dp(Res_Enum.STONE, 2, 3);
        player.getWarehouseDepots().add_dp(Res_Enum.COIN, 1, 2);

        assertTrue(requirement.isSatisfiable(player));
    }

    /**
     * checks if a requirement is satisfiable by only the LeaderCard ability PlusSlot
     */
    @Test
    public void isSatisfiableOnlyLeaderPlusSlot() throws Exception {
        ResRequirements requirement = new ResRequirements(List.of(
                Res_Enum.STONE, Res_Enum.STONE
        ));

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        // creating leader card
        PlusSlot plusSlot = new PlusSlot(Res_Enum.STONE);
        plusSlot.setPlayer(player);

        LeaderCard leaderCard = new LeaderCard(plusSlot, null, null, 48);

        // asserting that at the moment we the requirement isn't satisfied
        assertFalse(requirement.isSatisfiable(player));

        // putting 2 STONES in the leaderCard
        ((PlusSlot) leaderCard.getCardAbility()).putRes(Res_Enum.STONE);
        ((PlusSlot) leaderCard.getCardAbility()).putRes(Res_Enum.STONE);

        // asserting that now the requirement is satisfied
        assertTrue(requirement.isSatisfiable(player));

        ((PlusSlot) leaderCard.getCardAbility()).removeRes(1);

        // asserting that now the requirement are no more satisfied
        assertFalse(requirement.isSatisfiable(player));
    }

    /**
     * checks if a requirement is satisfiable by StrongBox and WarehouseDepots resources
     */
    @Test
    public void isSatisfiableStrongBoxAndWarehouseDepots() throws Exception {
        ResRequirements requirement = new ResRequirements(List.of(
                Res_Enum.STONE, Res_Enum.STONE,
                Res_Enum.COIN
        ));

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 1);

        player.getWarehouseDepots().add_dp(Res_Enum.STONE, 1, 3);
        player.getWarehouseDepots().add_dp(Res_Enum.COIN, 1, 2);

        assertTrue(requirement.isSatisfiable(player));
    }

    /**
     * checks if a requirement is non satisfied
     */
    @Test
    public void isNotSatisfiableTest() {
        ResRequirements requirement = new ResRequirements(List.of(
                Res_Enum.STONE, Res_Enum.STONE,
                Res_Enum.COIN
        ));

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 1);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        assertFalse(requirement.isSatisfiable(player));
    }

    // TODO: test isSatisfiable with discounts and moreResources


}