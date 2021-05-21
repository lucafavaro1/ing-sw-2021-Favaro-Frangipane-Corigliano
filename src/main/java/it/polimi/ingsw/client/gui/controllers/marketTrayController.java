package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.GetMarketResEvent;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Market tray controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */
public class marketTrayController extends Controller{
    private boolean rowcol;
    @FXML
    public TextField number;        // number of row or column
    public ImageView freeball;      // freeball image
    // MARBLES OF THE MARKETTRAY
    public ImageView row3col1;
    public ImageView row2col1;
    public ImageView row1col1;
    public ImageView row1col2;
    public ImageView row2col2;
    public ImageView row3col2;
    public ImageView row1col3;
    public ImageView row2col3;
    public ImageView row3col3;
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
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    public void rowChosen(MouseEvent mouseEvent) {
        rowcol = true;
    }

    public void columnChosen(MouseEvent mouseEvent) {
        rowcol = false;
    }

    public void confirmNumber(MouseEvent mouseEvent) {
        int num = Integer.parseInt(number.getText());
        getCmb().sendEvent(new GetMarketResEvent(rowcol,num-1));  // EVENTO RICHIEDI RISORSE MARKET
        number.setText("");
        //GUIUserInterface x = (GUIUserInterface) UserInterface.getInstance();
        //x.choose(1);

    }


    public void conversion(MarketTray mymarket) {

        ImageView im  = (ImageView) getMarkettray().lookup("#row1col1");
        Image img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(0))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row1col2");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(1))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row1col3");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(2))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row1col4");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(0).get(3))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row2col1");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(1).get(0))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row2col2");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(1).get(1))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row2col3");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(1).get(2))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row2col4");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(1).get(3))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row3col1");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(2).get(0))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row3col2");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(2).get(1))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row3col3");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(2).get(2))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#row3col4");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(2).get(3))));
        im.setImage(img);

        im  = (ImageView) getMarkettray().lookup("#freeball");
        img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getFreeball())));
        im.setImage(img);
    }
}
