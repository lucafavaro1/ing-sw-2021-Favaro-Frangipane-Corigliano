package it.polimi.ingsw.client.gui.toBeChosen;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

/**
 * Procedure to load the pop up in order to choose which resource you want to take / spend
 * in those cases in which you can choose like the base production
 */
public class resourcesTakeSpend {

    public void run(List<?> toBeChosen, String message) {
        Parent root = null;
        HBox layout = new HBox(175);
        Stage pop = new Stage();
        pop.initModality(Modality.APPLICATION_MODAL);
        pop.setMinWidth(700);
        pop.setMinHeight(200);
        pop.setTitle(message);

        layout.setStyle("-fx-background-color: #F8EFD1");

        for (int i = 0; i < toBeChosen.size(); i++) {
            ImageView img = new ImageView();
            Image coin = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.COIN)));
            Image shield = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.SHIELD)));
            Image stone = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.STONE)));
            Image servant = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.SERVANT)));
            Button button = new Button();
            button.setMaxSize(5,5);
            button.setMinSize(5,5);
            int x = i;
            button.setOnAction(e -> {
                UserInterface.getInstance().choose(x + 1);
                pop.close();
            });
            button.setScaleX(0.5);
            button.setScaleY(0.5);
            switch (toBeChosen.get(i).toString()) {
                case "COIN":
                    img.setImage(coin);
                    button.setGraphic(img);
                    break;
                case "STONE":
                    img.setImage(stone);
                    button.setGraphic(img);
                    break;
                case "SERVANT":
                    img.setImage(servant);
                    button.setGraphic(img);
                    break;
                case "SHIELD":
                    img.setImage(shield);
                    button.setGraphic(img);
                    break;
            }
            layout.getChildren().add(button);
            layout.setAlignment(Pos.CENTER);
        }

        Scene scene = new Scene(layout);
        pop.setScene(scene);
        pop.showAndWait();
    }
}
