package it.polimi.ingsw.RequirementsAndProductions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Enumeration of the different resource types in the game
 */
public enum Res_Enum {
    COIN, STONE, SERVANT, SHIELD;

    public static Map<Res_Enum, Integer> getFrequencies(List<Res_Enum> list) {
        Map<Res_Enum, Integer> resFrequencies = new EnumMap<>(Res_Enum.class);
        Arrays.stream(Res_Enum.values())
                .filter(res_enum -> Collections.frequency(list, res_enum) > 0)
                .forEach(res_enum -> resFrequencies.put(res_enum, Collections.frequency(list, res_enum)));
        return resFrequencies;
    }
}
