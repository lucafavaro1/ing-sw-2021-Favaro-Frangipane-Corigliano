package it.polimi.ingsw.Market;
import it.polimi.ingsw.RequirementsAndProductions.Production;
import it.polimi.ingsw.RequirementsAndProductions.Res_Enum;
import it.polimi.ingsw.Player.*;
import java.util.Random;

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
     * @param player refers to the player id
     */
    public Res_Enum convertRes(Player player){

        if(this.marbleColor==Marble_Enum.WHITE){
            return null;
        }
        else if(this.marbleColor==Marble_Enum.RED){
            player.getFaithTrack().increasePos(1);
            return null;
        }
        else if(this.marbleColor==Marble_Enum.BLUE){
            return Res_Enum.SHIELD;
        }
        else if(this.marbleColor==Marble_Enum.YELLOW){
            return Res_Enum.COIN;
        }
        else if(this.marbleColor==Marble_Enum.GREY){
            return Res_Enum.STONE;
        }
        else{
            return Res_Enum.SERVANT;
        }
    }


}
