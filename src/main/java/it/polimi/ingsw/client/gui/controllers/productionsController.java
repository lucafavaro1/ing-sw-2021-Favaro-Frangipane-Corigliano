package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.ActivateProductionEvent;
import it.polimi.ingsw.common.Events.AddProductionEvent;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class productionsController extends Controller{
    @FXML
    public VBox addProduction;
    public VBox activateProduction;
    public Button topunchboard;
    public Button activate;
    public Label numCoin;
    public Label numStone;
    public Label numServant;
    public Label numShield;

    private static productionsController instance;

    public static productionsController getInstance() {
        if(instance == null)
            instance = new productionsController();
        return instance;
    }

    public void toPersonalBoard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    public void activate(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    public synchronized void updateAddedProductions(ArrayList<Production> productions) {
        VBox list = (VBox) getPrimarystage().getScene().lookup("#activateProduction");
        VBox mybox = (VBox) getPrimarystage().getScene().lookup("#addProduction");
        list.setSpacing(50);
        list.setAlignment(Pos.CENTER);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(list.getChildren().size()!=0)
                    list.getChildren().clear();
                for(int i = 0; i < productions.size(); i++) {
                    Label prod = new Label(check(productions.get(i).toString()));
                    prod.setAlignment(Pos.CENTER);
                    prod.setScaleX(1.8);
                    prod.setScaleY(1.8);
                    prod.setMinWidth(150);
                    list.getChildren().add(prod);
                    if(mybox.getChildren().size()>1)
                        Controller.getCmb().sendEvent(new AddProductionEvent());
                    mybox.getChildren().clear();
                }
            }
        });
    }

    public String check(String string) {
        if (string.equals("{QUESTION=2} -> {QUESTION=1}"))
            return "Base Production";
        else
            return string;
    }

}
