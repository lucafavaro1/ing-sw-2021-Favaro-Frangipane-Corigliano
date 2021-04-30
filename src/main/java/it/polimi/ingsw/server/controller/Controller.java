package it.polimi.ingsw.server.controller;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.Player.Player;

/**
 * This class is the main controller class of the game
 */
public class Controller {
    private final Game model;
    private final GameHandler gameHandler;

    //TODO: da aggiustare il gestore dei turni dei giocatori
    public void startGame() {
        model.prepareGame();
        while (!model.isLastRound()) {
            model.getPlayers().forEach(Player::play);
        }
    }

    /**
     * Contructor of the game controller
     * @param model game type reference
     * @param gameHandler game handler reference
     */
    public Controller(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
    }
}
