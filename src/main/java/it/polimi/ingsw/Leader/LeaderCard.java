package it.polimi.ingsw.Leader;
/**
 * Class that describes the leader card
 */

import it.polimi.ingsw.RequirementsAndProductions.*;


import java.util.ArrayList;
    public class LeaderCard {

        private int cardVictoryPoints;
        private ResRequirements resRequirements;
        private CardRequirements cardRequirements;
        private LeaderAbility cardAbility;

        public int getCardVictoryPoints() {
            return cardVictoryPoints;
        }

        public void setCardVictoryPoints(int cardVictoryPoints) {
            this.cardVictoryPoints = cardVictoryPoints;
        }

        public ResRequirements getResRequirements() {
            return resRequirements;
        }

        public void setResRequirements(ResRequirements resRequirements) {
            this.resRequirements = resRequirements;
        }

        public CardRequirements getCardRequirements() {
            return cardRequirements;
        }

        public void setCardRequirements(CardRequirements cardRequirements) {
            this.cardRequirements = cardRequirements;
        }

        public LeaderAbility getCardAbility() {
            return cardAbility;
        }

        public void setCardAbility(LeaderAbility cardAbility) {
            this.cardAbility = cardAbility;
        }
    }



