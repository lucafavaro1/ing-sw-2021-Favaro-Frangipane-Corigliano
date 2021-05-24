package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the faithtrack of the player
 */

public class PrintFaithtrackEvent extends PrintEvent<FaithTrack> {

    public PrintFaithtrackEvent(HumanPlayer player) {
        super(player.getNickname(), player.getFaithTrack());
        printType = PrintObjects_Enum.FAITH_TRACK;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.getPlayers().get(nickname).setFaithTrack(toPrint);
    }
}
