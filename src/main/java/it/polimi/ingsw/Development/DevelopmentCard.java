package it.polimi.ingsw.Development;

import it.polimi.ingsw.RequirementsAndProductions.Production;
import it.polimi.ingsw.RequirementsAndProductions.Requirements;
import it.polimi.ingsw.RequirementsAndProductions.ResRequirements;

/**
 * Class that models the development card
 */
final public class DevelopmentCard implements Comparable<DevelopmentCard> {
    private final Tuple cardType;
    private final ResRequirements cardCost;
    private final Production production;
    private final int cardVictoryPoints;

    public DevelopmentCard(Tuple cardType, Production production, ResRequirements cardCost, int cardVictoryPoints) {
        this.cardType = cardType;
        this.production = production;
        this.cardCost = cardCost;
        this.cardVictoryPoints = cardVictoryPoints;
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

    /**
     * Checks if the card is allowed in the game or there have been errors creating it
     *
     * @return true if the card is allowed, false otherwise
     */
    public boolean isAllowed() {
        return cardType != null && production != null && cardCost != null && cardVictoryPoints >= 0 &&
                cardType.getLevel() >= Tuple.getMinLevel() && cardType.getLevel() <= Tuple.getMaxLevel() &&
                cardType.getType() != null;
    }

    // TODO: develop activate production method
    public void activateProduction() {

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
        return Integer.compare(this.getCardType().getLevel(), otherCard.getCardType().getLevel());
    }
}

//TODO: remove once devCards are generated from file
/*
// example of generation of card and json code
public static void main(String[] args) {
        Gson gson = new Gson();
        DevelopmentCard dvCard = new DevelopmentCard(
                new Tuple(TypeDevCards_Enum.BLUE, 1),
                new Production(
                        List.of(Res_Enum.STONE, Res_Enum.STONE, Res_Enum.SERVANT, Res_Enum.SHIELD, Res_Enum.SHIELD),
                        List.of(Res_Enum.SERVANT, Res_Enum.SERVANT, Res_Enum.SERVANT, Res_Enum.STONE),
                        1),
                new ResRequirements(
                        List.of(
                                Res_Enum.SERVANT, Res_Enum.SERVANT, Res_Enum.SERVANT,
                                Res_Enum.COIN, Res_Enum.SERVANT, Res_Enum.STONE
                        )),
                5);

        System.out.println(gson.toJson(dvCard));
    }
    */