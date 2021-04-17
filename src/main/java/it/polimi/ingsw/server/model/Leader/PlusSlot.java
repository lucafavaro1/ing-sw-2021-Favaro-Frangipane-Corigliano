package it.polimi.ingsw.server.model.Leader;


import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that describes the leader ability that grants the player two more resource slots for a specified resource type
 */
public class PlusSlot extends LeaderAbility implements Deposit {
    private List<Res_Enum> resources = new ArrayList<>();
    private Res_Enum resType;

    public PlusSlot(Res_Enum r) {
        abilityType = Abil_Enum.SLOT;
        this.resType = r;
    }

    public List<Res_Enum> getResource() {
        return resources;
    }

    public void setResource(ArrayList<Res_Enum> resource) {
        this.resources = resource;
    }

    public Res_Enum getResType() {
        return resType;
    }

    public void setResType(Res_Enum resType) {
        this.resType = resType;
    }

    /**
     * Method used to add resources into the leader card slots
     *
     * @param r describes the resource to be added into the slot
     * @throws SlotIsFullException        if the player is trying to add resources when the slots are full
     * @throws IncorrectResourceException if the player is trying to add a resource whose type is different from the leader card slot resource type
     */
    public void putRes(Res_Enum r) throws SlotIsFullException, IncorrectResourceException {
        if (resources.size() >= 2) {
            throw new SlotIsFullException("Gli slot sono già pieni!");
        } else if (r != resType) {
            throw new IncorrectResourceException("Gli slot non possono contenere quel tipo di materiale!");
        } else {
            resources.add(r);
        }
    }

    @Override
    public boolean isAllowed() {
        return abilityType == Abil_Enum.SLOT && (resType == Res_Enum.STONE || resType == Res_Enum.COIN ||
                resType == Res_Enum.SERVANT || resType == Res_Enum.SHIELD) &&
                resources != null && (resources.isEmpty() || resources.contains(resType));
    }

    @Override
    public int useRes(Res_Enum res, int quantity) {
        if (res != resType || resources.isEmpty())
            return 0;

        int removed;
        for (removed = 0; removed < quantity; removed++) {
            if (!resources.remove(res))
                break;
        }

        return removed;
    }

    @Override
    public boolean tryAdding(Res_Enum res) {
        try {
            putRes(res);
            return true;
        } catch (SlotIsFullException ignored) {
        } catch (IncorrectResourceException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + super.toString() + ": " + resType + resources.size() + "}";
    }
}

