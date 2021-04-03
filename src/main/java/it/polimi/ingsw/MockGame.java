package it.polimi.ingsw;

import it.polimi.ingsw.Development.DevelopmentCardDeck;
import it.polimi.ingsw.Events.EventBroker;
import it.polimi.ingsw.Leader.LeaderCardDeck;
import it.polimi.ingsw.Market.MarketTray;
import it.polimi.ingsw.Player.Player;

import java.util.List;

/**
 * A mock class used for testing purposes
 * TODO: delete this class once the real Game class is present
 */

public class MockGame {
    // TODO: private DcBoard dcBoard;
    private List<Player> players;
    private LeaderCardDeck leaderCardDeck;
    private DevelopmentCardDeck developmentCardDeck;
    private MarketTray marketTray;
    private EventBroker eventBroker;

    public MockGame() {
    }

    /* TODO: public DcBoard getDcBoard() {
        return dcBoard;
    }*/

    public List<Player> getPlayers() {
        return players;
    }

    public LeaderCardDeck getLeaderCardDeck() {
        return leaderCardDeck;
    }

    public DevelopmentCardDeck getDevelopmentCardDeck() {
        return developmentCardDeck;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public EventBroker getEventBroker() {
        return eventBroker;
    }
}
