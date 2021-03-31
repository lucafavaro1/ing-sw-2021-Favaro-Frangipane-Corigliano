package it.polimi.ingsw.Leader;
import it.polimi.ingsw.Player.Res_Enum;

/**
 * Class that describes the leader ability that gives the player a specified resource whenever he gets a white marble
 * from the market tray
 */
public class whiteMarble {
    private Res_Enum resourceType;

    public Res_Enum getResourceType() {
        return resourceType;
    }

    public void setResourceType(Res_Enum resourceType) {
        this.resourceType = resourceType;
    }
}
