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
/**
 * GUI Controller: choosing nickname in case of single player game
 */
public class singleChooseNickController extends Controller{

    @FXML
    private TextField text;

    public void enterEvent(MouseEvent mouseEvent) throws IOException, InterruptedException {
        String message = text.getText();

        if (message.isBlank()) {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/SingleChooseNickErr.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();

        } else {
            getOut().println(message);
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/Punchboard.fxml")));
            Parent root = (Parent) loader.load();
            FXMLLoader loader1 = new FXMLLoader((getClass().getResource("/Client/Punchboard.fxml")));
            Parent root1 = (Parent) loader1.load();

            Scene punchboard = new Scene(root);
            Scene market = new Scene(root1);

            setPersonalpunchboard(punchboard);
            setMarkettray(market);

            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(punchboard);
            window.show();
            System.out.println(getIn().readLine());
            System.out.println(getIn().readLine());

        }
    }

}
