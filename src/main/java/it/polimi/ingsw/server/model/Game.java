package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintMarketTrayEvent;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Leader.LeaderCardDeck;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Game class, representing a lobby
 */
public class Game implements EventHandler {
    private final List<Player> players = new ArrayList<>();
    private final DcBoard dcBoard;
    private final LeaderCardDeck leaderCardDeck;
    private final MarketTray marketTray;
    private final EventBroker eventBroker = new EventBroker();
    private boolean lastRound = false;

    /**
     * Constructor of the Game class doing different things based on the number of players
     * if the players are just 1, it creates a singlePlayer game
     * if the players are between 2 and 4, it creates a multiplayer game, shuffling the players
     *
     * @param nPlayers number of human players in the game
     */
    public Game(int nPlayers) {
        // creating the players in base of the nPlayers passed
        if (nPlayers == 1) {
            // creating the game in single player mode
            players.add(new HumanPlayer(this));

            try {
                players.add(new CPUPlayer(this));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("ERROR: CPU player can't be created\n");
            }
        } else if (nPlayers >= 2 && nPlayers <= 4) {
            // creating the game in multiplayer mode
            for (int i = 0; i < nPlayers; i++) {
                players.add(new HumanPlayer(this));
            }
        } else {
            throw new IllegalArgumentException("ERROR: Game can't be created. Bad number of players");
        }

        // creating the MARKET TRAY
        marketTray = new MarketTray();
        // sending to the client the initial Market Tray
        eventBroker.post(new PrintMarketTrayEvent(this), false);

        // creating the DCBOARD
        try {
            dcBoard = new DcBoard(this);

            // sending to the client the initial dcBoard
            eventBroker.post(new PrintDcBoardEvent(this), false);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ERROR: Couldn't find the file for the Development Cards. Game can't be created");
        }

        // creating the LEADER CARD DECK
        try {
            leaderCardDeck = new LeaderCardDeck();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ERROR: Game can't be created. Leader cards not found!\n");
        }

        // subscribing to the events
        eventBroker.subscribe(this, EnumSet.of(Events_Enum.LAST_ROUND));
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

    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }
}