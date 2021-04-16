package it.polimi.ingsw.server.model.Leader;


import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that describes the leader ability that grants the player two more resource slots for a specified resource type
 */
public class PlusSlot extends LeaderAbility implements Deposit {
    private List<Res_Enum> resource = new ArrayList<>();
    private Res_Enum resType;

    public PlusSlot(Res_Enum r) {
        abilityType = Abil_Enum.SLOT;
        this.resType = r;
    }

    public List<Res_Enum> getResource() {
        return resource;
    }

    public void setResource(ArrayList<Res_Enum> resource) {
        this.resource = resource;
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
        if (resource.size() >= 2) {
            throw new SlotIsFullException("Gli slot sono già pieni!");
        } else if (r != resType) {
            throw new IncorrectResourceException("Gli slot non possono contenere quel tipo di materiale!");
        } else {
            resource.add(r);
        }
    }

    @Override
    public int useRes(Res_Enum res, int quantity) {
        if (res != resType || resource.isEmpty())
            return 0;

        int removed;
        for (removed = 0; removed < quantity; removed++) {
            if (!resource.remove(res))
                break;
        }

        return removed;
    }
}

