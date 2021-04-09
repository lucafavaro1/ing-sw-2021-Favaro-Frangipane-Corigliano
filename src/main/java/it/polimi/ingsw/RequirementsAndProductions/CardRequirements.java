package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Development.BadSlotNumberException;
import it.polimi.ingsw.Development.Tuple;
import it.polimi.ingsw.Player.HumanPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * class modeling the requirements of developmentCards
 */
public class CardRequirements implements Requirements {
    private final List<Tuple> cardReq;

    public CardRequirements(List<Tuple> cardReq) {
        this.cardReq = cardReq;
    }

    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        ArrayList<Tuple> allDev = new ArrayList<>();
        try {
            for (int i = 0; i < 3; i++) {
                player.getDevelopmentBoard().getCardsFromSlot(i).forEach(
                        developmentCard -> allDev.add(developmentCard.getCardType())
                );
            }

            return allDev.containsAll(cardReq);
        } catch (BadSlotNumberException e) {
            System.out.println("ERROR: invalid slot while checking all the devCards\n");
            return false;
        }
    }

    public List<Tuple> getCardReq() {
        return cardReq;
    }

    @Override
    public String toString() {
        return "{" + cardReq.stream()
                .map(Tuple::toString)
                .collect(Collectors.joining("; "))
                + "}";
    }
}
