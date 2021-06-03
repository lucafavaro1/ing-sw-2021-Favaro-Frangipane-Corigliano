package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

import java.util.List;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the productions added by the player
 */
public class PrintProductionsAddedEvent extends PrintEvent<List<Production>> {
    public PrintProductionsAddedEvent(HumanPlayer player) {
        super(player.getNickname(), player.getProductionsAdded());
        printType = PrintObjects_Enum.PRODUCTIONS_ADDED;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        // updating the view only if is of the client's player
        if(nickname.equals(userInterface.getMyNickname()))
            userInterface.printMessage(toPrint);

        ((HumanPlayer)userInterface.getPlayers().get(nickname)).setProductionsAdded(toPrint);
    }
}
