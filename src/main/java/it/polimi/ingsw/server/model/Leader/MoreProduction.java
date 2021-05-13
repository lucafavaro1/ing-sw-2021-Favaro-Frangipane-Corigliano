package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

/**
 * Class that describes the leader ability that grants the player an additional production
 */
public class MoreProduction extends LeaderAbility {
    private Production production;

    public MoreProduction(Production production) {
        abilityType = Abil_Enum.PRODUCTION;
        this.production = production;
    }

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }

    @Override
    public boolean isAllowed() {
        return abilityType == Abil_Enum.PRODUCTION && production != null;
    }

    @Override
    public String toString() {
        return "{" + abilityType + ": " + production + "}";
    }
}
