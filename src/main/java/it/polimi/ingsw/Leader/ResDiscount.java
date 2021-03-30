package it.polimi.ingsw.Leader;
import it.polimi.ingsw.Player.Res_Enum;

public class ResDiscount {
    private int discountValue;
    private Res_Enum resoruceTpye;

    public int getDiscountValue() {
        return discountValue;
    }

    public Res_Enum getResoruceTpye() {
        return resoruceTpye;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public void setResoruceTpye(Res_Enum resoruceTpye) {
        this.resoruceTpye = resoruceTpye;
    }
}
