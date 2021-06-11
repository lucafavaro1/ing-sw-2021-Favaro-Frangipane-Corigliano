package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintMarketTrayEvent;
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
        UserInterface userInterface = UserInterface.getInstance();

        userInterface.setDcBoard(dcBoard);
        userInterface.setMarketTray(marketTray);

        userInterface.getEventBroker().post(new PrintDcBoardEvent(dcBoard), false);
        userInterface.getEventBroker().post(new PrintMarketTrayEvent(marketTray), false);

        ((ClientController) clientController).gameStarted();
    }
}
