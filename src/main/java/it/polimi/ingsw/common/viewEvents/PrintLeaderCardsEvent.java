package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the leader card situation of the player
 */
public class PrintLeaderCardsEvent extends PrintEvent {
    public PrintLeaderCardsEvent(HumanPlayer nickname) {
        textMessage = "Leader cards: \n";
        for (LeaderCard leaderCard : nickname.getLeaderCards()) {
            textMessage = textMessage.concat(leaderCard.toString() + "\n");
        }
    }

}
