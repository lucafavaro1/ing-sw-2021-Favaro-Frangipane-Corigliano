package it.polimi.ingsw.RequirementsAndProductions;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Res_EnumTest {

    /**
     * testing if the getFrequencies method returns a map of all the frequencies
     */
    @Test
    public void getFrequencies() {
        List<Res_Enum> listRes = List.of(
                Res_Enum.COIN, Res_Enum.SHIELD, Res_Enum.COIN,
                Res_Enum.COIN, Res_Enum.STONE);

        Map<Res_Enum, Integer> mapRes = Res_Enum.getFrequencies(listRes);

        assertEquals(3, mapRes.get(Res_Enum.COIN).intValue());
        assertEquals(1, mapRes.get(Res_Enum.SHIELD).intValue());
        assertEquals(1, mapRes.get(Res_Enum.STONE).intValue());
        assertEquals(0, mapRes.get(Res_Enum.SERVANT).intValue());
    }
}