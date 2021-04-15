package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Leader.ResDiscount;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * class modeling the requirements of resources
 */
public class ResRequirements implements Requirements {
    protected final List<Res_Enum> resourcesReq;

    public ResRequirements(List<Res_Enum> resourcesReq) {
        this.resourcesReq = resourcesReq;
    }

    public List<Res_Enum> getResourcesReq() {
        return resourcesReq;
    }

    /**
     * Checks if a player satisfies the requirements using the passed leader cards with Discount ability
     * TODO test
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
     * Checks if the map of requirements passed is satisfied by the player
     * TODO test
     *
     * @param player          player on whom to check if the requirements are satisfied
     * @param mapRequirements map of requirements to check
     * @return true if the player satisfies the requirements, false otherwise
     */
    private boolean checkSatisfiability(HumanPlayer player, Map<Res_Enum, Integer> mapRequirements) {
        return Arrays.stream(Res_Enum.values())
                .filter(res_enum -> mapRequirements.get(res_enum) > 0)
                .allMatch(res_enum ->
                        player.getTotalResources().get(res_enum) >= mapRequirements.get(res_enum)
                );
    }

    @Override
    public String toString() {
        return Res_Enum.getFrequencies(resourcesReq).toString();
    }
}
