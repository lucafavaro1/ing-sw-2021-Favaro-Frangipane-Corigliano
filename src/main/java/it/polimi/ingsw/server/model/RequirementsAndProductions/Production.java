package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: to be developed
public class Production extends ResRequirements {

    private boolean available = true;
    private final List<Res_Enum> productionResources;
    private final int cardFaith;

    public Production(List<Res_Enum> resourcesReq, List<Res_Enum> productionResources, int cardFaith) {
        super(resourcesReq);
        this.productionResources = productionResources;
        this.cardFaith = cardFaith;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getCardFaith() {
        return cardFaith;
    }

    public List<Res_Enum> getProductionResources() {
        return productionResources;
    }

    @Override
    // TODO: javadoc, test, optimize
    public boolean isSatisfiable(HumanPlayer player) {
        Map<Res_Enum, Integer> resourcesAvailable = player.getAvailableResources();
        Map<Res_Enum, Integer> resourcesRequired = Res_Enum.getFrequencies(resourcesReq);

        return Arrays.stream(Res_Enum.values()).allMatch(res_enum ->
                resourcesAvailable.get(res_enum)>=resourcesRequired.get(res_enum)
        );
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
