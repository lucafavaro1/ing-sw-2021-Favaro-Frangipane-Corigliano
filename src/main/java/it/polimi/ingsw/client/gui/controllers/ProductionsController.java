package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.AddProductionEvent;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Production controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */
public class ProductionsController extends Controller{
    @FXML
    public VBox addProduction;
    public VBox activateProduction;
    public Button topunchboard;
    public Button activate;
    public Label numCoin;
    public Label numStone;
    public Label numServant;
    public Label numShield;

    private static ProductionsController instance;

    /**
     * Method implementing the singleton for the controller
     * @return the unique instance
     */
    public static ProductionsController getInstance() {
        if(instance == null)
            instance = new ProductionsController();
        return instance;
    }

    /**
     * Method to update the Added production column of the view
     * @param productions the list of productions to be added in order to be activated
     */
    public synchronized void updateAddedProductions(ArrayList<Production> productions) {
        VBox right = (VBox) getProductions().lookup("#activateProduction");
        VBox left = (VBox) getProductions().lookup("#addProduction");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(right.getChildren().size()!=0)
                    right.getChildren().clear();
                for(int i = 0; i < productions.size(); i++) {
                    Label prod = new Label(check(productions.get(i).toString()));
                    prod.setAlignment(Pos.CENTER);
                    prod.setScaleX(1.8);
                    prod.setScaleY(1.8);
                    prod.setMinWidth(150);
                    right.getChildren().add(prod);
                    if(left.getChildren().size()>1)
                        Controller.getCmb().sendEvent(new AddProductionEvent());
                    left.getChildren().clear();
                }
            }
        });
    }

    /**
     * Method to convert the base production syntax in a simple Base Production label
     * @param string the string of the production
     * @return an easy Base Production string
     */
    public String check(String string) {
        if (string.equals("{QUESTION=2} -> {QUESTION=1}"))
            return "Base Production";
        else
            return string;
    }

}
