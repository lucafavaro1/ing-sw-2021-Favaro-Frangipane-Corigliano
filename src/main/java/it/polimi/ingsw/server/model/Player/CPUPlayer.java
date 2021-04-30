package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.ActionCards.ActionCardDeck;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.util.EnumSet;

/**
 * Class that represents the opponent in a single player game
 */
public class CPUPlayer extends Player {
    private final ActionCardDeck actionCardDeck = new ActionCardDeck();

    public CPUPlayer(Game game, int idPlayer) throws FileNotFoundException {
        super(game, idPlayer);

        // registering his faithTrack to the events of the actionCards
        game.getEventBroker().subscribe(getFaithTrack(), EnumSet.of(Events_Enum.PLUS_FAITH_CARD));

        // registering his actionCardDeck to the events of the actionCards
        game.getEventBroker().subscribe(getActionCardDeck(), EnumSet.of(Events_Enum.SHUFFLE_ACTION));
    }

    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }

    @Override
    public void play() {
        try {
            ActionCard actionCard = actionCardDeck.takeFirstPutLast();
            actionCard.getEffect().applyEffect(game, actionCard.getDevCardToDiscard());
        } catch (NoCardsInDeckException e) {
            e.printStackTrace();
        }
    }
}
