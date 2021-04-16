package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

/**
 * Class that describes the leader ability that grants the player an additional production
 */
public class MoreProduction extends LeaderAbility {
    public MoreProduction(Production production) {
        abilityType = Abil_Enum.PRODUCTION;
        this.production = production;
    }

    private Production production;

    public Production getProduction() {
        return production;
    }

    public void setProduction(Production production) {
        this.production = production;
    }
}