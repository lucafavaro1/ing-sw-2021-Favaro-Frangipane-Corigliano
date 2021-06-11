package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * Class that describes the leader ability that gives the player a specified resource whenever he gets a white marble
 * from the market tray (changes the correspondence between white marble = no resource )
 */
public class WhiteMarble extends LeaderAbility {
    private Res_Enum resourceType;

    public WhiteMarble(Res_Enum resourceType) {
        abilityType = Abil_Enum.WHITE_MARBLE;
        this.resourceType = resourceType;
    }

    public Res_Enum getResourceType() {
        return resourceType;
    }

    public void setResourceType(Res_Enum resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean isAllowed() {
        return abilityType == Abil_Enum.WHITE_MARBLE && (resourceType == Res_Enum.STONE || resourceType == Res_Enum.COIN ||
                resourceType == Res_Enum.SERVANT || resourceType == Res_Enum.SHIELD);
    }

    @Override
    public String toString() {

        if (UserInterface.getInstance() != null && UserInterface.getInstance().getClass() == CLIUserInterface.class) {
            return "{" + abilityType + ": " + resourceType.toColoredString() + "}";
        } else {
            return "{" + abilityType + ": " + resourceType + "}";
        }
    }
}
