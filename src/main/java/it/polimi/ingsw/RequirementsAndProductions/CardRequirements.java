package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Development.Tuple;
import it.polimi.ingsw.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class CardRequirements implements Requirements {
    private final List<Tuple> cardReq;

    public CardRequirements(List<Tuple> cardReq) {
        this.cardReq = cardReq;
    }

    // TODO: develop once we put the DcPersonalBoard in the Player
    @Override
    public boolean isSatisfiable(HumanPlayer player) {
        return false;
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
