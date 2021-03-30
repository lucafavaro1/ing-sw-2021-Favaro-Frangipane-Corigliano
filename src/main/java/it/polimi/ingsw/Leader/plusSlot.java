package it.polimi.ingsw.Leader;


import java.util.ArrayList;
import it.polimi.ingsw.Player.Res_Enum;


public class plusSlot extends leaderAbility {
    private ArrayList<Resource> resource;
    private Res_Enum resType;


    public plusSlot(Res_Enum r){
        this.resource=new ArrayList<Resource>();
        this.resType=r;
    }

    public ArrayList<Resource> getResource() {
        return resource;
    }

    public void setResource(ArrayList<Resource> resource) {
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

    public void putRes(Resource r) throws Exception {
        if(resource.size()>=2){
            throw new Exception("Gli slot sono già pieni!");
        }
        else if(r.getResType()!=resType){
            throw new Exception("Gli slot non possono contenere quel tipo di materiale!");
        }

        else{
            resource.add(r);
        }
    }


    /**
     * Method used to remove resources from the leader card slots
     * @param n describes the number of resources to be removed
     * @throws Exception if the slots are empty
     */
    public void removeRes(int n) throws Exception{
        if(n==0) return;
        if(n==1){
            resource.remove(0);
        }

        else if(n>1)
            resource.clear();

    }

}

