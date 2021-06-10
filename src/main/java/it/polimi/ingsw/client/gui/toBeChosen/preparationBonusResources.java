package it.polimi.ingsw.client.gui.toBeChosen;

import it.polimi.ingsw.client.gui.controllers.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Procedure to load the pop up in order to choose the bonus resources at the beginning of the game
 */
public class preparationBonusResources {

    public void run() {
        Parent root = null;
        Stage pop = new Stage();
        pop.setTitle("Choose bonus resources");

        FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/ChooseResources.fxml")));
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            System.err.println("Loading error");
        }

        Scene reschoose = new Scene(root);

        pop.setScene(reschoose);
        pop.showAndWait();
    }
}
