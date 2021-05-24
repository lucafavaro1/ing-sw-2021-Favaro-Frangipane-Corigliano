package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;

public class PrintStrongboxEvent extends PrintEvent<StrongBox> {
    public PrintStrongboxEvent(HumanPlayer player) {
        super(player.getNickname(), player.getStrongBox());
        printType = PrintObjects_Enum.STRONGBOX;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.getPlayers().get(nickname).setStrongBox(toPrint);
    }
}
