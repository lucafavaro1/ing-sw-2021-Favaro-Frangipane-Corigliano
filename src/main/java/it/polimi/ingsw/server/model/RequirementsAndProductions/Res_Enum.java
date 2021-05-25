package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Enumeration of the different resource types in the game
 */
public enum Res_Enum {
    COIN, STONE, SERVANT, SHIELD,
    QUESTION {
        @Override
        public Res_Enum chooseResource(HumanPlayer player) {
            return (
                    new MakePlayerChoose<>(
                            "Choose the resource to pick",
                            List.of(COIN, STONE, SERVANT, SHIELD)
                    )
            ).choose(player);
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

    /**
     * static method that converts a map of the frequencies of Res_Enum and converts it to a List with
     * an amount of Res_Enums equals to the frequency in the map
     *
     * @param map map linking every Res_Enum to the amount
     * @return a list of Res_Enum
     */
    public static List<Res_Enum> getList(Map<Res_Enum, Integer> map) {
        List<Res_Enum> list = new ArrayList<>();

        map.forEach((res_enum, freq) -> {
            for (int i = 0; i < freq; i++) {
                list.add(res_enum);
            }
        });

        return list;
    }

    public String toColoredString(){
        if(this.equals(Res_Enum.STONE)){
            return "\u001B[37m PIETRA \u001B[0m";
        }
        else if(this.equals(Res_Enum.COIN)){
            return "\u001B[93m MONETA \u001B[0m";
        }
        else if(this.equals(Res_Enum.SHIELD)){
            return "\u001B[94m SCUDO \u001B[0m";
        }
        else if(this.equals(Res_Enum.SERVANT)){
            return "\u001B[95m SERVITORE \u001B[0m";
        }
        else{
            return "\u001B[97m SCEGLI \u001B[0m";
        }
    }

}
