package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;

public class PrintStrongboxEvent extends PrintEvent<StrongBox> {
    public PrintStrongboxEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.STRONGBOX;
        toPrint = player.getStrongBox();
    }
}
