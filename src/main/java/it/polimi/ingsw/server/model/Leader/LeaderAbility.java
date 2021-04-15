package it.polimi.ingsw.server.model.Leader;

/**
 * Class that describes the ability on the leader card
 */
public class LeaderAbility {
    protected Abil_Enum abilityType;

    public Abil_Enum getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(Abil_Enum abilityType) {
        this.abilityType = abilityType;
    }
}
