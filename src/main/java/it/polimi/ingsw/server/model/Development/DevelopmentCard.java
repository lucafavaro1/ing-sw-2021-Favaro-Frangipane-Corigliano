package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;

/**
 * Class that models the development card
 */
final public class DevelopmentCard implements Comparable<DevelopmentCard> {
    private final Tuple cardType;
    private final ResRequirements cardCost;
    private final Production production;
    private final int cardVictoryPoints;

    /**
     * Constructor that creates a development card
     *
     * @param cardType          Tuple representing the type of card
     * @param production        Production activable once bought the card
     * @param cardCost          ResRequirements representing the cost to buy the card
     * @param cardVictoryPoints victory points given from the purchase of the card
     */
    public DevelopmentCard(Tuple cardType, Production production, ResRequirements cardCost, int cardVictoryPoints) {
        this.cardType = cardType;
        this.production = production;
        this.cardCost = cardCost;
        this.cardVictoryPoints = cardVictoryPoints;
    }

    /**
     * Checks if the card is allowed in the game or there have been errors creating it
     *
     * @return true if the card is allowed, false otherwise
     */
    public boolean isAllowed() {
        return cardType != null && cardType.getType() != null &&
                cardType.getLevel() >= Tuple.getMinLevel() && cardType.getLevel() <= Tuple.getMaxLevel() &&
                production != null && production.getResourcesReq() != null &&
                (production.getCardFaith() != 0 || (production.getProductionResources() != null && !production.getProductionResources().isEmpty())) &&
                cardCost != null && cardCost.getResourcesReq() != null &&
                cardVictoryPoints >= 0;
    }

    /**
     * checks if the card passed has the level equal to the level of this card minus 1
     *
     * @param prev a Development Card to check if it's the predecessor of the this card
     * @return true if the "this" card is the successor of the one passed, false otherwise
     */
    public boolean isSuccessorOf(DevelopmentCard prev) {
        if (prev == null)
            return this.cardType.getLevel() == 1;

        return isSuccessorOf(prev.cardType);
    }

    /**
     * checks if the card passed has the level equal to the level of this card minus 1
     *
     * @param prev a Development Card to check if it's the predecessor of the this card
     * @return true if the "this" card is the successor of the one passed, false otherwise
     */
    public boolean isSuccessorOf(Tuple prev) {
        if (prev == null) {
            return this.cardType.getLevel() == 1;
        } else {
            return prev.getLevel() == this.cardType.getLevel() - 1;
        }
    }

    public Tuple getCardType() {
        return cardType;
    }

    public Production getProduction() {
        return production;
    }

    public ResRequirements getCardCost() {
        return cardCost;
    }

    public int getCardVictoryPoints() {
        return cardVictoryPoints;
    }

    @Override
    public String toString() {
        return cardType +
                "{\n\tCard cost: " + cardCost +
                "\n\tproduction: " + production +
                "\n\tvictory points: " + cardVictoryPoints +
                "\n}";
    }

    @Override
    public int compareTo(DevelopmentCard otherCard) {
        if (otherCard == null)
            return 1;

        return Integer.compare(otherCard.getCardType().getLevel(), this.getCardType().getLevel());
    }
}