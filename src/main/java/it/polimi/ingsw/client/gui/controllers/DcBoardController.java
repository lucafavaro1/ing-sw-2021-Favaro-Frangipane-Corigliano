package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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


    public void toPersonalBoard(MouseEvent mouseEvent) {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene x = getPersonalpunchboard();
        window.setScene(x);
        window.show();
    }
}
