package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

// TODO: fornirgli in qualche modo i numeri disponibili o leggere il mess ricevuto dal server che dice se è valido o meno
// TODO: il problema è che il buffer in ha tutti i mess precedenti non letti quindi se adesso fai in.readline legge il primo mess
// TODO: spedito dal server al client quando ha fatto l'accesso alla prima schermata
// TODO: in questo momento funziona perfetto solo se metti il numero della lobby che sai già che esiste e il numero è corretto

public class selectLobbyController extends Controller{
    @FXML
    public TextField number;

    int numberlobby=0;

    public void joinLobbyEvent(MouseEvent mouseEvent) throws IOException {
            numberlobby = Integer.parseInt(number.getText());
            getOut().println(numberlobby);
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/joiningLobbyScene.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            window.show();
    }
}
