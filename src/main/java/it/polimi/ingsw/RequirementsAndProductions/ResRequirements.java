package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Player.HumanPlayer;

import java.util.List;

public class ResRequirements implements Requirements {
    protected final List<Res_Enum> resourcesReq;

    public ResRequirements(List<Res_Enum> resourcesReq) {
        this.resourcesReq = resourcesReq;
    }

    // TODO: to be developed
    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        return false;
    }

    @Override
    public String toString() {
        return Res_Enum.getFrequencies(resourcesReq).toString();
    }
}
