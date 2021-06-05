package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintMarketTrayEvent;
import it.polimi.ingsw.common.viewEvents.PrintPlayerEvent;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.Player;

import java.util.List;

/**
 * Event used to advise that the preparation of the game is completed
 * The market tray scene and Dc board scene are updated
 */

public class PreparationEndedEvent extends Event {

    private final List<Player> players;
    private final DcBoard dcBoard;
    private final MarketTray marketTray;

    public PreparationEndedEvent(Game game) {
        eventType = Events_Enum.PREPARATION_ENDED;

        this.players = game.getPlayers();
        this.dcBoard = game.getDcBoard();
        this.marketTray = game.getMarketTray();
    }

    @Override
    public void handle(Object clientController) {
        UserInterface userInterface = UserInterface.getInstance();

        // updating view
        userInterface.getEventBroker().post(new PrintMarketTrayEvent(marketTray), false);
        userInterface.getEventBroker().post(new PrintDcBoardEvent(dcBoard), false);
        players.forEach(player -> userInterface.getEventBroker().post(new PrintPlayerEvent(player), false));

        ((ClientController) clientController).endPreparation();
    }
}
