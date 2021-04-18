package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

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

    public List<Tuple> getCardReq() {
        return cardReq;
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

    @Override
    public String toString() {
        return "{" + cardReq.stream()
                .map(Tuple::toString)
                .collect(Collectors.joining("; "))
                + "}";
    }
}
