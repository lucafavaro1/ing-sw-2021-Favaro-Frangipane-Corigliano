package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.ActivateLeaderEvent;
import it.polimi.ingsw.common.Events.DiscardLeaderEvent;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

public class leaderCardController extends Controller {
    @FXML
    public ImageView leadercard1;
    public ImageView leadercard2;
    public ProgressBar leader1activate; // si usa .setProgress(1) per dire che è attiva
    public ProgressBar leader2activate; // si usa .setProgress(1) per dire che è attiva

    private static leaderCardController instance;

    public static leaderCardController getInstance() {
        if(instance == null)
            instance = new leaderCardController();
        return instance;
    }

    public void toPersonalBoard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    public void discardleader1(MouseEvent mouseEvent) {
        getCmb().sendEvent(new DiscardLeaderEvent(0));
    }

    public void discardleader2(MouseEvent mouseEvent) {
        getCmb().sendEvent(new DiscardLeaderEvent(1));
    }

    public void activate1(MouseEvent mouseEvent) {
        getCmb().sendEvent(new ActivateLeaderEvent(0));
    }

    public void activate2(MouseEvent mouseEvent) {
        getCmb().sendEvent(new ActivateLeaderEvent(1));
    }


    public synchronized void updateLeader(List<LeaderCard> leaderCards) {
        ImageView im = (ImageView) getLeadercards().lookup("#leadercard1");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(0))));
            im.setImage(img);
            if(leaderCards.get(0).isEnabled()) {
                ProgressBar pb1 = (ProgressBar) getLeadercards().lookup("#leader1activate");
                pb1.setProgress(1);
            } else {
                ProgressBar pb1 = (ProgressBar) getLeadercards().lookup("#leader1activate");
                pb1.setProgress(0);
            }
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

        im = (ImageView) getLeadercards().lookup("#leadercard2");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(1))));
            im.setImage(img);
            if(leaderCards.get(1).isEnabled()) {
                ProgressBar pb1 = (ProgressBar) getLeadercards().lookup("#leader2activate");
                pb1.setProgress(1);
            } else {
                ProgressBar pb1 = (ProgressBar) getLeadercards().lookup("#leader2activate");
                pb1.setProgress(0);
            }
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

    }

}
