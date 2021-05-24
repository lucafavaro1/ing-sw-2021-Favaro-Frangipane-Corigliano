package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the development cards of the player
 */
public class PrintDevelopmentCardsEvent extends PrintEvent<DcPersonalBoard> {
    public PrintDevelopmentCardsEvent(HumanPlayer player) {
        super(player.getNickname(), player.getDevelopmentBoard());
        printType = PrintObjects_Enum.PERSONAL_DC_BOARD;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.getPlayers().get(nickname).setDevelopmentBoard(toPrint);
    }
}
