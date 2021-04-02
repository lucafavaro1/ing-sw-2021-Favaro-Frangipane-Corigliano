package it.polimi.ingsw.Development;

import it.polimi.ingsw.RequirementsAndProductions.Production;
import it.polimi.ingsw.RequirementsAndProductions.Requirements;

public class DevelopmentCard {
    private Tuple cardType;
    private Production production;
    private Requirements cardCost;
    private int cardVictoryPoints;

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

    public void activateProduction(){

    }
}
