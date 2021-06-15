package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the warehouse of the player
 */
public class PrintWarehouseEvent extends PrintEvent<WarehouseDepots> {
    public PrintWarehouseEvent(HumanPlayer player) {
        super(player.getNickname(), player.getWarehouseDepots());
        printType = PrintObjects_Enum.WAREHOUSE;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        // updating the view only if is of the client's player
        if (nickname.equals(userInterface.getMyNickname()))
            userInterface.printMessage(toPrint);

        ((HumanPlayer) userInterface.getPlayers().get(nickname)).setWarehouseDepots(toPrint);
    }
}
