package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the development cards of the player
 */

public class PrintDevelopmentCardsEvent extends PrintEvent {
    public PrintDevelopmentCardsEvent(HumanPlayer nickname) {
        textMessage = "Development cards: \n";
        try {
            for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(0)) {
                textMessage = textMessage.concat(developmentCard.toString() + "\n");
            }
            for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(1)) {
                textMessage = textMessage.concat(developmentCard.toString() + "\n");
            }
            for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(2)) {
                textMessage = textMessage.concat(developmentCard.toString() + "\n");
            }
        } catch (BadSlotNumberException e) {
            e.printStackTrace();
        }
    }
}
