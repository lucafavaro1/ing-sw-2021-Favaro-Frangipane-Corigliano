package it.polimi.ingsw.Player;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrongBoxTest {

    @Test
    public void testPutRes() {
        Game g = new Game(1);
        HumanPlayer p = new HumanPlayer(g,1);
        StrongBox mybox = new StrongBox(p);
        mybox.putRes(Res_Enum.COIN, 1);
        mybox.putRes(Res_Enum.SERVANT, 2);
        mybox.putRes(Res_Enum.SHIELD, 3);
        mybox.putRes(Res_Enum.STONE, 4);
        assertEquals(mybox.getRes(Res_Enum.COIN), 1);
        assertEquals(mybox.getRes(Res_Enum.SERVANT), 2);
        assertEquals(mybox.getRes(Res_Enum.SHIELD), 3);
        assertEquals(mybox.getRes(Res_Enum.STONE), 4);
        mybox.putRes(Res_Enum.COIN, 2);
        assertEquals(mybox.getRes(Res_Enum.COIN), 3);
    }

    @Test
    public void testUseRes() throws NotEnoughResourcesException {
        Game g = new Game(1);
        HumanPlayer p = new HumanPlayer(g,1);
        StrongBox mybox = new StrongBox(p);
        mybox.putRes(Res_Enum.COIN, 1);
        mybox.putRes(Res_Enum.SERVANT, 2);
        mybox.putRes(Res_Enum.SHIELD, 3);
        mybox.putRes(Res_Enum.STONE, 4);
        mybox.useRes(Res_Enum.SERVANT, 1);
        mybox.useRes(Res_Enum.STONE, 2);
        assertEquals(mybox.getRes(Res_Enum.COIN), 1);
        assertEquals(mybox.getRes(Res_Enum.SERVANT), 1);
        assertEquals(mybox.getRes(Res_Enum.SHIELD), 3);
        assertEquals(mybox.getRes(Res_Enum.STONE), 2);
    }

    @Test(expected = NotEnoughResourcesException.class)
    public void testUseResExc() throws NotEnoughResourcesException {
        Game g = new Game(1);
        HumanPlayer p = new HumanPlayer(g,1);
        StrongBox mybox = new StrongBox(p);
        mybox.useRes(Res_Enum.COIN, 1);
    }


}