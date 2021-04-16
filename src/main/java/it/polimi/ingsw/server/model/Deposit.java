package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * interface in order to use and add resources
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

    // TODO: add addRes?
}
