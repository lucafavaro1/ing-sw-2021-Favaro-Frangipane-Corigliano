package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Market.MarketTray;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the market tray situation of a game
 */
public class PrintMarketTrayEvent extends PrintEvent<MarketTray> {
    public PrintMarketTrayEvent(Game game) {
        super(null, game.getMarketTray());
        printType = PrintObjects_Enum.MARKET_TRAY;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
        userInterface.setMarketTray(toPrint);
    }

    @Override
    public String toString() {
        return "View Common MarketTray";
    }
}
