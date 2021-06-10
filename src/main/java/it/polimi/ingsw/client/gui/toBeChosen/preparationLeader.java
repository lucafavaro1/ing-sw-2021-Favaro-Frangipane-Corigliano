package it.polimi.ingsw.client.gui.toBeChosen;

import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Procedure to load the pop up in order to choose 2 of 4 leader cards at the beginning of the game
 */

public class preparationLeader {

    public void run(List<?> toBeChosen) {
        ArrayList<LeaderCard> leaderCards = (ArrayList<LeaderCard>) toBeChosen;
        Parent root = null;
        Stage pop = new Stage();
        pop.setTitle("Choosing Leader Card");

        FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/ChooseLeaderCard.fxml")));
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            System.err.println("Loading error");
        }

        Scene leaderchoose = new Scene(root);

        // set delle 4 carte leader
        for(int i=0; i<leaderCards.size(); i++) {
            if(leaderCards.size() == 3) {
                Label pickCards = (Label) leaderchoose.lookup("#pickCards");
                pickCards.setText("Pick 1 more Leader card");
            }
            ImageView im = (ImageView) leaderchoose.lookup("#leadercard".concat(Integer.toString(i+1)));
            Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(i))));
            im.setImage(img);
        }

        pop.setScene(leaderchoose);
        pop.showAndWait();
    }
}


