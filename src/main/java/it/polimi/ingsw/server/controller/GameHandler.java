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
public class GameHandler extends Thread {
    private final List<GameClientHandler> clientHandlers = new ArrayList<>();
    private final Controller controller;
    private final Game game;
    private int started;
    private int maxPlayers;

    /**
     * Constructor of the game handler
     *
     * @param num number of players for the game
     */
    public GameHandler(int num) {
        this.game = new Game(num);
        this.maxPlayers = num;
        this.controller = new Controller(game, this);
    }

    /**
     * adds a client handler to the list
     * TODO check if it's useful
     *
     * @param clientHandler client handler to add to the game
     * @return true if the number of players for the game is reached, false otherwise
     */
    public boolean addGameClientHandler(GameClientHandler clientHandler) {
        clientHandlers.add(clientHandler);

        return clientHandlers.size() == maxPlayers;
    }

    /**
     * For each human player in the game makes him choose the resources he can receive
     */
    public void prepareGame() {
        int resToChoose = 0;
        int faithToAdd;
        HumanPlayer player;
        for (int i = 0; i < maxPlayers; i++) {
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

    /**
     * Method to start the game for each player of the match
     */
    @Override
    public void run() {
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

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
