package it.polimi.ingsw.RequirementsAndProductions;

import java.util.*;

/**
 * Enumeration of the different resource types in the game
 */
public enum Res_Enum {
    COIN, STONE, SERVANT, SHIELD,
    QUESTION {
        @Override
        public Res_Enum chooseResource() {
            // TODO: modify when are ready the interfaces to the console game and graphic game
            int chosen = 0; // make the player choose the resource

            return Res_Enum.values()[chosen];
        }
    };

    public Res_Enum chooseResource() {
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
}
