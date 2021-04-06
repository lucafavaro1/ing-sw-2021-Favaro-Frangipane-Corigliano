package it.polimi.ingsw;

import it.polimi.ingsw.Development.DcBoard;
import it.polimi.ingsw.Events.EventBroker;
import it.polimi.ingsw.Leader.LeaderCardDeck;
import it.polimi.ingsw.Market.MarketTray;
import it.polimi.ingsw.Player.Player;

import java.util.List;

/**
 * A mock class used for testing purposes
 * TODO: delete this class once the real Game class is present
 */

public class Game {
    private DcBoard dcBoard;
    private List<Player> players;
    private LeaderCardDeck leaderCardDeck;
    private MarketTray marketTray;
    private EventBroker eventBroker;

    public Game() {
    }

    public DcBoard getDcBoard() {
        return dcBoard;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public LeaderCardDeck getLeaderCardDeck() {
        return leaderCardDeck;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public EventBroker getEventBroker() {
        return eventBroker;
    }
}
