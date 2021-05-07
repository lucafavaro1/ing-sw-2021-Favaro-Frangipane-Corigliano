package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the DcBoard situation of a game
 */

public class PrintDcBoardEvent extends PrintEvent {
    public PrintDcBoardEvent(Game game) {
        eventType = Events_Enum.PRINT_MESSAGE;
        textMessage = "DC BOARD IS COMPOSED OF: \n";

        for(Tuple tuple: game.getDcBoard().getAllCards().keySet()) {
            try {
                textMessage = textMessage.concat(game.getDcBoard().getFirstCard(tuple).toString() + "\n");
            } catch (NoCardsInDeckException ignored) {
            }
        }
    }

    @Override
    public String toString() {
        return "View Common Development Board";
    }
}
