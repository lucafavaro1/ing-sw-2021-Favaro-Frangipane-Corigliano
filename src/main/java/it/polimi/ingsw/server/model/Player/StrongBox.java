package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.HashMap;

/**
 * Class representing the strongbox object of the game
 */
public class StrongBox {
    private HashMap<Res_Enum, Integer> allRes = new HashMap<>();

    public StrongBox() {
        allRes.put(Res_Enum.COIN, 0);
        allRes.put(Res_Enum.SERVANT, 0);
        allRes.put(Res_Enum.STONE, 0);
        allRes.put(Res_Enum.SHIELD, 0);
    }

    /**
     * Getter method returning the number of occurrences of a certain type of resource
     *
     * @param ris type of the resource
     * @return number of occurrences in the strongbox
     */
    public int getRes(Res_Enum ris) {
        return allRes.get(ris);
    }

    /**
     * Method to add a certain quantity of a certain resource into the strongbox
     *
     * @param ris type of resource i want to add
     * @param n   quantity of the resource
     */
    public void putRes(Res_Enum ris, int n) {
        allRes.merge(ris, n, Integer::sum);
    }

    /**
     * Method to remove a certain quantity of a certain resource from the strongbox
     *
     * @param ris type of resource i want to remove
     * @param n   quantity of the resource
     * @throws NotEnoughResourcesException if there are not enough occurrences of that type of resource into the strongbox
     */
    public void useRes(Res_Enum ris, int n) throws NotEnoughResourcesException {
        if (allRes.get(ris) < n)
            throw new NotEnoughResourcesException("Risorse nel forziere non sufficienti!");

        allRes.merge(ris, n, (a, b) -> a - b);
    }

    public HashMap<Res_Enum, Integer> getAllRes() {
        return allRes;
    }
}

