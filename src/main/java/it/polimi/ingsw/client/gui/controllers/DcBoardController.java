package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.common.Events.BuyDevCardEvent;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * DcBoard controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */
public class DcBoardController extends Controller{
    @FXML // first card of each deck in DcBoard
    public ImageView lev3green;
    public ImageView lev3blue;
    public ImageView lev3yellow;
    public ImageView lev3purple;
    public ImageView lev2green;
    public ImageView lev2blue;
    public ImageView lev2yellow;
    public ImageView lev2purple;
    public ImageView lev1green;
    public ImageView lev1blue;
    public ImageView lev1yellow;
    public ImageView lev1purple;
    public Label numCoin;
    public Label numStone;
    public Label numServant;
    public Label numShield;

    private static DcBoardController instance;

    /**
     * Method implementing the singleton for the controller
     * @return the unique instance
     */
    public static DcBoardController getInstance() {
        if(instance == null)
            instance = new DcBoardController();
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
     * Buy level 1/2/3 yellow/green/blue/purple development card
     * @param mouseEvent click on the corresponding card you want to buy
     */
    // BUY LEVEL 3
    public void threegreen(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.GREEN,3)));
    }

    public void threeyellow(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.YELLOW,3)));
    }

    public void threeblue(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.BLUE,3)));
    }

    public void threepurple(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.PURPLE,3)));
    }

    // BUY LEVEL 2
    public void twogreen(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.GREEN,2)));
    }

    public void twoyellow(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.YELLOW,2)));
    }

    public void twoblue(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.BLUE,2)));
    }

    public void twopurple(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.PURPLE,2)));
    }

    // BUY LEVEL 1
    public void onegreen(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.GREEN,1)));
    }

    public void oneyellow(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.YELLOW,1)));
    }

    public void oneblue(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.BLUE,1)));
    }

    public void onepurple(MouseEvent mouseEvent) {
        getCmb().sendEvent(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.PURPLE,1)));
    }


    /**
     * Conversion from a DcBoard object to the corresponding grid of images (development cards)
     * @param totboard the board object
     */
    public void conversion(DcBoard totboard) {
        // LEV 3
        ImageView im  = (ImageView) getDcboard().lookup("#lev3green");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.GREEN, 3)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev3purple");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.PURPLE, 3)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev3yellow");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.YELLOW, 3)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev3blue");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 3)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        // LEV 2
        im  = (ImageView) getDcboard().lookup("#lev2green");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.GREEN, 2)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev2purple");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.PURPLE, 2)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev2yellow");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.YELLOW, 2)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev2blue");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 2)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        // LEV 1
        im  = (ImageView) getDcboard().lookup("#lev1green");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.GREEN, 1)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev1purple");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.PURPLE, 1)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev1yellow");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.YELLOW, 1)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }

        im  = (ImageView) getDcboard().lookup("#lev1blue");
        try {
            Image img = new Image(getClass().getResourceAsStream(Controller.devCardToUrl(totboard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 1)))));
            im.setImage(img);
        } catch (NoCardsInDeckException e) {
            im.setImage(null);
        }
    }


}
