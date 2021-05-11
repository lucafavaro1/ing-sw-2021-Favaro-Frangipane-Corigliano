package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class punchboardController extends Controller {
    @FXML
    public ImageView devCardLev1SX;

    public void toMarketTray(MouseEvent mouseEvent) {      // tentativo per vedere funzionamento della set image
        Image img = new Image(getClass().getResourceAsStream("/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
        devCardLev1SX.setImage(img);
    }

    public void toOwnLeaderCard(MouseEvent mouseEvent) {
    }

    public void toDcBoard(MouseEvent mouseEvent) {
    }
}
