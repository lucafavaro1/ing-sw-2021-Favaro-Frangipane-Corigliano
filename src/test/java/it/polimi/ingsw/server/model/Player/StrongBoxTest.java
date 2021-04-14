package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StrongBoxTest {

    @Test
    public void testPutRes() {
        StrongBox mybox = new StrongBox();
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
        StrongBox mybox = new StrongBox();
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
        StrongBox mybox = new StrongBox();
        mybox.useRes(Res_Enum.COIN, 1);
    }


}