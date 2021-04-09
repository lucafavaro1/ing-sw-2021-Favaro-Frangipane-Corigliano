package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Player.HumanPlayer;

/**
 * interface that models the different requirements with a "isSatisfiable" method
 */
public interface Requirements {
    /**
     * method that checks if the requirement is satisfied by the player passed as parameter
     *
     * @param player the player on which the satisfaction of the requirements will be checked
     * @return true if the player satisfies the requirements, false otherwise
     */
    boolean isSatisfiable(HumanPlayer player);
}
