package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the development cards of the player
 */

public class PrintDevelopmentCardsEvent extends Event {
    private String textMessage="";

    public PrintDevelopmentCardsEvent(HumanPlayer nickname) throws BadSlotNumberException {
        eventType = Events_Enum.PRINT_MESSAGE;
        textMessage = "Development cards: \n";
        for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(1)) {
            textMessage.concat(developmentCard.toString() + "\n");
        }
        for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(2)) {
            textMessage.concat(developmentCard.toString() + "\n");
        }
        for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(3)) {
            textMessage.concat(developmentCard.toString() + "\n");
        }
    }

    @Override
    public void handle(Object player) {
        System.out.println(textMessage);
    }
}
