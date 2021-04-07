package it.polimi.ingsw;

import it.polimi.ingsw.Development.DcBoard;
import it.polimi.ingsw.Events.EventBroker;
import it.polimi.ingsw.Leader.LeaderCardDeck;
import it.polimi.ingsw.Market.MarketTray;
import it.polimi.ingsw.Player.CPUPlayer;
import it.polimi.ingsw.Player.HumanPlayer;
import it.polimi.ingsw.Player.Player;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock class used for testing purposes
 */

public class Game {
    private DcBoard dcBoard;
    private LeaderCardDeck leaderCardDeck;
    private final List<Player> players = new ArrayList<>();
    private final MarketTray marketTray = new MarketTray();
    private final EventBroker eventBroker = new EventBroker();

    /**
     * Constructor or the Game class doing different things based on the number of players
     * @param nPlayers number of players
     */
    public Game(int nPlayers) {
        if (nPlayers == 1) {
            players.add(new HumanPlayer(this, 0));

            try {
                players.add(new CPUPlayer(this, 1));
            } catch (FileNotFoundException e) {
                System.out.println("ERROR: CPU player can't be created\n");
            }
        } else if (nPlayers >= 2 && nPlayers <= 5) {
            for (int i = 0; i < nPlayers; i++) {
                players.add(new HumanPlayer(this, i));
            }
        } else {
            System.out.println("ERROR: Game can't be created\n");
        }
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

    /**
     * Method to decide random who is the first player
     */
    public void decideFirstPlayer() {
        int n = players.size();
        int random = (int)Math.floor(Math.random()*(5-1+1)+1);
        players.get(random).setFirstPlayer(true);
    }

}
