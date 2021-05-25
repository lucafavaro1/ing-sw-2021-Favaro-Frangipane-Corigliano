package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Leader.ResDiscount;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * class modeling the requirements of resources
 */
public class ResRequirements extends Serializable implements Requirements {
    protected final List<Res_Enum> resourcesReq;

    public String ANSI_RESET = "\u001B[0m";
    public String ANSI_GREY = "\u001B[37m";
    public String ANSI_GREEN = "\u001B[32m";
    public String ANSI_RED = "\u001B[91m";
    public String ANSI_YELLOW = "\u001B[93m";
    public String ANSI_BLUE = "\u001B[94m";
    public String ANSI_PURPLE = "\u001B[95m";
    public String ANSI_WHITE = "\u001B[97m";

    public ResRequirements(List<Res_Enum> resourcesReq) {
        this.resourcesReq = resourcesReq;
        this.serializationType = SerializationType.RES_REQUIREMENTS;
    }

    public List<Res_Enum> getResourcesReq() {
        return resourcesReq;
    }

    /**
     * Checks if a player satisfies the requirements using the passed leader cards with Discount ability
     *
     * @param player       player on whom to check if the requirements are satisfied
     * @param resDiscounts list of leader ability of type ResDiscount
     * @return true if the player satisfies the requirements, false otherwise
     */
    public boolean isSatisfiable(HumanPlayer player, List<ResDiscount> resDiscounts) {
        Map<Res_Enum, Integer> mapRequirements = Res_Enum.getFrequencies(resourcesReq);

        // applies the discount of the leader card to the map of the requirements
        if (resDiscounts != null) {
            for (ResDiscount resDiscount : resDiscounts) {
                if (mapRequirements.get(resDiscount.getResourceType()) > 0) {
                    mapRequirements.merge(
                            resDiscount.getResourceType(),
                            resDiscount.getDiscountValue(),
                            (a, b) -> a - b);
                }
            }
        }

        return checkSatisfiability(player, mapRequirements);
    }

    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        return checkSatisfiability(player, Res_Enum.getFrequencies(resourcesReq));
    }

    /**
     * Checks if the map of requirements passed is satisfied by the player (considering all the resources)
     *
     * @param player          player on whom to check if the requirements are satisfied
     * @param mapRequirements map of requirements to check
     * @return true if the player satisfies the requirements, false otherwise
     */
    private boolean checkSatisfiability(HumanPlayer player, Map<Res_Enum, Integer> mapRequirements) {
        return player.getTotalResources().values().stream().reduce(0, Integer::sum) >= mapRequirements.values().stream().reduce(0, Integer::sum) &&
                Arrays.stream(Res_Enum.values())
                        .filter(res_enum -> res_enum != Res_Enum.QUESTION && mapRequirements.get(res_enum) > 0)
                        .allMatch(res_enum ->
                                player.getTotalResources().get(res_enum) >= mapRequirements.get(res_enum)
                        );
    }

    @Override
    public String toString() {
        Map<Res_Enum, Integer> frequencies = Res_Enum.getFrequencies(Optional.ofNullable(resourcesReq).orElse(List.of()));


        frequencies.forEach(
                (res_enum, quantity) -> {
                    if (quantity == 0)
                        frequencies.remove(res_enum);
                }
        );

        return translateCost(frequencies);
    }

    public String translateCost(Map<Res_Enum, Integer> map) {
        String stringa = "";

        for (Res_Enum x : map.keySet()) {
            String stringa2;
            stringa2 = map.get(x).toString();
            stringa = stringa.concat(translateResource(x)).concat("= " + stringa2 + " ");

        }

        return stringa;

    }

    public String translateResource(Res_Enum x) {
        if (x.equals(Res_Enum.COIN)) {
            return "\u001B[93m MONETA \u001B[0m";
        } else if (x.equals(Res_Enum.SERVANT)) {
            return "\u001B[95m SERVO \u001B[0m";
        } else if (x.equals(Res_Enum.SHIELD)) {
            return "\u001B[94m SCUDO \u001B[0m";
        } else if (x.equals(Res_Enum.STONE)) {
            return "\u001B[37m PIETRA \u001B[0m";
        } else  {
            return "\u001B[97m SCEGLI \u001B[0m";
        }

    }

}
