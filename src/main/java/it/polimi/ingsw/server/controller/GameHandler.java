package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.GameClientHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.GameServer;
import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

/**
 * GameHandler class handles a single match instantiating a game and a controller.
 */
public class GameHandler {
    private final GameClientHandler server;
    private final Controller controller;
    private final Game game;
    private int started;
    private int numberOfPlayers;

    /**
     * Constructor of the game handler
     * @param server reference to server
     * @param num number of players for the game
     */
    public GameHandler (GameClientHandler server, int num) {
        this.server = server;
        this.game = new Game(num);
        this.numberOfPlayers = num;
        this.controller = new Controller(game,this);
    }

    //TODO: discutere se è ok che gestore degli eventi che riceve dal server eventi e applica sul model
    // sia in gameClientHandler

    public int isStarted(){
        return started;
    }

    public GameClientHandler getServer() {
        return server;
    }

    public Controller getController() {
        return controller;
    }

    public Game getGame() {
        return game;
    }

    public int getStarted() {
        return started;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
