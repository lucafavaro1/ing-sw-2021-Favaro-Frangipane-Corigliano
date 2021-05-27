package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

/**
 * Class that models the development card
 */
final public class DevelopmentCard extends Serializable implements Comparable<DevelopmentCard> {

    private final Tuple cardType;
    private final ResRequirements cardCost;
    private final Production production;
    private final int cardVictoryPoints;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREY = "\u001B[37m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[91m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[94m";
    public static final String ANSI_PURPLE = "\u001B[95m";
    public static final String ANSI_WHITE = "\u001B[97m";

    /**
     * Constructor that creates a development card
     *
     * @param cardType          Tuple representing the type of card
     * @param production        Production activable once bought the card
     * @param cardCost          ResRequirements representing the cost to buy the card
     * @param cardVictoryPoints victory points given from the purchase of the card
     */
    public DevelopmentCard(Tuple cardType, Production production, ResRequirements cardCost, int cardVictoryPoints) {
        this.serializationType = SerializationType.DEVELOPMENT_CARD;
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
     * Checks if the card passed has the level equal to the level of this card minus 1
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
     * Checks if the card passed has the level equal to the level of this card minus 1
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
        return "Card Type: " +translateColor(cardType) + translateLevel(cardType)+
                "{\n\tCost: " + cardCost +
                "\n\tProduction: " + production +
                "\n\tVictory Points: " + cardVictoryPoints +
                "\n}";
    }

    /**
     * Override of the comparison method, true if the card has the same level, false otherwise
     */
    @Override
    public int compareTo(DevelopmentCard otherCard) {
        if (otherCard == null)
            return 1;

        return Integer.compare(otherCard.getCardType().getLevel(), this.getCardType().getLevel());
    }

    public String translateColor(Tuple type){
        if(type.getType().equals(TypeDevCards_Enum.BLUE)){
            return ANSI_BLUE + "BLUE" + ANSI_RESET;
        }
        else if(type.getType().equals(TypeDevCards_Enum.YELLOW)){
            return ANSI_YELLOW + "YELLOW" + ANSI_RESET;
        }
        else if(type.getType().equals(TypeDevCards_Enum.GREEN)){
            return ANSI_GREEN + "GREEN" + ANSI_RESET;
        }
        else {
            return ANSI_PURPLE + "PURPLE" + ANSI_RESET;
        }
    }
    public String translateLevel(Tuple type){
        if(type.getLevel()==1){
           return " Level 1 ";
        }
        else if(type.getLevel()==2){
            return " Level 2 ";
        }
        else{
            return " Level 3 ";
        }
    }
}