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

public class PrintDcBoardEvent extends Event {
    private String textMessage="";

    public PrintDcBoardEvent(Game game) throws NoCardsInDeckException {
        eventType = Events_Enum.PRINT_MESSAGE;
        textMessage = "DC BOARD IS COMPOSED OF: \n";

        for(Tuple tuple: game.getDcBoard().getAllCards().keySet()) {
            textMessage.concat(game.getDcBoard().getFirstCard(tuple).toString() + "\n");
        }
    }

    @Override
    public void handle(Object target) {
        System.out.println(textMessage);
    }
}
