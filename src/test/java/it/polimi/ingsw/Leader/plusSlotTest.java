package it.polimi.ingsw.Leader;

import it.polimi.ingsw.Player.Res_Enum;
import org.junit.Test;
import static org.junit.Assert.*;

public class plusSlotTest {

    @Test
    public void putRes() {
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Res_Enum r2=  Res_Enum.STONE;
        Res_Enum r3= Res_Enum.COIN;
        Res_Enum r6= Res_Enum.STONE;
        try{
            slot.putRes(r2);
            slot.putRes(r6);
        }
        catch (Exception e){
            fail();
        }
        assertEquals(slot.getResource().get(0), slot.getResType());
        assertEquals(slot.getResource().get(1), slot.getResType());
    }

    @Test (expected = SlotIsFullException.class)
    public void putRes1() throws Exception {
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Res_Enum r2=  Res_Enum.STONE;
        Res_Enum r3= Res_Enum.STONE;
        Res_Enum r6= Res_Enum.STONE;
        slot.putRes(r2);
        slot.putRes(r6);
        slot.putRes(r3);
    }

    @Test (expected = IncorrectResourceException.class)
    public void putRes2() throws Exception {
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Res_Enum r2=  Res_Enum.STONE;
        Res_Enum r3= Res_Enum.STONE;
        Res_Enum r6= Res_Enum.COIN;
        slot.putRes(r2);
        slot.putRes(r6);
        slot.putRes(r3);
    }
    @Test (expected = IncorrectResourceException.class)
    public void putRes3() throws Exception {
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Res_Enum r2=  Res_Enum.STONE;
        Res_Enum r3= Res_Enum.STONE;
        Res_Enum r6= Res_Enum.COIN;
        slot.putRes(r6);
        slot.putRes(r2);
        slot.putRes(r3);
    }


    @Test
    public void removeRes(){
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Res_Enum r2=  Res_Enum.STONE;
        Res_Enum r3= Res_Enum.COIN;
        Res_Enum r6= Res_Enum.STONE;
        try{
            slot.putRes(r2);
            slot.putRes(r6);
            slot.removeRes(2);
        }
        catch (Exception e){
            fail();
        }
        assertTrue(slot.getResource().isEmpty());
    }
}