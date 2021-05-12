package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

    public void toPersonalBoard(MouseEvent mouseEvent) {
    }

    public void rowChosen(MouseEvent mouseEvent) {
    }

    public void columnChosen(MouseEvent mouseEvent) {
    }
}
