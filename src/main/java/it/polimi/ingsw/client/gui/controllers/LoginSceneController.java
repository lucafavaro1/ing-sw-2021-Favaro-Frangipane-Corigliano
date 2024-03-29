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
public class LoginSceneController extends Controller{

    @FXML
    private TextField ipserver;
    @FXML
    private TextField port;
    @FXML
    private Label invalid;

    /**
     * Connect to the server by inserting ip-port and clicking connect button
     * @param mouseEvent click on the Connect button
     * @throws IOException if the scene has wrong name
     */
    public synchronized void connectionEvent(MouseEvent mouseEvent) throws IOException {
        String ip = null;
        int porta = 48000;
        Socket bypass = null;

        try {
            ip = ipserver.getText();
            String str = port.getText();
            if(!str.isBlank())
                porta = Integer.parseInt(str);
            bypass = new Socket(ip, porta);
        } catch (IOException e) {
            ipserver.setText("");
            port.setText("");
        }


        if (porta < 1024 || bypass == null) {
            // NON SOSTITUIRE CON METODO loadScene()
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/LoginSceneErr.fxml")));
            Parent root = (Parent) loader.load();

            Scene loginscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(loginscene);
            setPrimarystage(window);
            window.show();

        } else {
            setClientSocket(bypass);
            setBw(new BufferedWriter(new OutputStreamWriter(getClientSocket().getOutputStream())));
            setOut(new PrintWriter(getBw(), true));
            setIn(new BufferedReader(new InputStreamReader(getClientSocket().getInputStream())));

            // NON SOSTITUIRE CON METODO loadScene()
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/ChooseMode.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            setPrimarystage(window);
            window.show();
            System.out.println(getIn().readLine());

        }
    }
}
