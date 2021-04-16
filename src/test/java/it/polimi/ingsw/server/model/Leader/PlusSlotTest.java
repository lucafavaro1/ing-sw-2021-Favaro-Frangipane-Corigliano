package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlusSlotTest {

    @Test
    public void putRes() {
        Game game = new Game(2);

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

    @Test(expected = SlotIsFullException.class)
    public void putRes1() throws Exception {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);

        Game game = new Game(2);
        

        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r3 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.STONE;
        slot.putRes(r2);
        slot.putRes(r6);
        slot.putRes(r3);
    }

    @Test(expected = IncorrectResourceException.class)
    public void putRes2() throws Exception {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        Game game = new Game(2);
        
        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r3 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.COIN;
        slot.putRes(r2);
        slot.putRes(r6);
        slot.putRes(r3);
    }

    @Test(expected = IncorrectResourceException.class)
    public void putRes3() throws Exception {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        Game game = new Game(2);
        
        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r3 = Res_Enum.STONE;
        Res_Enum r6 = Res_Enum.COIN;
        slot.putRes(r6);
        slot.putRes(r2);
        slot.putRes(r3);
    }


    @Test
    public void useRes() {
        PlusSlot slot = new PlusSlot(Res_Enum.STONE);
        Game game = new Game(2);
        
        Res_Enum r2 = Res_Enum.STONE;
        Res_Enum r3 = Res_Enum.COIN;
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
}