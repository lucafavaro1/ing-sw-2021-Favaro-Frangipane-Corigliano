package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Game;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the DcBoard situation of a game
 */
public class PrintDcBoardEvent extends PrintEvent<DcBoard> {
    public PrintDcBoardEvent(Game game) {
        super(null, game.getDcBoard());
        printType = PrintObjects_Enum.DC_BOARD;
    }

    public PrintDcBoardEvent(DcBoard dcBoard) {
        super(null, dcBoard);
        printType = PrintObjects_Enum.DC_BOARD;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.setDcBoard(toPrint);
    }

    @Override
    public String toString() {
        return "View Common Development Board";
    }
}
