package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the action cards
 */
public class PrintActionCardEvent extends PrintEvent<ActionCard> {
    public PrintActionCardEvent(ActionCard card) {
        super(null, card);
        printType = PrintObjects_Enum.ACTION_CARD;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.setLastActionCard(toPrint);
    }

    @Override
    public String toString() {
        return "View Action Cards";
    }
}
