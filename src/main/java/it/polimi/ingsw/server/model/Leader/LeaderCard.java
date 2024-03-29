package it.polimi.ingsw.server.model.Leader;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.CardRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

/**
 * Class that describes the leader card
 */
public class LeaderCard extends Serializable {
    private int cardVictoryPoints;
    private ResRequirements resRequirements;
    private CardRequirements cardRequirements;
    private LeaderAbility cardAbility;
    private boolean enabled = false;

    public LeaderCard(LeaderAbility cardAbility, CardRequirements cardRequirements,
                      ResRequirements resRequirements, int cardVictoryPoints) {
        this.serializationType = SerializationType.LEADER_CARD;
        this.cardAbility = cardAbility;
        this.cardRequirements = cardRequirements;
        this.resRequirements = resRequirements;
        this.cardVictoryPoints = cardVictoryPoints;

    }

    /**
     * Enables the leader card if all the requirements are satisfied by the player
     *
     * @param player Human player that wants to activate this leader card
     * @return if the card has been enabled or not
     */
    public boolean enable(HumanPlayer player) {
        if (!enabled &&
                (resRequirements == null || resRequirements.isSatisfiable(player)) &&
                (cardRequirements == null || cardRequirements.isSatisfiable(player))
        ) {
            enabled = true;

            // if the card is a more production ability sets as available the produciton
            if (cardAbility != null && cardAbility.getAbilityType() == Abil_Enum.PRODUCTION)
                ((MoreProduction) cardAbility).getProduction().setAvailable(true);
        }

        return enabled;
    }

    /**
     * Checks if the Leader card is allowed in the game (used after deserializing the cards from the JSON file)
     *
     * @return true if the card is allowed, false otherwise
     */
    public boolean isAllowed() {
        return cardVictoryPoints >= 0 &&
                cardAbility != null && cardAbility.isAllowed() &&
                (resRequirements != null || cardRequirements != null);
    }

    public boolean isEnabled() {
        return enabled;
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

    @Override
    public String toString() {
        if (cardAbility == null || (resRequirements == null && cardRequirements == null))
            return "Leader card not yet activated";

        return cardAbility.getAbilityType() + " { \n" +
                "\tenabled=" + enabled + "\n" +
                "\tresRequirements=" + resRequirements + "\n" +
                "\tcardRequirements=" + cardRequirements + "\n" +
                "\tcardAbility=" + cardAbility + "\n" +
                "\tcardVictoryPoints=" + cardVictoryPoints + "\n" +
                '}';
    }
}



