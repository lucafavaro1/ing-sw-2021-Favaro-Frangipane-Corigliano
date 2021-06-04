package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.ActivateLeaderEvent;
import it.polimi.ingsw.common.Events.DiscardLeaderEvent;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.List;

/**
 * Leader Card controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */

public class LeaderCardController extends Controller {
    @FXML
    public ImageView leadercard1;
    public ImageView leadercard2;
    public ProgressBar leader1activate; // si usa .setProgress(1) per dire che è attiva
    public ProgressBar leader2activate; // si usa .setProgress(1) per dire che è attiva
    public Label watching;

    private static LeaderCardController instance;

    /**
     * Method implementing the singleton for the controller
     * @return the unique instance
     */
    public static LeaderCardController getInstance() {
        if(instance == null)
            instance = new LeaderCardController();
        return instance;
    }

    /**
     * Go to personal board scene
     * @param mouseEvent click on To Personal Board button
     */
    public void toPersonalBoard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    /**
     * Discard the left card in the leader card scene
     * @param mouseEvent click on left discard
     */
    public void discardleader1(MouseEvent mouseEvent) {
        getCmb().sendEvent(new DiscardLeaderEvent(0));
    }

    /**
     * Discard the right card in the leader card scene
     * @param mouseEvent click on the right discard
     */
    public void discardleader2(MouseEvent mouseEvent) {
        getCmb().sendEvent(new DiscardLeaderEvent(1));
    }

    /**
     * Activate the left card in the leader card scene
     * @param mouseEvent click on the left card image
     */
    public void activate1(MouseEvent mouseEvent) {
        ImageView l1 = (ImageView) getLeadercards().lookup("#leadercard1");
        ProgressBar b1 = (ProgressBar) getLeadercards().lookup("#leader1activate");
        if(l1.getImage() != null && b1.getProgress()!=1)
            getCmb().sendEvent(new ActivateLeaderEvent(0));
    }

    /**
     * Activate the right card in the leader card scene
     * @param mouseEvent click on the right card image
     */
    public void activate2(MouseEvent mouseEvent) {
        ProgressBar l1 = (ProgressBar) getLeadercards().lookup("#leader1activate");
        ImageView l2 = (ImageView) getLeadercards().lookup("#leadercard1");
        ProgressBar b2 = (ProgressBar) getLeadercards().lookup("#leader1activate");

        if(l1.getProgress() == 1)
            getCmb().sendEvent(new ActivateLeaderEvent(0));
        else {
            if(l2.getImage()!=null && b2.getProgress()!=1)
            getCmb().sendEvent(new ActivateLeaderEvent(1));
        }
    }

    /**
     * Update leader card scene with current leader cards and their progression bar for activate/deactivate
     * @param leaderCards the array of leader cards
     * @param personal 1 if you want to update your personal, 0 if want to update the currentscene
     */
    public synchronized void updateLeader(List<LeaderCard> leaderCards, boolean personal, Stage other) {
        Scene x = null;
        if(personal)
            x = getLeadercards();
        else
            x = other.getScene();

        ImageView im = (ImageView) x.lookup("#leadercard1");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(0))));
            im.setImage(img);
            if(leaderCards.get(0).isEnabled()) {
                ProgressBar pb1 = (ProgressBar) x.lookup("#leader1activate");
                pb1.setProgress(1);
            } else {
                ProgressBar pb1 = (ProgressBar) x.lookup("#leader1activate");
                pb1.setProgress(0);
            }
        } catch (Exception e) {
            if(e.getClass() == NullPointerException.class)
                im.setImage(new Image("/GraphicsGUI/back/leader_back.png"));
            else
                im.setImage(null);
        }

        im = (ImageView) x.lookup("#leadercard2");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(1))));
            im.setImage(img);
            if(leaderCards.get(1).isEnabled()) {
                ProgressBar pb1 = (ProgressBar) x.lookup("#leader2activate");
                pb1.setProgress(1);
            } else {
                ProgressBar pb1 = (ProgressBar) x.lookup("#leader2activate");
                pb1.setProgress(0);
            }
        } catch (Exception e) {
            if(e.getClass() == NullPointerException.class)
                im.setImage(new Image("/GraphicsGUI/back/leader_back.png"));
            else
                im.setImage(null);
        }

    }

}
