package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.GetMarketResEvent;
import it.polimi.ingsw.server.model.Market.MarketTray;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class marketTrayController extends Controller{
    private boolean rowcol;
    @FXML
    public TextField number;        // number of row or column
    public ImageView freeball;      // freeball image
    // MARBLES OF THE MARKETTRAY
    public ImageView row3col1;
    public ImageView row2col1;
    @FXML
    public ImageView row1col1;
    @FXML
    public ImageView row1col2;
    public ImageView row2col2;
    public ImageView row3col2;
    @FXML
    public ImageView row1col3;
    public ImageView row2col3;
    public ImageView row3col3;
    @FXML
    public ImageView row1col4;
    public ImageView row2col4;
    public ImageView row3col4;

    private static marketTrayController instance;

    public static marketTrayController getInstance() {
        if(instance == null)
            instance = new marketTrayController();
        return instance;
    }

    public void toPersonalBoard(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene x = getPersonalpunchboard();
        window.setScene(x);
        window.show();
    }

    public void rowChosen(MouseEvent mouseEvent) {
        rowcol = true;
    }

    public void columnChosen(MouseEvent mouseEvent) {
        rowcol = false;
    }

    public void confirmNumber(MouseEvent mouseEvent) {
        int num = Integer.parseInt(number.getText());
        getCmb().sendEvent(new GetMarketResEvent(rowcol,num));
    }

    public void conversion(MarketTray mymarket) {
        Image img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(0))));
        row1col1.setImage(img);
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(1))));
        row1col2.setImage(img);
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(2))));
        row1col3.setImage(img);
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(3))));
        row1col4.setImage(img);
    }
}
