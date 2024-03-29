package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintFaithtrackEvent;
import it.polimi.ingsw.common.viewEvents.PrintLeaderCardsEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Event that signals the activation of the production
 */
public class DiscardLeaderEvent extends Event {
    private int numcard = -1;

    /**
     * Basic constructor used in the enumeration of what possible actions the player can do
     */
    public DiscardLeaderEvent() {
        eventType = Events_Enum.DISCARD_LEADER;
    }

    /**
     * Constructor that specifies the position of the leader card the players wants to discard
     *
     * @param num position of the leader card to discard (0=left, 1=right)
     */
    public DiscardLeaderEvent(int num) {
        eventType = Events_Enum.DISCARD_LEADER;
        this.numcard = num;
    }

    @Override
    public void handle(Object playerObj) {
        LeaderCard leaderCardToDiscard;
        HumanPlayer player = (HumanPlayer) playerObj;

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't complete this action, it's not your turn!"));
            return;
        }

        // checking that there are leader cards to be discarded
        List<Object> leaderCardList = new ArrayList<>(Arrays.asList(player.getLeaderCards().toArray()));
        if (leaderCardList.size() == 0) {
            player.getGameClientHandler().sendEvent(new FailEvent("There are no leader cards to delete!"));
            return;
        }


        if (numcard != -1) {
            try {
                leaderCardToDiscard = player.getLeaderCards().get(numcard);
            } catch (IndexOutOfBoundsException e) {
                player.getGameClientHandler().sendEvent(new FailEvent("This leader card does not exist!"));
                return;
            }
        } else {
            leaderCardList.add("Go back");
            // making the player choose the leader card to discard
            Object chosen = (new MakePlayerChoose<>(
                    "Choose which leader card to delete: ",
                    leaderCardList
            ).choose(player));

            // check if the player wants to go back
            if (chosen.equals("Go back")) {
                player.getGameClientHandler().sendEvent(new ActionDoneEvent(""));
                return;
            }

            leaderCardToDiscard = (LeaderCard) chosen;
        }

        // discarding the card chosen
        player.getLeaderCards().remove(leaderCardToDiscard);

        // adding the faith
        player.getGame().getEventBroker().post(player.getFaithTrack(), new AddFaithEvent(1), true);

        // updating the view
        player.getGame().getEventBroker().post(new PrintFaithtrackEvent(player), false);
        player.getGame().getEventBroker().post(new PrintLeaderCardsEvent(player), false);

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You discarded the leader card!"));
    }
}
