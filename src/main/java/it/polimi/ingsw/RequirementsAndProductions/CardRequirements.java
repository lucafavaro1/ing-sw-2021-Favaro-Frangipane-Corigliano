package it.polimi.ingsw.RequirementsAndProductions;

import it.polimi.ingsw.Development.BadSlotNumberException;
import it.polimi.ingsw.Development.DevelopmentCard;
import it.polimi.ingsw.Development.Tuple;
import it.polimi.ingsw.Player.HumanPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardRequirements implements Requirements {
    private final List<Tuple> cardReq;

    public CardRequirements(List<Tuple> cardReq) {
        this.cardReq = cardReq;
    }

    @Override
    public boolean isSatisfiable(HumanPlayer player) throws BadSlotNumberException {
         ArrayList<Tuple> allDev = new ArrayList<>();
         for(int i=1; i<3; i++) {
             for (int j = 1; j < player.getDevelopmentBoard().getCardsFromSlot(i).size(); j++) {
                 allDev.add(player.getDevelopmentBoard().getCardsFromSlot(i).get(j).getCardType());
             }
         }

         if(allDev.contains(cardReq))
             return true;
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
