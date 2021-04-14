package it.polimi.ingsw.server.model.Market;

import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * Class that implements the marbles in the market
 */

public class MarketMarble {
    private Marble_Enum marbleColor;

    public Marble_Enum getMarbleColor() {
        return marbleColor;
    }

    public void setMarbleColor(Marble_Enum marbleColor) {
        this.marbleColor = marbleColor;
    }

    /**
     * Method use to convert a marble into the corresponding resource
     *
     * @param player refers to the player id
     */
    // TODO: check if it's right (faith in particular)
    public Res_Enum convertRes(Player player) {
        if (marbleColor.getEquivalentResource() == null) {
            player.getFaithTrack().increasePos(1);
            return null;
        }

        return marbleColor.getEquivalentResource();
    }


}
