package it.polimi.ingsw.client.gui.toBeChosen;

import it.polimi.ingsw.client.UserInterface;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Procedure to load the pop up in order to choose where to put each resource taken from the market
 */
public class resourcesWherePut {

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
            Button button = new Button(toBeChosen.get(i).getClass().getSimpleName());
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
        pop.showAndWait();
    }
}
