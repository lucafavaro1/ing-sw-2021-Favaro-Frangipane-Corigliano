package it.polimi.ingsw.Leader;

import it.polimi.ingsw.Player.Res_Enum;
import org.junit.Test;
import static org.junit.Assert.*;

public class plusSlotTest {

    @Test
    public void putRes() {
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Resource r2= new Resource(Res_Enum.STONE);
        Resource r3= new Resource(Res_Enum.COIN);
        Resource r6= new Resource(Res_Enum.STONE);
        try{
            slot.putRes(r2);
            slot.putRes(r6);
        }
        catch (Exception e){
            System.out.println("Eccezione");
        }
        assertEquals(slot.getResource().get(0).getResType(), slot.getResType());
        assertEquals(slot.getResource().get(1).getResType(), slot.getResType());
    }

    @Test
    public void removeRes() {
        plusSlot slot=new plusSlot(Res_Enum.STONE);
        Resource r2= new Resource(Res_Enum.STONE);
        Resource r3= new Resource(Res_Enum.COIN);
        Resource r6= new Resource(Res_Enum.STONE);
        try{
            slot.putRes(r2);
            slot.putRes(r6);
            slot.removeRes(2);
        }
        catch (Exception e){
            System.out.println("Eccezione");
        }
        assertTrue(slot.getResource().isEmpty());
    }
}