package it.polimi.ingsw.server.model.Leader;

/**
 * Class that describes the ability on the leader card
 */
public abstract class LeaderAbility {
    protected Abil_Enum abilityType;

    public abstract boolean isAllowed();

    public Abil_Enum getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(Abil_Enum abilityType) {
        this.abilityType = abilityType;
    }

    @Override
    public String toString() {
        return abilityType.toString();
    }
}
