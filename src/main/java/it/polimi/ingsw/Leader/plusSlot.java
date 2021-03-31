package it.polimi.ingsw.Leader;


import java.util.ArrayList;
import it.polimi.ingsw.Player.Res_Enum;

/**
 * Class that describes the leader ability that grants the player two more resource slots for a specified resource type
 */

public class plusSlot extends leaderAbility {
    private ArrayList<Res_Enum> resource;
    private Res_Enum resType;


    public plusSlot(Res_Enum r){
        this.resource=new ArrayList<Res_Enum>();
        this.resType=r;
    }

    public ArrayList<Res_Enum> getResource() {
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
     * @param r describes the resource to be added into the slot
     * @throws Exception if the player is trying to add resources when the slots are full or if the player is trying to
     *                   add a resource whose type is different from the leader card slot resource type
     */

    public void putRes(Res_Enum r) throws SlotIsFullException, IncorrectResourceException {
        if(resource.size()>=2){
            throw new SlotIsFullException("Gli slot sono già pieni!");
        }
        else if(r!=resType){
            throw new IncorrectResourceException("Gli slot non possono contenere quel tipo di materiale!");
        }

        else{
            resource.add(r);
        }
    }


    /**
     * Method used to remove resources from the leader card slots
     * @param n describes the number of resources to be removed
     */
    public void removeRes(int n){
        if(n==0) return;
        if(n==1){
            resource.remove(0);
        }

        else if(n>1)
            resource.clear();

    }

}

