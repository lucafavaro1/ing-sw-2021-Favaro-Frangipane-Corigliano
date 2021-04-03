package it.polimi.ingsw.Leader;
/**
 * Class that describes the leader card
 */

import it.polimi.ingsw.RequirementsAndProductions.Requirements;

import java.util.ArrayList;
    public class LeaderCard {

        private int cardID;
        private int cardVictoryPoints;

    private ArrayList<Requirements> cardRequirements;
    private LeaderAbility cardAbility;

    public int getCardID() {
        return cardID;
    }

    public int getCardVictoryPoints() {
        return cardVictoryPoints;
    }

    public ArrayList<Requirements> getCardRequirements() {
        return cardRequirements;
    }

    public LeaderAbility getCardAbility() {
        return cardAbility;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
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


