package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;

public class PrintWarehouseEvent extends PrintEvent<WarehouseDepots> {
    public PrintWarehouseEvent(HumanPlayer player) {
        printType = PrintObjects_Enum.WAREHOUSE;
        toPrint = player.getWarehouseDepots();
    }
}
