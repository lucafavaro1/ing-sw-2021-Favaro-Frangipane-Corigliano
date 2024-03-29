package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.StrongBox;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the strongbox
 */
public class PrintStrongboxEvent extends PrintEvent<StrongBox> {
    public PrintStrongboxEvent(HumanPlayer player) {
        super(player.getNickname(), player.getStrongBox());
        printType = PrintObjects_Enum.STRONGBOX;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        // updating the view only if is of the client's player
        if (nickname.equals(userInterface.getMyNickname()))
            userInterface.printMessage(toPrint);

        ((HumanPlayer) userInterface.getPlayers().get(nickname)).setStrongBox(toPrint);
    }
}
