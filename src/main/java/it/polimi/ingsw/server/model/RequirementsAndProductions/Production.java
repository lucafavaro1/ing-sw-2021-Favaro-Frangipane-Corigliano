package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.SerializationType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class that models a production
 */
public class Production extends ResRequirements {
    private boolean available = true;
    private final List<Res_Enum> productionResources;
    private final int cardFaith;

    /**
     * Constructor of the production
     *
     * @param resourcesReq        resources required for the activation of the production
     * @param productionResources resources given by the activation of the production
     * @param cardFaith           faith given by the activation of the production
     */
    public Production(List<Res_Enum> resourcesReq, List<Res_Enum> productionResources, int cardFaith) {
        super(resourcesReq);
        this.productionResources = productionResources;
        this.cardFaith = cardFaith;
        this.serializationType = SerializationType.PRODUCTION;
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

    /**
     * checks if the production is possible considered all the other productions added by the player
     *
     * @param player human player on whom to check if the requisites are satisfied
     * @return true if the requirements of the production are satisfied, false otherwise
     */
    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        if (player == null || !available)
            return false;

        Map<Res_Enum, Integer> resourcesAvailable = player.getAvailableResources();
        Map<Res_Enum, Integer> resourcesRequired = Res_Enum.getFrequencies(resourcesReq);

        // checking in first instance if the number of the resources is enough (used to consider the question resources)
        if (resourcesRequired.values().stream().reduce(0, Integer::sum) > resourcesAvailable.values().stream().reduce(0, Integer::sum))
            return false;

        // once the number of resources are satisfied, the question resources can be eliminated from the check
        resourcesRequired.remove(Res_Enum.QUESTION);
        resourcesAvailable.remove(Res_Enum.QUESTION);

        return Arrays.stream(Res_Enum.values())
                .filter(res_enum -> res_enum != Res_Enum.QUESTION)
                .allMatch(res_enum ->
                        resourcesAvailable.get(res_enum) >= resourcesRequired.get(res_enum)
                );
    }

    @Override
    public String toString() {
        String productionString = Arrays.stream(Res_Enum.values())
                .filter(res -> Collections.frequency(productionResources, res) > 0)
                .map(res -> res + "=" + Collections.frequency(productionResources, res))
                .collect(Collectors.joining(", "));

        if (cardFaith != 0) {
            if (!productionString.equals(""))
                productionString += ", ";

            if (UserInterface.getInstance() != null && UserInterface.getInstance().getClass() == CLIUserInterface.class)
                productionString += "\u001B[91m FAITH\u001B[0m = " + cardFaith;
            else {
                productionString += "FAITH=" + cardFaith;
            }
        }

        if (UserInterface.getInstance() != null && UserInterface.getInstance().getClass() == CLIUserInterface.class)
            return super.toString() + " -> " + "{" + colorProd(productionString) + "}";
        else {
            return super.toString() + " -> " + "{" + productionString + "}";
        }

    }

    public static String colorProd(String x) {
        String y;
        y = x.replaceAll("STONE", Res_Enum.STONE.toColoredString());
        y = y.replaceAll("SHIELD", Res_Enum.SHIELD.toColoredString());
        y = y.replaceAll("SERVANT", Res_Enum.SERVANT.toColoredString());
        y = y.replaceAll("COIN", Res_Enum.COIN.toColoredString());
        y = y.replaceAll("QUESTION", Res_Enum.QUESTION.toColoredString());
        return y;
    }
}
