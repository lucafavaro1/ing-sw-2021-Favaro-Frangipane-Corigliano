package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.GetMarketResEvent;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * Market tray controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */
public class MarketTrayController extends Controller{
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

    private static MarketTrayController instance;

    /**
     * Method implementing the singleton for the controller
     * @return the unique instance
     */
    public static MarketTrayController getInstance() {
        if(instance == null)
            instance = new MarketTrayController();
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
     * Methods to get the corresponding row or column (of resources) of the markey tray
     * @param mouseEvent click on row 1/2/3 or column 1/2/3/4
     */
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

    /**
     * Conversion from a MarketTray object to the corresponding view my converting the marbles
     * @param mymarket the market tray object
     */
    public void conversion(MarketTray mymarket) {

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                ImageView im  = (ImageView) getMarkettray().lookup("#row".concat(Integer.toString(i+1))
                        .concat("col").concat(Integer.toString(j+1)));
                Image img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getRow(i).get(j))));
                im.setImage(img);
            }
        }

        ImageView im  = (ImageView) getMarkettray().lookup("#freeball");
        Image img = new Image(getClass().getResourceAsStream(Controller.marbleToUrl(mymarket.getFreeball())));
        im.setImage(img);
    }

    /**
     * Method to update the warehouse depots representation in the market tray scene
     * @param warehouseDepots the warehousedepots object
     */
    public synchronized void updateWarehouseDepots(WarehouseDepots warehouseDepots) {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j<=i; j++) {
                ImageView im = (ImageView) getMarkettray().lookup("#res".concat(Integer.toString(j+1))
                        .concat("slot").concat(Integer.toString(i+1)));
                try {
                    Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(i+1).get(j))));
                    im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                    im.setImage(null);
                }
            }
        }
    }

}
