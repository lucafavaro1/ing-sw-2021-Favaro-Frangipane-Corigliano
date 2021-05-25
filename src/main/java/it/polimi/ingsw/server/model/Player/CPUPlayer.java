package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.viewEvents.PrintActionCardEvent;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.ActionCards.ActionCardDeck;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;

import java.io.FileNotFoundException;
import java.util.EnumSet;

/**
 * Class that represents the opponent in a single player game
 */
public class CPUPlayer extends Player {
    private final ActionCardDeck actionCardDeck = new ActionCardDeck();
    private boolean victory = false;

    // TODO: modify to make CPUPlayer handle itself the events?
    public CPUPlayer(Game game, int idPlayer) throws FileNotFoundException {
        super(game, idPlayer);

        // registering his faithTrack to the events of the actionCards
        game.getEventBroker().subscribe(getFaithTrack(), EnumSet.of(Events_Enum.PLUS_FAITH_CARD));

        // registering his actionCardDeck to the events of the actionCards
        game.getEventBroker().subscribe(getActionCardDeck(), EnumSet.of(Events_Enum.SHUFFLE_ACTION));

        nickname = "Lorenzo (CPU)";
    }

    public ActionCardDeck getActionCardDeck() {
        return actionCardDeck;
    }

    @Override
    public void play() {
        try {
            ActionCard actionCard = actionCardDeck.takeFirstPutLast();

            // notifying the player about the card took by the CPU
            game.getEventBroker().post(new PrintActionCardEvent(actionCard), false);
            actionCard.getEffect().applyEffect(game, actionCard.getDevCardToDiscard());
        } catch (NoCardsInDeckException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int countPoints() {
        boolean NoDevCards = false;
        for (TypeDevCards_Enum type : TypeDevCards_Enum.values()) {
            if (game.getDcBoard().getTupleCards(new Tuple(type, 1)).size() +
                    game.getDcBoard().getTupleCards(new Tuple(type, 2)).size() +
                    game.getDcBoard().getTupleCards(new Tuple(type, 3)).size() == 0) {
                NoDevCards = true;
            }
        }


        // TODO to be modified?
        if (NoDevCards || faithTrack.getTrackPos() == 24)
            return 1000;
        else
            return -1;
    }

    public void setVictory() {
        this.victory = true;
    }

}
