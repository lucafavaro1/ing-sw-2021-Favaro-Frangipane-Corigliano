package it.polimi.ingsw.Player;

import org.junit.Test;

import static org.junit.Assert.*;

public class HumanPlayerTest {
    @Test
    public void testCountPoints1 () throws Exception {
        HumanPlayer player = new HumanPlayer(1);
        player.getStrongBox().putRes(Res_Enum.COIN, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SERVANT, 2, 2);
        //player.getWarehouseDepots().add_dp(Res_Enum.SERVANT, 2, 2);
        player.getFaithTrack().increasePos(6);
        player.getFaithTrack().VaticanReport(1);
        assertEquals(player.countPoints(),4);
    }
    @Test
    public void testCountPoints2 () throws Exception {
        HumanPlayer player = new HumanPlayer(1);
        player.getStrongBox().putRes(Res_Enum.COIN, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SERVANT, 2, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SHIELD, 2, 3);
        player.getFaithTrack().increasePos(6);
        player.getFaithTrack().VaticanReport(1);
        assertEquals(player.countPoints(),5);
    }

}