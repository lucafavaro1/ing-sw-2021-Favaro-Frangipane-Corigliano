package it.polimi.ingsw.client.gui.toBeChosen;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Procedure to load the pop up in order to choose where to put the development card bought
 */

public class devcardWherePut {

    public void run(List<?> toBeChosen, String message) {
        Parent root = null;
        Stage pop = new Stage();
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setMinWidth(550);
        pop.setMinHeight(200);

        pop.setTitle(message);
        HBox layout = new HBox(toBeChosen.size());
        layout.setStyle("-fx-background-color: #F8EFD1");
        layout.setSpacing(100);

        for (int i = 0; i < toBeChosen.size(); i++) {
            Button button = new Button(devcheck(toBeChosen.get(i).toString()));
            int x = i;
            button.setOnAction(e -> {
                UserInterface.getInstance().choose(x + 1);
                pop.close();
            });
            button.setScaleX(1.8);
            button.setScaleY(1.8);
            layout.getChildren().add(button);
            layout.setAlignment(Pos.CENTER);
        }

        Scene scene = new Scene(layout);
        pop.setScene(scene);
        Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
        pop.showAndWait();
    }

    /**
     * Method that convert the number of dev cards slots into text
     * @param string the number
     * @return the name of the slot
     */
    public String devcheck(String string) {
        switch (string) {
            case "1":
                return "LEFT";
            case "2":
                return "MIDDLE";
            case "3":
                return "RIGHT";
            default:
                return string;
        }
    }
}
