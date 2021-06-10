package it.polimi.ingsw.client.gui.toBeChosen;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.common.Events.ActivateProductionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * Procedure to load the productions scene with all the possible "to add" productions available
 */

public class productionsSetup {

    public void run(List<?> toBeChosen, String message) {
        VBox left = null;
        VBox right = null;

        left = (VBox) Controller.getProductions().lookup("#addProduction");
        left.setSpacing(50);
        left.setAlignment(Pos.CENTER);
        right = (VBox) Controller.getProductions().lookup("#activateProduction") ;
        right.setSpacing(50);
        right.setAlignment(Pos.CENTER);

        Button topunchboard = (Button) Controller.getProductions().lookup("#topunchboard");

        topunchboard.setOnMouseClicked(e -> {
            VBox left1 = (VBox) Controller.getProductions().lookup("#addProduction");
            if(left1.getChildren().size()!=0)
                UserInterface.getInstance().choose(toBeChosen.size());
            left1.getChildren().clear();
            Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
            Controller.getPrimarystage().show();
        });

        Button activate = (Button) Controller.getProductions().lookup("#activate");

        activate.setOnMouseClicked(e -> {
            VBox right1 = (VBox) Controller.getProductions().lookup("#activateProduction");
            VBox left1 = (VBox) Controller.getProductions().lookup("#addProduction");
            if(left1.getChildren().size()!=0)
                UserInterface.getInstance().choose(toBeChosen.size());
            Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
            Controller.getPrimarystage().show();
            Controller.getCmb().sendEvent(new ActivateProductionEvent());
            right1.getChildren().clear();
            left1.getChildren().clear();
        });

        for (int i = 0; i < toBeChosen.size()-1; i++) {
            Button button = new Button(check(toBeChosen.get(i).toString()));

            int x = i;
            button.setOnAction(e -> {
                UserInterface.getInstance().choose(x + 1);
            });
            button.setScaleX(1.5);
            button.setScaleY(1.5);
            button.setMinWidth(150);
            left.getChildren().add(button);
        }

    }

    /**
     * Method to convert the text of the basic production into a simple string
     * @param string the string received (for add production list)
     * @return "Base Production" string format
     */
    public String check(String string) {
        if (string.equals("{QUESTION=2} -> {QUESTION=1}"))
            return "Base Production";
        else
            return string;
    }
}
