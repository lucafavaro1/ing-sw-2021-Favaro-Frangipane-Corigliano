package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * class modeling the requirements of developmentCards
 */
public class CardRequirements extends Serializable implements Requirements {
    private final List<Tuple> cardReq;

    public CardRequirements(List<Tuple> cardReq) {
        this.cardReq = cardReq;
        this.serializationType = SerializationType.CARD_REQUIREMENTS;
    }

    public List<Tuple> getCardReq() {
        return cardReq;
    }

    // TODO add javadoc
    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        ArrayList<Tuple> allDev = new ArrayList<>();
        Map<TypeDevCards_Enum, Integer> numOfCardsReq = new HashMap<>();

        // taking all the tuple of the development cards owned by the player
        try {
            for (int i = 0; i < 3; i++) {
                player.getDevelopmentBoard().getCardsFromSlot(i).forEach(
                        developmentCard -> allDev.add(developmentCard.getCardType())
                );
            }
        } catch (BadSlotNumberException e) {
            System.err.println("ERROR: invalid slot while checking all the devCards\n");
            return false;
        }

        for (Tuple tuple : cardReq) {
            numOfCardsReq.merge(tuple.getType(), 1, Integer::sum);
        }

        // checking if the number of cards required are less than the cards owned by the player
        if (!Arrays.stream(TypeDevCards_Enum.values()).allMatch(type ->
                Optional.ofNullable(numOfCardsReq.get(type)).orElse(0) <= Collections.frequency(
                        allDev.stream().map(Tuple::getType).collect(Collectors.toList()), type
                )
        )) {
            return false;
        }

        // returns true if the list of cards owned by the player contains all the card requirements with an acceptable level
        return allDev.containsAll(cardReq.stream().filter(tuple ->
                        tuple.getLevel() >= Tuple.getMinLevel() && tuple.getLevel() <= Tuple.getMaxLevel()
                ).collect(Collectors.toList())
        );
    }

    @Override
    public String toString() {
        return "{" + cardReq.stream()
                .map(Tuple::toString)
                .collect(Collectors.joining("; "))
                + "}";
    }
}
