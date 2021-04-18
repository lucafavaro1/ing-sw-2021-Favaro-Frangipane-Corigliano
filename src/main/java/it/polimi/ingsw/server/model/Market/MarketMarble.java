package it.polimi.ingsw.server.model.Market;

import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * Class that implements the marbles in the market
 */
public class MarketMarble {
    private Marble_Enum marbleColor;

    public MarketMarble() {
    }

    public MarketMarble(Marble_Enum marbleColor) {
        this.marbleColor = marbleColor;
    }

    /**
     * Method used to convert a marble into the corresponding resource
     *
     * @param player refers to the player id
     */
    public Res_Enum convertRes(Player player) {
        if (marbleColor.equals(Marble_Enum.RED)) {
            player.getFaithTrack().increasePos(1);
        }

        return marbleColor.getEquivalentResource();
    }

    public Marble_Enum getMarbleColor() {
        return marbleColor;
    }

    public void setMarbleColor(Marble_Enum marbleColor) {
        this.marbleColor = marbleColor;
    }

    @Override
    public String toString() {
        return marbleColor.toString();
    }
}
