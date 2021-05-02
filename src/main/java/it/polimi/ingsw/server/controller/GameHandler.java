package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.GameClientHandler;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.List;

/**
 * GameHandler class handles a single match instantiating a game and a controller.
 */

public class GameHandler {
    private final List<GameClientHandler> clientHandlers = new ArrayList<>();
    private final Controller controller;
    private final Game game;
    private int started;
    private int numberOfPlayers;

    /**
     * Constructor of the game handler
     *
     * @param num number of players for the game
     */
    public GameHandler(int num) {
        this.game = new Game(num);
        this.numberOfPlayers = num;
        this.controller = new Controller(game, this);
    }

    public void addGameClientHandler(GameClientHandler clientHandler) {
        clientHandlers.add(clientHandler);
    }

    //TODO: discutere se è ok che gestore degli eventi che riceve dal server eventi e applica sul model
    // sia in gameClientHandler

    /**
     * for each human player in the game makes him choose the resources he can receive
     */
    private void prepareGame() {
        int resToChoose = 0;
        int faithToAdd;
        HumanPlayer player;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i > 0 && i % 2 == 0)
                resToChoose++;

            faithToAdd = i / 2;

            player = (HumanPlayer) game.getPlayers().get(i);
            player.getFaithTrack().increasePos(faithToAdd);
            for (int j = 0; j < resToChoose; j++) {
                player.getWarehouseDepots().tryAdding(Res_Enum.QUESTION.chooseResource(player));
            }
        }

        // TODO distribuire carte e far scegliere
    }

    //TODO: da aggiustare il gestore dei turni dei giocatori
    public void startGame() {
        prepareGame();
        while (!game.isLastRound()) {
            game.getPlayers().forEach(Player::play);
        }

        // TODO count points and say who is the winner
    }

    public int isStarted() {
        return started;
    }

    public List<GameClientHandler> getClientHandlers() {
        return clientHandlers;
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
