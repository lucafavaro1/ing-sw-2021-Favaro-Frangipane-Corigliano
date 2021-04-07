package it.polimi.ingsw.RequirementsAndProductions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public String toString() {
        String productionString = Arrays.stream(Res_Enum.values())
                .filter(res -> Collections.frequency(productionResources, res) > 0)
                .map(res -> res + ": " + Collections.frequency(productionResources, res))
                .collect(Collectors.joining(", "));

        if (cardFaith != 0) {
            if (!productionString.equals(""))
                productionString += ", ";

            productionString += "FAITH: " + cardFaith;
        }

        return super.toString() + " -> " + "{" + productionString + "}";
    }
}
