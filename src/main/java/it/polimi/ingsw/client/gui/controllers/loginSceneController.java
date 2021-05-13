package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
/**
 * GUI Controller: logging the game server
 */
public class loginSceneController extends Controller{

    @FXML
    private TextField ipserver;
    @FXML
    private TextField port;
    @FXML
    private Label invalid;


    public synchronized void connectionEvent(MouseEvent mouseEvent) throws IOException {
        String ip = null;
        int porta=0;
        Socket bypass = null;

        try {
            ip = ipserver.getText();
            porta = Integer.parseInt(port.getText());
            bypass = new Socket(ip, porta);
        } catch (IOException e) {
            ipserver.setText("");
            port.setText("");
        }


        if (!ip.equals("") || porta!=48000) {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/LoginSceneErr.fxml")));
            Parent root = (Parent) loader.load();

            Scene loginscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(loginscene);
            window.show();
        } else {
            setClientSocket(bypass);
            setBw(new BufferedWriter(new OutputStreamWriter(getClientSocket().getOutputStream())));
            setOut(new PrintWriter(getBw(), true));
            setIn(new BufferedReader(new InputStreamReader(getClientSocket().getInputStream())));
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/ChooseMode.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            window.show();
            System.out.println(getIn().readLine());

        }
    }
}
