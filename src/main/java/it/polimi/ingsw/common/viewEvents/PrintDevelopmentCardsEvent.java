package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the development cards of the player
 */

public class PrintDevelopmentCardsEvent extends PrintEvent<DcPersonalBoard> {
    public PrintDevelopmentCardsEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.PERSONAL_DC_BOARD;
        toPrint = player.getDevelopmentBoard();

    /*"Development cards: \n";
        try {
            for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(0)) {
                toPrint = toPrint.concat(developmentCard.toString() + "\n");
            }
            for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(1)) {
                toPrint = toPrint.concat(developmentCard.toString() + "\n");
            }
            for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(2)) {
                toPrint = toPrint.concat(developmentCard.toString() + "\n");
            }
        } catch (BadSlotNumberException e) {
            e.printStackTrace();
        }*/
    }
}
