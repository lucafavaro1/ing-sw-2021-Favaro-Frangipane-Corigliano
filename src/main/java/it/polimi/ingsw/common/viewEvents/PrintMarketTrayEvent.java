package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Game;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the markettray situation of a game
 */

public class PrintMarketTrayEvent extends Event {
    private String textMessage="";

    public PrintMarketTrayEvent(Game game) {
        eventType = Events_Enum.PRINT_MESSAGE;
        textMessage = "Market Tray: \n" +
                "freeball: " + game.getMarketTray().getFreeball() +"\n" +
                " \n " + "["+game.getMarketTray().getRow(0).get(0)+"]" + "["+game.getMarketTray().getRow(0).get(1)+"]" +
                "["+game.getMarketTray().getRow(0).get(2)+"]" + "["+game.getMarketTray().getRow(0).get(3)+"]" +
                " \n " + "["+game.getMarketTray().getRow(1).get(0)+"]" + "["+game.getMarketTray().getRow(1).get(1)+"]" +
                "["+game.getMarketTray().getRow(1).get(2)+"]" + "["+game.getMarketTray().getRow(1).get(3)+"]" +
                " \n " + "["+game.getMarketTray().getRow(2).get(0)+"]" + "["+game.getMarketTray().getRow(2).get(1)+"]" +
                "["+game.getMarketTray().getRow(2).get(2)+"]" + "["+game.getMarketTray().getRow(2).get(3)+"]";
    }

    @Override
    public void handle(Object player) {
        System.out.println(textMessage);
    }
}
