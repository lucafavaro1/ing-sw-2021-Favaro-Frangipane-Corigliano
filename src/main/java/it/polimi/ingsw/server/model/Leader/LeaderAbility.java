package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

/**
 * Abstract class describing the ability on the leader card
 */
public abstract class LeaderAbility extends Serializable {
    protected Abil_Enum abilityType;

    public LeaderAbility() {
        this.serializationType = SerializationType.LEADER_ABILITY;
    }

    public abstract boolean isAllowed();

    public Abil_Enum getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(Abil_Enum abilityType) {
        this.abilityType = abilityType;
    }
    
}
