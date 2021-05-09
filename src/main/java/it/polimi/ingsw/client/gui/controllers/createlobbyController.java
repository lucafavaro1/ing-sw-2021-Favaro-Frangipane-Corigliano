package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class createlobbyController extends Controller {

    public void TwoPlayersEvent(MouseEvent mouseEvent) throws  IOException{
        System.out.println("Due Giocatori");
        getOut().println("2");
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/waitingForPlayersScene.fxml")));
        Parent root = (Parent) loader.load();

        Scene firstscene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(firstscene);
        window.show();
    }

    public void ThreePlayersEvent(MouseEvent mouseEvent) throws IOException{
        System.out.println("Tre Giocatori");
        getOut().println("3");
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/waitingForPlayersScene.fxml")));
        Parent root = (Parent) loader.load();

        Scene firstscene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(firstscene);
        window.show();
    }

    public void FourPlayersEvent(MouseEvent mouseEvent) throws  IOException{
        System.out.println("Quattro Giocatori");
        getOut().println("4");
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/waitingForPlayersScene.fxml")));
        Parent root = (Parent) loader.load();

        Scene firstscene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(firstscene);
        window.show();
    }

}
