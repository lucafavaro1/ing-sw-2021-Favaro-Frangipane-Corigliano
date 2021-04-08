package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Player.HumanPlayer;

import java.util.List;

public class ResRequirements implements Requirements {
    protected final List<Res_Enum> resourcesReq;

    public ResRequirements(List<Res_Enum> resourcesReq) {
        this.resourcesReq = resourcesReq;
    }

    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        int counter = 0;
        int size = resourcesReq.size();
        for(int i=0;i<size;i++) {
            if(resourcesReq.get(i) == Res_Enum.COIN) counter++;
        }
        if(player.getTotalResources().get(Res_Enum.COIN) < counter) return false;
        counter = 0;

        for(int i=0;i<size;i++) {
            if(resourcesReq.get(i) == Res_Enum.STONE) counter++;
        }
        if(player.getTotalResources().get(Res_Enum.STONE) < counter) return false;
        counter = 0;

        for(int i=0;i<size;i++) {
            if(resourcesReq.get(i) == Res_Enum.SHIELD) counter++;
        }
        if(player.getTotalResources().get(Res_Enum.SHIELD) < counter) return false;
        counter = 0;

        for(int i=0;i<size;i++) {
            if(resourcesReq.get(i) == Res_Enum.SERVANT) counter++;
        }
        if(player.getTotalResources().get(Res_Enum.SERVANT) < counter) return false;
        return true;
    }

    @Override
    public String toString() {
        return Res_Enum.getFrequencies(resourcesReq).toString();
    }
}
