package it.polimi.ingsw.server.model.RequirementsAndProductions;

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

    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        Map<Res_Enum, Integer> mapRequirements = Res_Enum.getFrequencies(resourcesReq);

        return Arrays.stream(Res_Enum.values())
                .filter(res_enum -> mapRequirements.get(res_enum)>0)
                .allMatch(res_enum ->
                        player.getTotalResources().get(res_enum) >= mapRequirements.get(res_enum)
                );
    }

    @Override
    public String toString() {
        return Res_Enum.getFrequencies(resourcesReq).toString();
    }
}
