package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the WarehouseDepots object.
 */

public class WarehouseDepots {
    private ArrayList<Res_Enum> dpLevel1;
    private ArrayList<Res_Enum> dpLevel2;
    private ArrayList<Res_Enum> dpLevel3;

    /**
     * WarehouseDepots constructor
     */
    public WarehouseDepots() {
        dpLevel1 = new ArrayList<>(1);
        dpLevel2 = new ArrayList<>(2);
        dpLevel3 = new ArrayList<>(3);
    }

    /**
     * "Get" method per the shelves of the Depots
     *
     * @param n represents the number of the shelf (1-2-3)
     * @return an array containing the resources of that shelf
     */
    public ArrayList<Res_Enum> get_dp(int n) {
        switch (n) {
            case 1:
                return dpLevel1;
            case 2:
                return dpLevel2;
            case 3:
                return dpLevel3;
            default:
                return null;
        }
    }

    /**
     * Method to add a certain quantity of a certain type of a resource, into a particular level of the Deposit.
     * If i wanna try adding to a partial busy level i need to check if the space is enought and if the resource type is the same
     * If i wanna try adding to an empty level first i need to check if the others level don't contain that type of resource
     * and only that i can check if the space is enough
     *
     * @param ris      describes what type of resource i want to add (one of the Res_Enum)
     * @param numitems is the number of resources i am trying to add to that shelf
     * @param numlevel is the number of the shelf in which i want to store my resources
     * @throws MixedResourcesException      if you try to mix different type of resources in the same shelf
     * @throws NotEnoughSpaceException      if you overflow the capacity of the single shelf of the deposit
     * @throws SameResInTwoShelvesException if you try to add the same type of resource in two different shelves at the same time
     */
    public void add_dp(Res_Enum ris, int numitems, int numlevel) throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        if (get_dp(numlevel).size() > 0) {
            if ((get_dp(numlevel).size() + numitems <= numlevel) && (get_dp(numlevel).get(0).equals(ris))) {
                for (int k = 1; k < numitems + 1; k++) {
                    get_dp(numlevel).add(ris);
                }
                return;
            }
            if (!get_dp(numlevel).get(0).equals(ris))
                throw new MixedResourcesException("Non puoi mescolare diversi tipi di risorsa!");
            if (get_dp(numlevel).size() + numitems > numlevel)
                throw new NotEnoughSpaceException("Non c'è abbastanza posto nel magazzino!");
        }
        if ((get_dp(1).size() > 0 && !get_dp(1).get(0).equals(ris)) || (get_dp(1).size() == 0))
            if ((get_dp(2).size() > 0 && !get_dp(2).get(0).equals(ris)) || (get_dp(2).size() == 0))
                if ((get_dp(3).size() > 0 && !get_dp(3).get(0).equals(ris)) || (get_dp(3).size() == 0)) {
                    if (get_dp(numlevel).size() + numitems <= numlevel) {
                        for (int k = 1; k < numitems + 1; k++) {
                            get_dp(numlevel).add(ris);
                        }
                        return;
                    } else
                        throw new NotEnoughSpaceException("Non c'è abbastanza posto nel magazzino!");
                }
        throw new SameResInTwoShelvesException("Non puoi mettere lo stesso tipo di risorse in due diversi livelli del magazzino!");
    }

    /**
     * Method to move a single resource from a shelf to another one that is initially empty.
     *
     * @param ris        describes what type of resource i want to add (one of the Res_Enum)
     * @param from_level is the number of the shelf from which i remove my resources
     * @param to_level   is the number of the shelf in which i want to store my resources
     * @throws NotEnoughSpaceException if you overflow the capacity of the single shelf of the Deposit to where you are moving resources.
     * @throws MixedResourcesException if you try to mix different types of resources in the same shelf
     */
    public void move_res(Res_Enum ris, int from_level, int to_level) throws Exception {
        if (get_dp(to_level).size() > 0 && !get_dp(to_level).get(0).equals(ris))
            throw new MixedResourcesException("Non puoi mescolare diversi tipi di risorsa!");
        if ((get_dp(to_level).size() + 1 <= to_level) && ((get_dp(to_level).size() > 0 && get_dp(to_level).get(0).equals(ris)) || get_dp(to_level).size() == 0)) {
            get_dp(to_level).add(ris);
            get_dp(from_level).remove(ris);
            return;
        }
        throw new NotEnoughSpaceException("Non c'è abbastanza posto nel magazzino!");
    }

    /**
     * Method to remove a certain quantity of a certain type of a resource, from a particular level of the Deposit
     *
     * @param ris      ris describes what type of resource i want to add (one of the Res_Enum)
     * @param numitems is the number of resources i am trying to remove to that shelf
     * @param numlevel is the number of the shelf from which i want to remove my resources
     * @throws NotEnoughResourcesException if i try to remove more resources than the quantity that is stored in that shelf
     */
    public void rem_dp(Res_Enum ris, int numitems, int numlevel) throws Exception {
        if ((get_dp(numlevel).size() >= numitems) && (get_dp(numlevel).get(0).equals(ris))) {
            for (int k = 1; k < numitems + 1; k++) {
                get_dp(numlevel).remove(ris);
            }
            return;
        }
        if (get_dp(numlevel).size() < numitems)
            throw new NotEnoughResourcesException("Risorse nel magazzino non sufficienti!");
    }

    // TODO: develop
    // TODO: add javadoc
    // TODO: test (test also if the the deposits are changed)
    // TODO: remove res from player too
    public int useRes(Res_Enum res, int quantity) {
        // checks for each deposit if it contains the resources wanted
        for (int i = 1; i <= 3; i++) {
            List<Res_Enum> dp = get_dp(i);

            // if the department contains that type of resource removes them
            if (dp.contains(res)) {
                int removed;
                for (removed = 0; removed < quantity; removed++) {
                    if (!dp.remove(res))
                        break;
                }

                return removed;
            }
        }
        return 0;
    }

    /**
     * Swap the resources contained in two shelves, if it is dimensionally possible
     *
     * @param first  the number of the first shelf
     * @param second the number of the second shelf
     * @throws NotEnoughSpaceException if the two shelves don't have compatible dimensions
     */
    public void swap(int first, int second) throws NotEnoughSpaceException {
        int size1, size2;
        size1 = get_dp(first).size();
        size2 = get_dp(second).size();
        if ((size1 > second) || (size2 > first))
            throw new NotEnoughSpaceException("Scambio non fattibile per spazio mancante");

        ArrayList<Res_Enum> temp = new ArrayList<>(size1);

        for (int i = 0; i < size1; i++) {
            temp.add(get_dp(first).get(0));
            get_dp(first).remove(size1 - 1 - i);
        }
        for (int i = 0; i < size2; i++) {
            get_dp(first).add(get_dp(second).get(0));
            get_dp(second).remove(size2 - 1 - i);
        }
        for (int i = 0; i < size1; i++) {
            get_dp(second).add(temp.get(0));
            temp.remove(size1 - 1 - i);
        }
    }

    // TODO: javadoc, test
    public boolean tryAdding(Res_Enum res_enum) {
        boolean added = false;
        int dp = 0;

        // searching for a shelf with the resources we are inserting
        for (int i = 1; i <= 3; i++) {
            if (get_dp(i).contains(res_enum))
                dp = i;
        }

        // searching for an empty shelf
        if (dp == 0) {
            for (int i = 1; i <= 3; i++) {
                if (get_dp(i).isEmpty())
                    dp = i;
            }
        }

        // if there is a possible shelf in which to put the resource, try inserting it
        if (dp != 0) {
            // trying to swap the shelf of the res enum with the shelf with maximun space possible
            for (int swapDp = 3; swapDp > dp; swapDp--) {
                try {
                    swap(dp, swapDp);
                    break;
                } catch (NotEnoughSpaceException ignored) {
                }
            }

            // trying to add the resource in the right shelf
            try {
                add_dp(res_enum, 1, dp);
                added = true;
            } catch (MixedResourcesException | SameResInTwoShelvesException e) {
                e.printStackTrace();
            } catch (NotEnoughSpaceException ignored) {
            }
        }

        return added;
    }

}
