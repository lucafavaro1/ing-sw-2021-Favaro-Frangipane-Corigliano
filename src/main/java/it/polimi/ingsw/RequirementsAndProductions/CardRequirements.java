package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Development.Tuple;
import it.polimi.ingsw.Player.HumanPlayer;

import java.util.List;

public class CardRequirements implements Requirements {
    private List<Tuple> cardReq;

    // TODO: develop once we put the DvBoard in the Player
    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        return false;
    }

    @Override
    public String toString() {
        return "CardRequirements: \n" + cardReq;
    }
}
