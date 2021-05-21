package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

import java.util.HashMap;
import java.util.Optional;

/**
 * Class representing the strongbox object of the game
 */
public class StrongBox extends Serializable implements Deposit {
    private final HashMap<Res_Enum, Integer> allRes = new HashMap<>();

    /**
     * Basic constructor of the strongbox setting to 0 the quantity of each resource
     */
    public StrongBox(HumanPlayer player) {
        this.serializationType = SerializationType.STRONG_BOX;
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
        return Optional.ofNullable(allRes.get(ris)).orElse(0);
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
     * Get total resources actually into the strongbox
     * @return a map of all the contained resources
     */
    public HashMap<Res_Enum, Integer> getAllRes() {
        return allRes;
    }

    @Override
    public int useRes(Res_Enum ris, int n) {
        if (n < 0)
            return 0;

        int removed = Math.min(n, getRes(ris));

        allRes.merge(ris, removed, (a, b) -> a - b);
        return removed;
    }

    @Override
    public boolean tryAdding(Res_Enum res) {
        putRes(res, 1);
        return true;
    }

    @Override
    public String toString() {
        return "Strongbox: {" + "\n" +
                "\tCOIN = " + allRes.get(Res_Enum.COIN) +
                "\tSHIELD = " + allRes.get(Res_Enum.SHIELD) +
                "\tSERVANT = " + allRes.get(Res_Enum.SERVANT) +
                "\tSTONE = " + allRes.get(Res_Enum.STONE) +  "\n" +
                '}';
    }

}

