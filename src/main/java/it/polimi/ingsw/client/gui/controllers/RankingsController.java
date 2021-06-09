package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * GUI Controller: when the GAME_ENDED event is received, final rankings are displayed at screen
 */
public class RankingsController extends Controller {
    @FXML
    public Label nickname1;
    public Label nickname2;
    public Label nickname3;
    public Label nickname4;
    public Label score1;
    public Label score2;
    public Label score3;
    public Label score4;

    /**
     * Close button to shut down the game
     * @param mouseEvent click on close button
     */
    public void close(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
