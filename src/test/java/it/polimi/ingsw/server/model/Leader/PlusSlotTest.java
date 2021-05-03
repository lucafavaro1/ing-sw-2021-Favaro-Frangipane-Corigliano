package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.NotEnoughResourcesException;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlusSlotTest {

    /**
     * Testing the putRes method: add two resources of the same type
     */
    @Test
    public void putRes() {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);

        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r3 = Res_Enum.COIN;
        Res_Enum r6 = Res_Enum.STONE;

        try {
            slot.putRes(r2);
            slot.putRes(r6);
        } catch (Exception e) {
            fail();
        }
        assertEquals(slot.getResource().get(0), slot.getResType());
        assertEquals(slot.getResource().get(1), slot.getResType());
    }

    /**
     * Testing the putRes method: add three resources of the same type, throw exception
     */
    @Test(expected = SlotIsFullException.class)
    public void putRes1() throws Exception {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);

        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r3 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.STONE;
        slot.putRes(r2);
        slot.putRes(r6);
        slot.putRes(r3);
    }

    /**
     * Testing the putRes method: add two resources of different type, throw exception
     */
    @Test(expected = IncorrectResourceException.class)
    public void putRes2() throws Exception {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);

        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.COIN;
        slot.putRes(r2);
        slot.putRes(r6);
    }

    /**
     * Testing the useRes method: add two resources of the same type, remove two, check
     */
    @Test
    public void useRes() {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);

        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.STONE;
        try {
            slot.putRes(r2);
            slot.putRes(r6);
            slot.useRes(Res_Enum.STONE, 2);
        } catch (Exception e) {
            fail();
        }
        assertTrue(slot.getResource().isEmpty());
    }

    /**
     * Testing the useRes method: add two resources of the same type, try removing three
     */
    @Test
    public void useResMore() {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        int removed=0;

        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.STONE;
        try {
            slot.putRes(r2);
            slot.putRes(r6);
            removed = slot.useRes(Res_Enum.STONE, 3);
        } catch (Exception e) {
            fail();
        }
        assertTrue(slot.getResource().isEmpty());
        assertTrue(removed == 2);
    }

    /**
     * Testing the tryAdding method: valid insert
     */
    @Test
    public void tryAdding(){
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        assertTrue(slot.tryAdding(Res_Enum.STONE));
    }

    /**
     * Testing the tryAdding method: not valid insert for type
     */
    @Test
    public void tryAdding1() throws Exception{
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        assertFalse(slot.tryAdding(Res_Enum.COIN));
    }

    /**
     * Testing the tryAdding method: too many insert
     */
    @Test
    public void tryAdding2() throws Exception{
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        assertTrue(slot.tryAdding(Res_Enum.STONE));
        assertTrue(slot.tryAdding(Res_Enum.STONE));
        assertFalse(slot.tryAdding(Res_Enum.STONE));
    }

}