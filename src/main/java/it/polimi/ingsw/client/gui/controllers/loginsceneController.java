package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.GameServer;
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

public class loginsceneController{

    private Socket clientSocket;
    private int lobby;

    public int getLobby() {
        return lobby;
    }

    public void setLobby(int lobby) {
        this.lobby = lobby;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @FXML
    private TextField ipserver;
    @FXML
    private TextField port;
    @FXML
    private Label invalid;


    public synchronized void connectionEvent(MouseEvent mouseEvent) throws IOException {
        String ip;
        int porta;
        setClientSocket(null);
        boolean flag = true;

        try {
            ip = ipserver.getText();
            porta = Integer.parseInt(port.getText());
            setClientSocket(new Socket(ip, porta));
        } catch (IOException e) {
            ipserver.setText("");
            port.setText("");
        }


        if (getClientSocket() == null) {
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
