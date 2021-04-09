package it.polimi.ingsw.Player;

import it.polimi.ingsw.ActionCards.ActionCardDeck;
import it.polimi.ingsw.Events.Events_Enum;
import it.polimi.ingsw.Game;

import java.io.FileNotFoundException;
import java.util.EnumSet;

public class CPUPlayer extends Player {

    ActionCardDeck actionCardDeck = new ActionCardDeck();

    public CPUPlayer(Game game, int idPlayer) throws FileNotFoundException {
        super(game, idPlayer);

        // registering his faithTrack to the events of the actionCards
        game.getEventBroker().subscribe(getFaithTrack(), EnumSet.of(
                Events_Enum.PLUS_ONE_FAITH, Events_Enum.PLUS_TWO_FAITH
        ));

        // registering his actionCardDeck to the events of the actionCards
        game.getEventBroker().subscribe(getActionCardDeck(),
                EnumSet.of(Events_Enum.SHUFFLE_ACTION)
        );
    }

    // TODO play() to be developed
    @Override
    public boolean play() {
        return false;
    }

    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }

}
