package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * GUI Controller: waiting for player scene in which event broker + user interface + client controller are setted
 * Wait the GAME_STARTED event to change scene
 */

public class WaitingForPlayersController extends Controller {
    public WaitingForPlayersController() {
        procedure();

        Label myNickname = (Label) getPersonalpunchboard().lookup("#yourNickname");
        myNickname.setText("  "+getMynickname()+"  ");
    }
}
