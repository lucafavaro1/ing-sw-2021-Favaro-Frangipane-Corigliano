package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * GUI Controller: choosing mode of play (single player or multi player)
 */
public class chooseModeController extends Controller {

    public void singleplayerEvent(MouseEvent mouseEvent) throws IOException {

        getOut().println("1");

        loadScene("SingleChooseNick.fxml");

        System.out.println(getIn().readLine());
        System.out.println(getIn().readLine());
    }

    public void multiplayerEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println("2");

        loadScene("MultiJoinOrCreate.fxml");

        System.out.println(getIn().readLine());
        System.out.println(getIn().readLine());
    }
}
