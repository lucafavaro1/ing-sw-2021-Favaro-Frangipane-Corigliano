package it.polimi.ingsw.Leader;

import it.polimi.ingsw.Player.HumanPlayer;

/**
 * Class that describes the ability on the leader card
 */
public class LeaderAbility {
    private Abil_Enum abilityType;
    protected HumanPlayer player;

    public void setPlayer(HumanPlayer player) {
        this.player = player;
    }

    public Abil_Enum getAbilityType() {
        return abilityType;
    }

    public void setAbilityType(Abil_Enum abilityType) {
        this.abilityType = abilityType;
    }
}
