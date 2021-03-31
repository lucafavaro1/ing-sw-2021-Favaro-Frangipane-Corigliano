package it.polimi.ingsw.Player;

import it.polimi.ingsw.ActionCards.ActionCardDeck;
import java.io.FileNotFoundException;

public class CPUPlayer extends Player{

    ActionCardDeck actionCardDeck = new ActionCardDeck();
    public CPUPlayer(int idPlayer) throws FileNotFoundException {
        super(idPlayer);
    }


    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }

}
