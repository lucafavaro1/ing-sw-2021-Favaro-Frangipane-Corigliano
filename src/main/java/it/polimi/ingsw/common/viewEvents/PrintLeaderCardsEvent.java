package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the leader card situation of the player
 */
public class PrintLeaderCardsEvent extends PrintEvent<List<LeaderCard>> {
    public PrintLeaderCardsEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.LEADER_CARDS;
        toPrint = player.getLeaderCards();
    }

}
