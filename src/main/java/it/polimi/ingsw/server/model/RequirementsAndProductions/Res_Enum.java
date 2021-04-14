package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.*;

/**
 * Enumeration of the different resource types in the game
 */
public enum Res_Enum {
    COIN, STONE, SERVANT, SHIELD,
    QUESTION {
        public Res_Enum chooseResource(HumanPlayer player) {
            return (new MakePlayerChoose<>(List.of(COIN, SERVANT, STONE, SHIELD))).choose(player);
        }
    };

    public Res_Enum chooseResource(HumanPlayer player) {
        return this;
    }

    /**
     * static method that converts a list of res_Enum to a map of Res_Enum and the relative frequencies
     *
     * @param list list of Res_Enum
     * @return the map of the frequencies of each resource type
     */
    public static Map<Res_Enum, Integer> getFrequencies(List<Res_Enum> list) {
        Map<Res_Enum, Integer> resFrequencies = new EnumMap<>(Res_Enum.class);
        Arrays.stream(Res_Enum.values())
                .forEach(res_enum -> resFrequencies.put(res_enum, Collections.frequency(list, res_enum)));
        return resFrequencies;
    }

    // TODO test
    // TODO add javadoc
    public static List<Res_Enum> getList(Map<Res_Enum, Integer> map) {
        List<Res_Enum> list = new ArrayList<>();

        map.forEach((res_enum, freq) -> {
            for (int i = 0; i < freq; i++) {
                list.add(res_enum);
            }
        });

        return list;
    }
}
