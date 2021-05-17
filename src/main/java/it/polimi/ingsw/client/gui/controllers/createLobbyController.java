package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI Controller: choosing number of players in case of new multiplayer game
 */
public class createLobbyController extends Controller {

    public void TwoPlayersEvent(MouseEvent mouseEvent) throws IOException{
        System.out.println("Due Giocatori");
        getOut().println("2");

        loadItems();

        System.out.println(getIn().readLine());

        loadScene("WaitingForPlayersScene.fxml");
    }

    public void ThreePlayersEvent(MouseEvent mouseEvent) throws IOException{
        System.out.println("Tre Giocatori");
        getOut().println("3");

        loadItems();

        System.out.println(getIn().readLine());

        loadScene("WaitingForPlayersScene.fxml");

    }

    public void FourPlayersEvent(MouseEvent mouseEvent) throws  IOException{
        System.out.println("Quattro Giocatori");
        getOut().println("4");

        loadItems();

        System.out.println(getIn().readLine());

        loadScene("WaitingForPlayersScene.fxml");
    }

}
