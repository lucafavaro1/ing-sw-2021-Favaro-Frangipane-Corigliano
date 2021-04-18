package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * Class describing the leader ability that gives the player a discount upon buying a specified material
 */
public class ResDiscount extends LeaderAbility {
    private int discountValue;
    private Res_Enum resourceType;

    public ResDiscount(Res_Enum resourceType, int discountValue) {
        abilityType = Abil_Enum.DISCOUNT;
        this.resourceType = resourceType;
        this.discountValue = discountValue;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public Res_Enum getResourceType() {
        return resourceType;
    }

    public void setDiscountValue(int discountValue) {
        this.discountValue = discountValue;
    }

    public void setResourceType(Res_Enum resourceType) {
        this.resourceType = resourceType;
    }

    @Override
    public boolean isAllowed() {
        return abilityType == Abil_Enum.DISCOUNT && (resourceType == Res_Enum.STONE || resourceType == Res_Enum.COIN ||
                resourceType == Res_Enum.SERVANT || resourceType == Res_Enum.SHIELD) && discountValue >= 1;
    }

    @Override
    public String toString() {
        return "{"+super.toString() + ": " + resourceType + " " + discountValue+"}";
    }
}
