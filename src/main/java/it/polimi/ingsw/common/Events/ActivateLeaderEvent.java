package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event that signals the activation of the production
 */
public class ActivateLeaderEvent extends Event {

    public ActivateLeaderEvent() {
        eventType = Events_Enum.ACTIVATE_LEADER;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;

        List<LeaderCard> leaderCardList = player.getLeaderCards().stream().filter(leaderCard -> !leaderCard.isEnabled()).collect(Collectors.toList());
        if (leaderCardList.size() == 0) {
            player.getGameClientHandler().sendEvent(new FailEvent("No leader cards to be activated"));
        }

        // making the player choose the leader card to activate
        boolean enabled = (new MakePlayerChoose<>(
                "Choose the Leader card to activate: ",
                leaderCardList
        ).choose(player))
                .enable(player);

        if (enabled) {
            player.getGameClientHandler().sendEvent(new ActionDoneEvent("You activated the leader card!"));
        } else {
            player.getGameClientHandler().sendEvent(new FailEvent("Leader card can't be enabled"));
        }
    }
}
