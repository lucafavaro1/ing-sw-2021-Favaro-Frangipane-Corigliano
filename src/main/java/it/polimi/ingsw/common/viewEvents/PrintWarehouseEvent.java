package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;

public class PrintWarehouseEvent extends PrintEvent<WarehouseDepots> {
    public PrintWarehouseEvent(HumanPlayer player) {
        super(player.getNickname(), player.getWarehouseDepots());
        printType = PrintObjects_Enum.WAREHOUSE;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.getPlayers().get(nickname).setWarehouseDepots(toPrint);
    }
}
