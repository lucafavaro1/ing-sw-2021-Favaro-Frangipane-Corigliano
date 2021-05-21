package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintLeaderCardsEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event that signals the activation of the production
 */
public class ActivateLeaderEvent extends Event {
    private int numcard=-1;

    public ActivateLeaderEvent() {
        eventType = Events_Enum.ACTIVATE_LEADER;
    }

    public ActivateLeaderEvent(int num) {
        eventType = Events_Enum.ACTIVATE_LEADER;
        this.numcard = num;
    }

    @Override
    public void handle(Object playerObj) {
        boolean enabled = false;
        HumanPlayer player = (HumanPlayer) playerObj;

        // returning a fail event if it's not the turn of the player
        if(!player.isPlaying()){
            player.getGameClientHandler().sendEvent(new FailEvent("Can't do this action, it's not your turn!"));
            return;
        }

        List<LeaderCard> leaderCardList = player.getLeaderCards().stream().filter(leaderCard -> !leaderCard.isEnabled()).collect(Collectors.toList());
        if (leaderCardList.size() == 0) {
            player.getGameClientHandler().sendEvent(new FailEvent("No leader cards to be activated"));
        }

        if(numcard != -1) {
            try {
                enabled = leaderCardList.get(numcard).enable(player);
            } catch (IndexOutOfBoundsException e) {
                player.getGameClientHandler().sendEvent(new FailEvent("Leader card isn't present"));
            }
        }
        else {
            // making the player choose the leader card to activate
            enabled = (new MakePlayerChoose<>(
                    "Choose the Leader card to activate: ",
                    leaderCardList
            ).choose(player))
                    .enable(player);
        }

        if (enabled) {
            player.getGameClientHandler().sendEvent(new PrintLeaderCardsEvent(player));
            player.getGameClientHandler().sendEvent(new ActionDoneEvent("You activated the leader card!"));
        } else {
            player.getGameClientHandler().sendEvent(new FailEvent("Leader card can't be enabled"));
        }
    }
}
