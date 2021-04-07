package it.polimi.ingsw.RequirementsAndProductions;

import java.util.*;

/**
 * Enumeration of the different resource types in the game
 */
public enum Res_Enum {
    COIN, STONE, SERVANT, SHIELD,
    QUESTION, FAITH {
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

    public static Map<Res_Enum, Integer> getFrequencies(List<Res_Enum> list) {
        Map<Res_Enum, Integer> resFrequencies = new EnumMap<>(Res_Enum.class);
        Arrays.stream(Res_Enum.values())
                .filter(res_enum -> Collections.frequency(list, res_enum) > 0)
                .forEach(res_enum -> resFrequencies.put(res_enum, Collections.frequency(list, res_enum)));
        return resFrequencies;
    }
}
