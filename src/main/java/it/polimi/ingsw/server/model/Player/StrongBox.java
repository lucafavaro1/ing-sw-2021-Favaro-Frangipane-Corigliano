package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.HashMap;

/**
 * Class representing the strongbox object of the game
 */
public class StrongBox implements Deposit {
    private final HashMap<Res_Enum, Integer> allRes = new HashMap<>();

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


    @Override
    public int useRes(Res_Enum ris, int n) {
        if (n < 0)
            return 0;

        int removed = Math.min(n, allRes.get(ris));

        allRes.merge(ris, removed, (a, b) -> a - b);
        return removed;
    }

    public HashMap<Res_Enum, Integer> getAllRes() {
        return allRes;
    }
}

