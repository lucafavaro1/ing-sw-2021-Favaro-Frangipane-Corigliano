package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.CardRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
/**
 * Class that describes the leader card
 */

public class LeaderCard {

    private int cardVictoryPoints;
    private ResRequirements resRequirements;
    private CardRequirements cardRequirements;
    private LeaderAbility cardAbility;


    public LeaderCard(LeaderAbility cardAbility, CardRequirements cardRequirements,
                      ResRequirements resRequirements, int cardVictoryPoints) {
        this.cardAbility = cardAbility;
        this.cardRequirements = cardRequirements;
        this.resRequirements = resRequirements;
        this.cardVictoryPoints = cardVictoryPoints;
    }

    public void setPlayer(HumanPlayer player) {
        cardAbility.setPlayer(player);
    }

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

    public boolean isAllowed() {
        return getCardVictoryPoints() > 0 && getCardAbility() != null && (getResRequirements() != null || getCardRequirements() != null)
                && !(getResRequirements() == null && getCardRequirements() == null);
    }
}



