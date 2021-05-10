package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the faithtrack of the player
 */

public class PrintFaithtrackEvent extends PrintEvent {

    public PrintFaithtrackEvent(HumanPlayer nickname) {
        textMessage = "Faithtrack : " + nickname.getFaithTrack().toString();
    }

}
