package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.model.Player.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

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
