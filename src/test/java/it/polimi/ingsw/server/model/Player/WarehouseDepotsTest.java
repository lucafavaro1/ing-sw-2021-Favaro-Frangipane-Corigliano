package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class WarehouseDepotsTest {
    @Test
    public void testShelfGet() {
        WarehouseDepots mydeposit = new WarehouseDepots();
        assertEquals(mydeposit.get_dp(1).size(), 0);
        assertEquals(mydeposit.get_dp(2).size(), 0);
        assertEquals(mydeposit.get_dp(3).size(), 0);
    }

    @Test
    public void testAddDp() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1, 1);
        mydeposit.add_dp(Res_Enum.SHIELD,2, 2);
        mydeposit.add_dp(Res_Enum.SERVANT,3, 3);
        assertEquals(mydeposit.get_dp(1).get(0), Res_Enum.COIN);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.SHIELD);
        assertEquals(mydeposit.get_dp(3).get(0), Res_Enum.SERVANT);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 2);
        assertEquals(mydeposit.get_dp(3).size(), 3);
    }

    @Test (expected = Exception.class)
    public void testAddTwoShelf() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1, 1);
        mydeposit.add_dp(Res_Enum.COIN,1, 2);
    }

    @Test (expected = NotEnoughSpaceException.class)
    public void testAddOver1() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1, 1);
        mydeposit.add_dp(Res_Enum.SERVANT,3, 2);
    }

    @Test (expected = NotEnoughSpaceException.class)
    public void testAddOver2() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1, 1);
        mydeposit.add_dp(Res_Enum.SHIELD,2,2);
        mydeposit.add_dp(Res_Enum.SERVANT,2, 3);
        mydeposit.add_dp(Res_Enum.COIN,1, 1);
    }

    @Test (expected = MixedResourcesException.class)
    public void testAddMix() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,2);
        mydeposit.add_dp(Res_Enum.SERVANT,1,2);
    }

    @Test
    public void testMove() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 0);
        mydeposit.move_res(Res_Enum.COIN, 1,2);
        assertEquals(mydeposit.get_dp(1).size(), 0);
        assertEquals(mydeposit.get_dp(2).size(), 1);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.COIN);
    }

    @Test (expected = NotEnoughSpaceException.class)
    public void testMoveOver() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,2,2);
        assertEquals(mydeposit.get_dp(1).size(), 0);
        assertEquals(mydeposit.get_dp(2).size(), 2);
        mydeposit.move_res(Res_Enum.COIN, 2,1);
        mydeposit.move_res(Res_Enum.COIN, 2,1);
    }

    @Test (expected = MixedResourcesException.class)
    public void testMoveMix() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,2);
        mydeposit.add_dp(Res_Enum.SERVANT,1,1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 1);
        mydeposit.move_res(Res_Enum.COIN, 2,3);
        mydeposit.move_res(Res_Enum.SERVANT, 1,3);
    }

    @Test
    public void testRemove() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        mydeposit.rem_dp(Res_Enum.COIN, 1, 1);
        assertEquals(mydeposit.get_dp(1).size(), 0);
    }

    @Test (expected = NotEnoughResourcesException.class)
    public void testRemoveOver() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        mydeposit.rem_dp(Res_Enum.COIN, 3, 1);
    }

    @Test
    public void testSwap() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,1);
        mydeposit.add_dp(Res_Enum.SERVANT,1,2);
        mydeposit.swap(1,2);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 1);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.COIN);
        assertEquals(mydeposit.get_dp(1).get(0), Res_Enum.SERVANT);
    }

    @Test (expected = NotEnoughSpaceException.class)
    public void testSwapOver() throws Exception {
        WarehouseDepots mydeposit = new WarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN,1,1);
        mydeposit.add_dp(Res_Enum.SERVANT,2,2);
        mydeposit.swap(1,2);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 2);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.COIN);
        assertEquals(mydeposit.get_dp(1).get(0), Res_Enum.SERVANT);
    }

}