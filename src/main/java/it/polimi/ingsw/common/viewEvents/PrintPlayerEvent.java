package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the DcBoard situation of a game
 */
public class PrintPlayerEvent extends PrintEvent<HumanPlayer> {
    public PrintPlayerEvent(Player player) {
        super(player.getNickname(), (HumanPlayer) player);
        printType = PrintObjects_Enum.PLAYER;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.getPlayers().put(toPrint.getNickname(), toPrint);
    }

    @Override
    public String toString() {
        return "View Common Development Board";
    }
}
