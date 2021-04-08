package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Development.BadSlotNumberException;
import it.polimi.ingsw.Player.HumanPlayer;

public interface Requirements {
    boolean isSatisfiable(HumanPlayer player) throws BadSlotNumberException;
}
