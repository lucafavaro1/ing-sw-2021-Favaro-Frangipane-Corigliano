package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.GameServer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class multiplayerController extends Controller{

    public void joinlobbyEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println("2");
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/selectLobbyScene.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
    }


    public void createlobbyEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println("1");
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/choosenickmulti.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
    }

}
