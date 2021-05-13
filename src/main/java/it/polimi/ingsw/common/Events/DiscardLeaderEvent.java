package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.LeaderAbility;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event that signals the activation of the production
 */
public class DiscardLeaderEvent extends Event {

    public DiscardLeaderEvent() {
        eventType = Events_Enum.DISCARD_LEADER;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;
        List<LeaderCard> leaderCardList = player.getLeaderCards().stream().filter(leaderCard -> !leaderCard.isEnabled()).collect(Collectors.toList());
        if(leaderCardList.size() == 0){
            player.getGameClientHandler().sendEvent(new FailEvent("No leader cards to be discarded"));
        }

        // making the player choose the leader card to activate
        LeaderCard leaderCardToDiscard = (new MakePlayerChoose<>(
                "Choose the Leader card to activate: ",
                leaderCardList
        ).choose(player));

        // discarding the card chosen
        player.getLeaderCards().remove(leaderCardToDiscard);

        // adding the faith
        player.getGame().getEventBroker().post(player.getFaithTrack(), new AddFaithEvent(1), false);

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You discarded the leader card!"));
    }
}
