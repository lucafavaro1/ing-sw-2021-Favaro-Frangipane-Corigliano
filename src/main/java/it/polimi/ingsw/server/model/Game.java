package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Leader.LeaderCardDeck;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * A mock class used for testing purposes
 */
public class Game implements Runnable, EventHandler {
    private DcBoard dcBoard;
    private LeaderCardDeck leaderCardDeck;
    private boolean lastRound = false;
    private final List<Player> players = new ArrayList<>();
    private final MarketTray marketTray = new MarketTray();
    private final EventBroker eventBroker = new EventBroker();

    /**
     * Constructor of the Game class doing different things based on the number of players
     * if the players are just 1, it creates a singlePlayer game
     * if the players are between 2 and 4, it creates a multiplayer game, shuffling the players and setting to true
     * the "first player" flag in the player
     *
     * @param nPlayers number of human players in the game
     */
    public Game(int nPlayers) {
        // creating the players in base of the nPlayers passed
        if (nPlayers == 1) {
            // creating the game in single player mode
            players.add(new HumanPlayer(this, 0));

            try {
                players.add(new CPUPlayer(this, 1));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("ERROR: CPU player can't be created\n");
            }
        } else if (nPlayers >= 2 && nPlayers <= 4) {
            // creating the game in multiplayer mode
            for (int i = 0; i < nPlayers; i++) {
                players.add(new HumanPlayer(this, i));
            }

            // shuffling the order of players
            Collections.shuffle(players);
        } else {
            throw new IllegalArgumentException("ERROR: Game can't be created. Wrong number of players");
        }

        // creating the DcBoard
        try {
            dcBoard = new DcBoard(this);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ERROR: Couldn't find the file for the Development Cards. Game can't be created");
        }

        // creating the leader card deck
        try {
            leaderCardDeck = new LeaderCardDeck();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("ERROR: Game can't be created. Leader cards not found!\n");
        }

        // choosing the first player
        players.get(0).setFirstPlayer(true);

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

    // TODO to be developed: distribute the initial resources, distribute LeaderCards
    private void prepareGame() {

    }

    @Override
    public void run() {
        prepareGame();
        while (!lastRound) {
            players.forEach(Player::play);
        }
    }
}
