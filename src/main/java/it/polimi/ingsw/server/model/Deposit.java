package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * Tnterface in order to use and add resources
 */
public interface Deposit {
    /**
     * Removes the resources passed as parameter, of the given amount
     *
     * @param res      the type of resource to remove
     * @param quantity the amount of resource to remove
     * @return how many resources has been effectively removed
     */
    int useRes(Res_Enum res, int quantity);

    /**
     * Tries to add the resource passed as parameter
     *
     * @param res resource to be added to the deposit
     * @return true if the resource has been successfully added, false otherwise
     */
    boolean tryAdding(Res_Enum res);
}
