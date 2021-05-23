package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.GetMarketResEvent;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
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
    // COPY OF THE WAREHOUSE
    public ImageView res1slot1;
    public ImageView res1slot2;
    public ImageView res2slot2;
    public ImageView res1slot3;
    public ImageView res2slot3;
    public ImageView res3slot3;


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

    public void getCol1(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(false,0));
    }

    public void getCol2(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(false,1));
    }

    public void getCol3(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(false,2));
    }

    public void getCol4(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(false,3));
    }

    public void getRow1(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(true,0));
    }

    public void getRow2(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(true,1));
    }

    public void getRow3(MouseEvent mouseEvent) {
        getCmb().sendEvent(new GetMarketResEvent(true,2));
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

    public synchronized void updateWarehouseDepots(WarehouseDepots warehouseDepots) {
        ImageView im = (ImageView) getMarkettray().lookup("#res1slot1");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(1).get(0))));
            im.setImage(img);
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

        im = (ImageView) getMarkettray().lookup("#res1slot2");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(2).get(0))));
            im.setImage(img);
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

        im = (ImageView) getMarkettray().lookup("#res2slot2");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(2).get(1))));
            im.setImage(img);
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

        im = (ImageView) getMarkettray().lookup("#res1slot3");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(3).get(0))));
            im.setImage(img);
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

        im = (ImageView) getMarkettray().lookup("#res2slot3");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(3).get(1))));
            im.setImage(img);
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

        im = (ImageView) getMarkettray().lookup("#res3slot3");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(3).get(2))));
            im.setImage(img);
        } catch (IndexOutOfBoundsException e) {
            im.setImage(null);
        }

    }

}
