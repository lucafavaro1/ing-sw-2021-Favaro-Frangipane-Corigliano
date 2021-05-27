package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintFaithtrackEvent;
import it.polimi.ingsw.common.viewEvents.PrintLeaderCardsEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event that signals the activation of the production
 */
public class DiscardLeaderEvent extends Event {
    private int numcard = -1;

    public DiscardLeaderEvent() {
        eventType = Events_Enum.DISCARD_LEADER;
    }

    public DiscardLeaderEvent(int num) {
        eventType = Events_Enum.DISCARD_LEADER;
        this.numcard = num;
    }

    @Override
    public void handle(Object playerObj) {
        LeaderCard leaderCardToDiscard = null;
        HumanPlayer player = (HumanPlayer) playerObj;

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Impossibile fare questa azione, non è il tuo turno!"));
            return;
        }

        List<Object> leaderCardList = player.getLeaderCards().stream().filter(leaderCard -> !leaderCard.isEnabled()).collect(Collectors.toList());
        if (leaderCardList.size() == 0) {
            player.getGameClientHandler().sendEvent(new FailEvent("Non ci sono carte leader da scartare!"));
        }

        if (numcard != -1) {
            try {
                leaderCardToDiscard = player.getLeaderCards().get(numcard);
            } catch (IndexOutOfBoundsException e) {
                player.getGameClientHandler().sendEvent(new FailEvent("La carta leader da scartare non esiste!"));
            }
        } else {
            leaderCardList.add("Torna indietro");
            // making the player choose the leader card to discard
            Object chosen = (new MakePlayerChoose<>(
                    "Scegli la carta leader da scartare: ",
                    leaderCardList
            ).choose(player));

            // check if the player wants to go back
            if (chosen.equals("Torna indietro")) {
                player.getGameClientHandler().sendEvent(new ActionDoneEvent(""));
                return;
            }

            leaderCardToDiscard = (LeaderCard) chosen;
        }

        // discarding the card chosen
        player.getLeaderCards().remove(leaderCardToDiscard);

        // adding the faith
        player.getGame().getEventBroker().post(player.getFaithTrack(), new AddFaithEvent(1), false);

        // updating the view
        player.getGame().getEventBroker().post(player.getGameClientHandler(), new PrintFaithtrackEvent(player), false);
        player.getGame().getEventBroker().post(player.getGameClientHandler(), new PrintLeaderCardsEvent(player), false);

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("Hai scartato una carta leader!"));
    }
}
