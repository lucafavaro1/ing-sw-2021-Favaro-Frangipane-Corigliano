package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class WarehouseDepotsTest {
    /**
     * Testing the get_dp method for the shelves
     */

    @Test
    public void testShelfGet() {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        assertEquals(mydeposit.get_dp(1).size(), 0);
        assertEquals(mydeposit.get_dp(2).size(), 0);
        assertEquals(mydeposit.get_dp(3).size(), 0);
    }

    /**
     * Testing the add_dp method to add resources to a shelf
     * @throws Exception if there is not enough space or mixed resources in the same shelf
     */
    @Test
    public void testAddDp() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        mydeposit.add_dp(Res_Enum.SHIELD, 2, 2);
        mydeposit.add_dp(Res_Enum.SERVANT, 3, 3);
        assertEquals(mydeposit.get_dp(1).get(0), Res_Enum.COIN);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.SHIELD);
        assertEquals(mydeposit.get_dp(3).get(0), Res_Enum.SERVANT);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 2);
        assertEquals(mydeposit.get_dp(3).size(), 3);
    }

    /**
     * Testing the add in case of same type of resource in two different shelves
     * @throws Exception same type in two different levels
     */
    @Test(expected = Exception.class)
    public void testAddTwoShelf() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        mydeposit.add_dp(Res_Enum.COIN, 1, 2);
    }

    /**
     * Testing the add in case of attempt to add more resources than actual free space
     * @throws Exception if you overflow the max space
     */
    @Test(expected = NotEnoughSpaceException.class)
    public void testAddOver1() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        mydeposit.add_dp(Res_Enum.SERVANT, 3, 2);
    }

    /**
     * Testing the add in case of attempt to add more resources than actual free space
     * @throws Exception if you overflow the max space
     */
    @Test(expected = NotEnoughSpaceException.class)
    public void testAddOver2() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        mydeposit.add_dp(Res_Enum.SHIELD, 2, 2);
        mydeposit.add_dp(Res_Enum.SERVANT, 2, 3);
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
    }
    /**
     * Testing the add in case of attempt to add mixed resources in the same shelf
     * @throws Exception if you mix resources in the same shelf
     */
    @Test(expected = MixedResourcesException.class)
    public void testAddMix() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 2);
        mydeposit.add_dp(Res_Enum.SERVANT, 1, 2);
    }

    /**
     * Testing the move method from a shelf to another
     * @throws Exception if there is not enough space in the destination shelf
     */
    @Test
    public void testMove() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 0);
        mydeposit.move_res(Res_Enum.COIN, 1, 2);
        assertEquals(mydeposit.get_dp(1).size(), 0);
        assertEquals(mydeposit.get_dp(2).size(), 1);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.COIN);
    }

    /**
     * Testing the move method from a shelf to another in case of no space
     * @throws Exception if there is not enough space in the destination shelf
     */
    @Test(expected = NotEnoughSpaceException.class)
    public void testMoveOver() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 2, 2);
        assertEquals(mydeposit.get_dp(1).size(), 0);
        assertEquals(mydeposit.get_dp(2).size(), 2);
        mydeposit.move_res(Res_Enum.COIN, 2, 1);
        mydeposit.move_res(Res_Enum.COIN, 2, 1);
    }

    /**
     * Testing the move method from a shelf to another in case of mixing types
     * @throws Exception if you try to mix different types of resource in the same shelf
     */
    @Test(expected = MixedResourcesException.class)
    public void testMoveMix() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 2);
        mydeposit.add_dp(Res_Enum.SERVANT, 1, 1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 1);
        mydeposit.move_res(Res_Enum.COIN, 2, 3);
        mydeposit.move_res(Res_Enum.SERVANT, 1, 3);
    }

    /**
     * Testing the remove method from a shelf
     * @throws Exception if you want to remove more items than the actually present ones
     */
    @Test
    public void testRemove() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        mydeposit.rem_dp(Res_Enum.COIN, 1, 1);
        assertEquals(mydeposit.get_dp(1).size(), 0);
    }

    /**
     * Testing the remove method from a shelf
     * @throws Exception if you want to remove more items than the actually present ones
     */
    @Test(expected = NotEnoughResourcesException.class)
    public void testRemoveOver() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        mydeposit.rem_dp(Res_Enum.COIN, 3, 1);
    }

    /**
     * Testing the swap method
     * @throws Exception if the swap is dimensionally wrong
     */
    @Test
    public void testSwap() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        mydeposit.add_dp(Res_Enum.SERVANT, 1, 2);
        mydeposit.swap(1, 2);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 1);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.COIN);
        assertEquals(mydeposit.get_dp(1).get(0), Res_Enum.SERVANT);
    }

    /**
     * Testing the swap method
     * @throws Exception if the swap is dimensionally wrong
     */
    @Test(expected = NotEnoughSpaceException.class)
    public void testSwapOver() throws Exception {
        WarehouseDepots mydeposit = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        mydeposit.add_dp(Res_Enum.COIN, 1, 1);
        mydeposit.add_dp(Res_Enum.SERVANT, 2, 2);
        mydeposit.swap(1, 2);
        assertEquals(mydeposit.get_dp(1).size(), 1);
        assertEquals(mydeposit.get_dp(2).size(), 2);
        assertEquals(mydeposit.get_dp(2).get(0), Res_Enum.COIN);
        assertEquals(mydeposit.get_dp(1).get(0), Res_Enum.SERVANT);
    }

    /**
     * Testing if removes resources from an empty warehouse
     */
    @Test
    public void testUseResEmpty() {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        assertEquals(0, warehouseDepots.useRes(Res_Enum.STONE, 1));
        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(0, warehouseDepots.get_dp(2).size());
        assertEquals(0, warehouseDepots.get_dp(3).size());
    }

    /**
     * Testing if removes the maximum amount of resources from the warehouse
     */
    @Test
    public void testUseResLessResourcesInDepot() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        warehouseDepots.add_dp(Res_Enum.STONE, 1, 3);

        assertEquals(1, warehouseDepots.useRes(Res_Enum.STONE, 2));
        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(0, warehouseDepots.get_dp(2).size());
        assertEquals(0, warehouseDepots.get_dp(3).size());
    }

    /**
     * Testing if removes the maximum amount of resources from the warehouse
     */
    @Test
    public void testUseResLessResourcesRequired() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();
        warehouseDepots.add_dp(Res_Enum.STONE, 3, 3);

        assertEquals(2, warehouseDepots.useRes(Res_Enum.STONE, 2));
        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(0, warehouseDepots.get_dp(2).size());
        assertEquals(1, warehouseDepots.get_dp(3).size());
    }

    /**
     * Testing if orders the shelves with all different amount of resources
     */
    @Test
    public void testOrderShelves1() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.add_dp(Res_Enum.COIN, 2, 2);
        warehouseDepots.add_dp(Res_Enum.STONE, 1, 3);

        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(1, warehouseDepots.get_dp(3).size());

        warehouseDepots.orderShelves();

        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(1, warehouseDepots.get_dp(2).size());
        assertEquals(2, warehouseDepots.get_dp(3).size());

        assertEquals(Res_Enum.STONE, warehouseDepots.get_dp(2).get(0));
        assertEquals(Res_Enum.COIN, warehouseDepots.get_dp(3).get(0));
    }

    /**
     * Testing if orders the shelves with some equal amount of resources
     */
    @Test
    public void testOrderShelves2() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.add_dp(Res_Enum.COIN, 2, 2);

        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(0, warehouseDepots.get_dp(3).size());

        warehouseDepots.orderShelves();

        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(0, warehouseDepots.get_dp(2).size());
        assertEquals(2, warehouseDepots.get_dp(3).size());
        assertEquals(Res_Enum.COIN, warehouseDepots.get_dp(3).get(0));
    }

    /**
     * Testing if orders the shelves with some equal amount of resources
     */
    @Test
    public void testOrderShelvesEmpty() {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.orderShelves();

        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(0, warehouseDepots.get_dp(2).size());
        assertEquals(0, warehouseDepots.get_dp(3).size());
    }

    /**
     * Testing if the contains() method returns false if the warehouse is empty
     */
    @Test
    public void testContainsEmpty() {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        assertFalse(warehouseDepots.contains(Res_Enum.COIN));
    }

    /**
     * Testing if the contains() method returns true if the warehouse is empty
     */
    @Test
    public void testContains() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.add_dp(Res_Enum.COIN, 2, 2);

        assertTrue(warehouseDepots.contains(Res_Enum.COIN));
        assertFalse(warehouseDepots.contains(Res_Enum.STONE));
    }

    /**
     * Testing if the tryAdding method deals well with empty warehouse
     */
    @Test
    public void testTryAddingEmpty() {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        assertFalse(warehouseDepots.contains(Res_Enum.COIN));

        // adding a resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.COIN));

        assertTrue(warehouseDepots.contains(Res_Enum.COIN));
    }

    /**
     * Testing if the tryAdding method deals well with already present resources
     */
    @Test
    public void testTryAdding() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.add_dp(Res_Enum.COIN, 1, 1);
        // adding a resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.COIN));

        assertTrue(warehouseDepots.contains(Res_Enum.COIN));
        assertEquals(2, warehouseDepots.get_dp(3).size());

        // adding a resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.COIN));

        assertEquals(3, warehouseDepots.get_dp(3).size());
    }

    /**
     * Testing if the tryAdding method returns false and doesn't add resources if there is no space
     */
    @Test
    public void testTryAddingFull() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.add_dp(Res_Enum.STONE, 3, 3);
        warehouseDepots.add_dp(Res_Enum.COIN, 1, 1);

        // adding a resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.COIN));

        assertTrue(warehouseDepots.contains(Res_Enum.COIN));
        assertEquals(2, warehouseDepots.get_dp(2).size());

        // adding a resource to the warehouse, failing
        assertFalse(warehouseDepots.tryAdding(Res_Enum.COIN));

        assertEquals(0, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(3, warehouseDepots.get_dp(3).size());
    }

    /**
     * Testing if the tryAdding method deals well with partially full warehouse (white box)
     */
    @Test
    public void testTryAdding1() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        WarehouseDepots warehouseDepots = ((HumanPlayer) (new Game(2)).getPlayers().get(0)).getWarehouseDepots();

        warehouseDepots.add_dp(Res_Enum.STONE, 2, 2);
        warehouseDepots.add_dp(Res_Enum.COIN, 1, 1);

        // adding a resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.SHIELD));
        assertTrue(warehouseDepots.contains(Res_Enum.SHIELD));
        assertEquals(1, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(1, warehouseDepots.get_dp(3).size());

        // adding another resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.COIN));
        assertEquals(1, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(2, warehouseDepots.get_dp(3).size());

        // adding another resource to the warehouse
        assertTrue(warehouseDepots.tryAdding(Res_Enum.STONE));
        assertEquals(1, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(3, warehouseDepots.get_dp(3).size());

        // adding another resource to the warehouse, failing
        assertFalse(warehouseDepots.tryAdding(Res_Enum.SHIELD));
        assertEquals(1, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(3, warehouseDepots.get_dp(3).size());

        // adding another resource to the warehouse, failing
        assertFalse(warehouseDepots.tryAdding(Res_Enum.SERVANT));
        assertEquals(1, warehouseDepots.get_dp(1).size());
        assertEquals(2, warehouseDepots.get_dp(2).size());
        assertEquals(3, warehouseDepots.get_dp(3).size());
    }
}