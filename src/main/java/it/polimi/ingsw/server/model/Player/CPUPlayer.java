package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.ActionCards.ActionCardDeck;
import it.polimi.ingsw.server.model.Events.Events_Enum;
import it.polimi.ingsw.server.model.Game;

import java.io.FileNotFoundException;
import java.util.EnumSet;

public class CPUPlayer extends Player {

    ActionCardDeck actionCardDeck = new ActionCardDeck();

    public CPUPlayer(Game game, int idPlayer) throws FileNotFoundException {
        super(game, idPlayer);

        // registering his faithTrack to the events of the actionCards
        game.getEventBroker().subscribe(getFaithTrack(), EnumSet.of(Events_Enum.PLUS_FAITH_CARD));

        // registering his actionCardDeck to the events of the actionCards
        game.getEventBroker().subscribe(getActionCardDeck(), EnumSet.of(Events_Enum.SHUFFLE_ACTION));
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
