package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class waitingForPlayersController extends Controller {
    public waitingForPlayersController () throws IOException {
        EventBroker eventBroker = new EventBroker();
        GUIUserInterface guiUserInterface = new GUIUserInterface(eventBroker);

        ClientController clientController = new ClientController(
                guiUserInterface,
                eventBroker,
                getClientSocket()
        );

        setCmb(clientController.getClientMessageBroker());
        clientController.start();

    }
}
