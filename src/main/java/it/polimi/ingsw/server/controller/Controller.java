package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * This class is the main controller class of the game
 */
public class Controller {
    private final Game model;
    private final GameHandler gameHandler;

    /**
     * Contructor of the game controller
     *
     * @param model       game type reference
     * @param gameHandler game handler reference
     */
    public Controller(Game model, GameHandler gameHandler) {
        this.model = model;
        this.gameHandler = gameHandler;
    }
}
