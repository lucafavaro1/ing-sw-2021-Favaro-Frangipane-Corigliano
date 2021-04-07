package it.polimi.ingsw.Player;

import it.polimi.ingsw.ActionCards.ActionCardDeck;
import it.polimi.ingsw.Game;

import java.io.FileNotFoundException;

public class CPUPlayer extends Player{

    ActionCardDeck actionCardDeck = new ActionCardDeck();
    public CPUPlayer(Game game, int idPlayer) throws FileNotFoundException {
        super(game, idPlayer);
    }


    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }

}
