package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the faithtrack of the player
 */

public class PrintFaithtrackEvent extends Event {
    private String textMessage="";

    public PrintFaithtrackEvent(HumanPlayer nickname) {
        eventType = Events_Enum.PRINT_MESSAGE;
        textMessage = "Faithtrack : " + nickname.getFaithTrack().toString();
    }

    @Override
    public void handle(Object player) {
        System.out.println(textMessage);
    }

}
