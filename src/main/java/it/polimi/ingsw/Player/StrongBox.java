package it.polimi.ingsw.Player;

import java.util.*;

/**
 * Class representing the strongbox object of the game
 */
public class StrongBox {
    HashMap<Res_Enum, Integer> allRes = new HashMap<>();

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
        int x = allRes.get(ris);
        for (int i = 0; i < n; i++) {
            allRes.replace(ris, x + 1);
            x++;
        }
    }

    /**
     * Method to remove a certain quantity of a certain resource from the strongbox
     *
     * @param ris type of resource i want to remove
     * @param n   quantity of the resource
     * @throws NotEnoughResourcesException if there are not enough occurrences of that type of resource into the strongbox
     */
    public void useRes(Res_Enum ris, int n) throws NotEnoughResourcesException {
        int x = allRes.get(ris);
        if (x < n)
            throw new NotEnoughResourcesException("Risorse nel forziere non sufficienti!");
        for (int i = 0; i < n; i++) {
            allRes.replace(ris, x - 1);
            x--;
        }
    }
}
