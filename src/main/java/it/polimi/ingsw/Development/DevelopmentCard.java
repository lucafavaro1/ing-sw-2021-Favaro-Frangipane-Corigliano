package it.polimi.ingsw.Development;

import it.polimi.ingsw.RequirementsAndProductions.Production;
import it.polimi.ingsw.RequirementsAndProductions.Requirements;

// TODO: add documentation
public class DevelopmentCard {
    private final Tuple cardType;
    private final Production production;
    private final Requirements cardCost;
    private final int cardVictoryPoints;

    public DevelopmentCard(Tuple cardType, Production production, Requirements cardCost, int cardVictoryPoints) {
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

    public Requirements getCardCost() {
        return cardCost;
    }

    public int getCardVictoryPoints() {
        return cardVictoryPoints;
    }

    // TODO: develop activate production method
    public void activateProduction() {

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
                new CardRequirements(
                        List.of(
                                new Tuple(TypeDevCards_Enum.BLUE, 2),
                                new Tuple(TypeDevCards_Enum.BLUE, 2),
                                new Tuple(TypeDevCards_Enum.GREEN, 1)
                        )),
                5);

        System.out.println(gson.toJson(dvCard));
    }
    */