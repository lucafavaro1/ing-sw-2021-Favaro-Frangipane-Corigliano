package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.StrongBox;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductionTest {

    /**
     * Testing if the production is really satisfiable
     */
    @Test
    public void isSatisfiable1True() {
        Production production = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);
        StrongBox strongBox = player.getStrongBox();

        strongBox.putRes(Res_Enum.STONE, 2);
        strongBox.putRes(Res_Enum.COIN, 1);

        assertTrue(production.isSatisfiable(player));
    }

    /**
     * Testing if the production is really satisfiable
     */
    @Test
    public void isSatisfiable1False() {
        Production production = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);
        StrongBox strongBox = player.getStrongBox();

        strongBox.putRes(Res_Enum.STONE, 3);
        strongBox.putRes(Res_Enum.SERVANT, 1);

        assertFalse(production.isSatisfiable(player));
    }

    /**
     * Testing if the production is really satisfiable
     */
    @Test
    public void isSatisfiable2SameFalse() {
        Production production = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);
        StrongBox strongBox = player.getStrongBox();

        strongBox.putRes(Res_Enum.STONE, 10);
        strongBox.putRes(Res_Enum.COIN, 10);

        assertTrue(player.addProduction(production));

        assertFalse(production.isSatisfiable(player));
    }

    /**
     * Testing if the production is really satisfiable
     */
    @Test
    public void isSatisfiable2DifferentTrue() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);
        StrongBox strongBox = player.getStrongBox();

        Production production1 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        Production production2 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        strongBox.putRes(Res_Enum.STONE, 10);
        strongBox.putRes(Res_Enum.COIN, 10);

        assertTrue(player.addProduction(production1));

        assertTrue(production2.isSatisfiable(player));
    }

    /**
     * Testing if the production is really satisfiable
     */
    @Test
    public void isSatisfiable2DifferentFalse() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);
        StrongBox strongBox = player.getStrongBox();

        Production production1 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        Production production2 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.COIN),
                List.of(Res_Enum.SHIELD, Res_Enum.SERVANT, Res_Enum.SERVANT),
                1
        );

        strongBox.putRes(Res_Enum.STONE, 2);
        strongBox.putRes(Res_Enum.COIN, 1);


        assertTrue(production1.isSatisfiable(player));

        assertTrue(player.addProduction(production1));

        assertFalse(production2.isSatisfiable(player));
    }
}