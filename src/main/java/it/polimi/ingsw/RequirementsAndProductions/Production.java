package it.polimi.ingsw.RequirementsAndProductions;

import java.util.EnumSet;
import java.util.List;

// TODO: to be developed
public class Production extends ResRequirements {
    private final List<Res_Enum> productionResources;
    private final int cardFaith;

    public Production(List<Res_Enum> resourcesReq, List<Res_Enum> productionResources, int cardFaith) {
        super(resourcesReq);
        this.productionResources = productionResources;
        this.cardFaith = cardFaith;
    }

    public int getCardFaith() {
        return cardFaith;
    }

    public List<Res_Enum> getProductionResources() {
        return productionResources;
    }
}
