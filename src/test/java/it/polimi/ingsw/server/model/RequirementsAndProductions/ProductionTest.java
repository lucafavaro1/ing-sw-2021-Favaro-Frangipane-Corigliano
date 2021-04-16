package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.StrongBox;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ProductionTest {

    /**
     * Testing if the production is correctly
     */
    @Test
    // TODO finish test
    public void isSatisfiable() {
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
}