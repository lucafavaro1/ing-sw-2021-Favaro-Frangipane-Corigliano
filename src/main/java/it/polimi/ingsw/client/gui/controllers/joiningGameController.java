package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * GUI Controller: changing scene to the first of the game after the start (personal board)
 */
public class joiningGameController extends Controller{
    public joiningGameController() {
        EventBroker eventBroker = new EventBroker();

        UserInterface.newInstance(false, eventBroker);
        GUIUserInterface guiUserInterface = (GUIUserInterface) UserInterface.getInstance();

        ClientController clientController = new ClientController(
                eventBroker,
                getClientSocket()
        );

        setCmb(clientController.getClientMessageBroker());
        clientController.start();
    }
}
