package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Market.MarketTray;

/**
 * Event that signals the starting of the game
 */
public class GameStartedEvent extends Event {
    private final DcBoard dcBoard;
    private final MarketTray marketTray;

    public GameStartedEvent(Game game) {
        eventType = Events_Enum.GAME_STARTED;
        dcBoard = game.getDcBoard();
        marketTray = game.getMarketTray();
    }

    @Override
    public void handle(Object clientController) {
        UserInterface.getInstance().printMessage(dcBoard.toString() + "\n" + marketTray.toString());
        ((ClientController) clientController).gameStarted();
    }
}
