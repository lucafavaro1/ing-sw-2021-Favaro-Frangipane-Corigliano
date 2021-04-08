package it.polimi.ingsw.Leader;

import it.polimi.ingsw.RequirementsAndProductions.Requirements;

import java.util.ArrayList;

/**
 * Class that describes the leader card
 */
    public class LeaderCard {
        private int cardVictoryPoints;
        private ArrayList<Requirements> cardRequirements;
        private LeaderAbility cardAbility;


    public int getCardVictoryPoints() {
        return cardVictoryPoints;
    }

    public ArrayList<Requirements> getCardRequirements() {
        return cardRequirements;
    }

    public LeaderAbility getCardAbility() {
        return cardAbility;
    }


    public void setCardVictoryPoints(int cardVictoryPoints) {
        this.cardVictoryPoints = cardVictoryPoints;
    }

    public void setCardRequirements(ArrayList<Requirements> cardRequirements) {
        this.cardRequirements = cardRequirements;
    }

    public void setCardAbility(LeaderAbility cardAbility) {
        this.cardAbility = cardAbility;
    }
    }


