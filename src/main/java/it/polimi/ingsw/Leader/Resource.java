package it.polimi.ingsw.Leader;
import it.polimi.ingsw.Player.Res_Enum;

public class Resource {
    private Res_Enum resType;

    public Resource(Res_Enum s){
        this.resType=s;
    }
    public Res_Enum getResType() {
        return resType;
    }
    public void setResType(Res_Enum resType) {
        this.resType = resType;
    }

}
