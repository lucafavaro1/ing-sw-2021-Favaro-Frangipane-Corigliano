package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class loginsceneController {

    @FXML
    private TextField ipserver;
    @FXML
    private TextField port;
    @FXML
    private Label invalid;

    public synchronized void connectionEvent(MouseEvent mouseEvent) throws IOException {
        String ip;
        int porta;
        Socket clientSocket = null;
        boolean flag = true;

        try {
            ip = ipserver.getText();
            porta = Integer.parseInt(port.getText());
            clientSocket = new Socket(ip, porta);
        } catch (IOException e) {
            ipserver.setText("");
            port.setText("");
        }


        if (clientSocket == null) {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/loginsceneerr.fxml")));
            Parent root = (Parent) loader.load();

            Scene loginscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(loginscene);
            window.show();
        } else {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/firstscene.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            window.show();
        }
    }
}
