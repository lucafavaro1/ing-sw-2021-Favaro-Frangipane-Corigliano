package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Market.MarketTray;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the market tray situation of a game
 */

public class PrintMarketTrayEvent extends PrintEvent<MarketTray> {
    public PrintMarketTrayEvent(Game game) {
        printType = PrintObjects_Enum.MARKET_TRAY;
        toPrint = game.getMarketTray();
        /*toPrint = "Market Tray: \n" +
                "freeball: " + game.getMarketTray().getFreeball() +"\n" +
                "\t   1\t  2\t    3\t   4" +
                " \n 1: " + "["+game.getMarketTray().getRow(0).get(0)+"]" + "["+game.getMarketTray().getRow(0).get(1)+"]" +
                "["+game.getMarketTray().getRow(0).get(2)+"]" + "["+game.getMarketTray().getRow(0).get(3)+"]" +
                " \n 2: " + "["+game.getMarketTray().getRow(1).get(0)+"]" + "["+game.getMarketTray().getRow(1).get(1)+"]" +
                "["+game.getMarketTray().getRow(1).get(2)+"]" + "["+game.getMarketTray().getRow(1).get(3)+"]" +
                " \n 3: " + "["+game.getMarketTray().getRow(2).get(0)+"]" + "["+game.getMarketTray().getRow(2).get(1)+"]" +
                "["+game.getMarketTray().getRow(2).get(2)+"]" + "["+game.getMarketTray().getRow(2).get(3)+"]";*/
    }

    @Override
    public String toString() {
        return "View Common MarketTray";
    }
}
