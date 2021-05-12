package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class punchboardController extends Controller {
        @FXML // LISTA PLAYER PER VEDERE PLANCE
        public MenuButton playerList;

        @FXML   // LEV 1 DEVCARD
        public ImageView devCardLev1SX;
        public ImageView devCardLevl1MID;
        public ImageView devCardLev1DX;
        @FXML   // LEV 2 DEVCARD
        public ImageView devCardLev2SX;
        public ImageView devCardLevl2MID;
        public ImageView devCardLev2DX;
        @FXML   // LEV 3 DEVCARD
        public ImageView devCardLev3SX;
        public ImageView devCardLevl3MID;
        public ImageView devCardLev3DX;
        @FXML   // BONUS POINTS FAITHTRACK
        public ImageView bonusPointsFaith1;
        public ImageView bonusPointsFaith2;
        public ImageView bonusPointsFaith3;
        @FXML   // IS FIRST PLAYER? se non lo è metti opacity 0
        public ImageView calamaio_firstplayer;
        @FXML   // RISORSE DEPOSITO
        public ImageView res1slot1;
        public ImageView res1slot2;
        public ImageView res2slot2;
        public ImageView res1slot3;
        public ImageView res2slot3;
        public ImageView res3slot3;
        @FXML   // RISORSE FORZIERE
        public Label numCoin;
        public Label numStone;
        public Label numServant;
        public Label numShield;


        //Image img = new Image(getClass().getResourceAsStream("/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
        //devCardLev1SX.setImage(img);
        //devCardLev1SX.setImage(null);

        public void toMarketTray(MouseEvent mouseEvent) {
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            if(getMarkettray() == null)
                    System.out.println("è null");
            Scene x = getMarkettray();
            window.setScene(x);
            window.show();
        }

        public void toOwnLeaderCard(MouseEvent mouseEvent) {

        }

        public void toDcBoard(MouseEvent mouseEvent) {
        }
}
