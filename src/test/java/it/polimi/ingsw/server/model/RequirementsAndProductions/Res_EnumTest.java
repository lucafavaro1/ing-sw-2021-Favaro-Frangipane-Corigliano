package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Res_EnumTest {

    /**
     * testing if the getFrequencies method returns a map of all the frequencies
     */
    @Test
    public void getFrequenciesTest() {
        List<Res_Enum> listRes = List.of(
                Res_Enum.COIN, Res_Enum.SHIELD, Res_Enum.COIN,
                Res_Enum.COIN, Res_Enum.STONE);

        Map<Res_Enum, Integer> mapRes = Res_Enum.getFrequencies(listRes);

        assertEquals(3, mapRes.get(Res_Enum.COIN).intValue());
        assertEquals(1, mapRes.get(Res_Enum.SHIELD).intValue());
        assertEquals(1, mapRes.get(Res_Enum.STONE).intValue());
        assertEquals(0, mapRes.get(Res_Enum.SERVANT).intValue());
    }

    /**
     * testing if the getFrequencies method returns a map of all the frequencies
     */
    @Test
    public void getListTest() {
        Map<Res_Enum, Integer> frequencies = new HashMap<>();
        frequencies.put(Res_Enum.COIN, 3);
        frequencies.put(Res_Enum.SHIELD, 1);

        List<Res_Enum> listRes = Res_Enum.getList(frequencies);

        assertEquals(3, Collections.frequency(listRes, Res_Enum.COIN));
        assertEquals(1, Collections.frequency(listRes, Res_Enum.SHIELD));
        assertEquals(0, Collections.frequency(listRes, Res_Enum.SERVANT));
        assertEquals(0, Collections.frequency(listRes, Res_Enum.STONE));
    }
}