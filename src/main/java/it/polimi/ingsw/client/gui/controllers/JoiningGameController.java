package it.polimi.ingsw.client.gui.controllers;

import javafx.scene.control.Label;

/**
 * GUI Controller: scene in which event broker + user interface + client controller are setted, wait the GAME_STARTED event
 */
public class JoiningGameController extends Controller{
    public JoiningGameController() {
        procedure();

        Label myNickname = (Label) getPersonalpunchboard().lookup("#yourNickname");
        myNickname.setText("  "+getMynickname()+"  ");
    }
}
