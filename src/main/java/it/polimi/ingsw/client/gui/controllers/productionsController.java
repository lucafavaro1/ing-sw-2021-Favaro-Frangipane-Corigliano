package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.ActivateProductionEvent;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.awt.*;
import java.util.ArrayList;

public class productionsController extends Controller{
    @FXML
    public VBox addProduction;
    public VBox activateProduction;

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
        getCmb().sendEvent(new ActivateProductionEvent());  //@TODO: da sistemare la richiesta quale risorsa usare/ricevere per produzione base
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    public synchronized void updateAddedProductions(ArrayList<Production> productions) {
        VBox list = (VBox) getPrimarystage().getScene().lookup("#addProduction");
        list.setSpacing(100);
        list.setAlignment(Pos.CENTER);
        for(int i=0; i<productions.size(); i++) {
            Label prod = new Label(productions.get(i).toString());
            //list.getChildren().add(prod);       //@TODO: capire cosa non gli piace
        }
    }

}
