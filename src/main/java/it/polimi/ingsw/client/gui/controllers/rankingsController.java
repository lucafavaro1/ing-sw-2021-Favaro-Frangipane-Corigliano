package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * GUI Controller: when the GAME_ENDED event is received, final rankings are displayed at screen
 */
public class rankingsController extends Controller {
    @FXML
    public Label nickname1;
    public Label nickname2;
    public Label nickname3;
    public Label nickname4;
    public Label score1;
    public Label score2;
    public Label score3;
    public Label score4;

    public void toHomepage(MouseEvent mouseEvent) {
        try {
            getPrimarystage().setMaxWidth(560);
            getPrimarystage().setMinWidth(560);
            getPrimarystage().setMinHeight(425);
            getPrimarystage().setMaxHeight(425);
            loadScene("ChooseMode.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(MouseEvent mouseEvent) {
        System.exit(0);
    }
}
