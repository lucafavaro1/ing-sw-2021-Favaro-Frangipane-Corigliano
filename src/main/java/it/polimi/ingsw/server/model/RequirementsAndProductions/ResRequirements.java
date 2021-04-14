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

    // TODO: test
    // TODO: add javadoc
    public boolean isSatisfiable(HumanPlayer player, List<ResDiscount> resDiscounts) {
        Map<Res_Enum, Integer> mapRequirements = Res_Enum.getFrequencies(resourcesReq);

        // applies the discount of the leader card to the map of the requirements
        for (ResDiscount resDiscount : resDiscounts) {
            if (mapRequirements.get(resDiscount.getResourceType()) > 0) {
                mapRequirements.merge(resDiscount.getResourceType(), -resDiscount.getDiscountValue(), Integer::sum);
            }
        }

        return checkSatisfiability(player, mapRequirements);
    }

    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        return checkSatisfiability(player, Res_Enum.getFrequencies(resourcesReq));
    }

    private boolean checkSatisfiability(HumanPlayer player, Map<Res_Enum, Integer> mapRequirements){
        return Arrays.stream(Res_Enum.values())
                .filter(res_enum -> mapRequirements.get(res_enum) > 0)
                .allMatch(res_enum ->
                        player.totalResources().get(res_enum) >= mapRequirements.get(res_enum)
                );
    }

    @Override
    public String toString() {
        return Res_Enum.getFrequencies(resourcesReq).toString();
    }
}
