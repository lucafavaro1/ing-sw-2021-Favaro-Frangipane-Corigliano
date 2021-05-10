package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the DcBoard situation of a game
 */

public class PrintDcBoardEvent extends PrintEvent {
    public PrintDcBoardEvent(Game game) {
        textMessage = "DC BOARD IS COMPOSED OF: \n";

        for (int level = 1; level <= 3; level++) {
            for(TypeDevCards_Enum type : TypeDevCards_Enum.values()){
                Tuple tuple = new Tuple(type, level);
                try {
                    textMessage = textMessage.concat(game.getDcBoard().getFirstCard(tuple).toString() + "\n\n");
                } catch (NoCardsInDeckException ignored) {
                }
            }
        }
    }

    @Override
    public String toString() {
        return "View Common Development Board";
    }
}
