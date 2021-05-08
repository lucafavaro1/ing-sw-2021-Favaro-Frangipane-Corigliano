package it.polimi.ingsw.client.gui.controllers;

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

public class multiplayerController extends loginsceneController{

    public void joinlobbyEvent(MouseEvent mouseEvent) throws IOException {
        System.out.println("Join lobby clicked");


        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/selectLobbyScene.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
    }


    public void createlobbyEvent(MouseEvent mouseEvent) throws IOException {
        System.out.println("Create lobby clicked");

        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/createLobbyScene.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
    }

    public void backevent(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/firstscene.fxml")));
        Parent root = (Parent) loader.load();

        Scene firstscene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(firstscene);
        window.show();
    }

}
