package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Player.HumanPlayer;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class ResRequirements implements Requirements {
    private final List<Res_Enum> resourcesReq;

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
        return "Resource requirements: \n" + resourcesReq;
    }
}
