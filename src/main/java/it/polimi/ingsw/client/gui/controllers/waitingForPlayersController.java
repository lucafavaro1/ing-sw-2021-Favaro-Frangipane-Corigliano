package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;

import java.io.IOException;

public class waitingForPlayersController extends Controller {
    public waitingForPlayersController() {
        EventBroker eventBroker = new EventBroker();
        UserInterface.newInstance(false, eventBroker);
        GUIUserInterface guiUserInterface = (GUIUserInterface) UserInterface.getInstance();
        guiUserInterface.setMyNickname(getMynickname());

        ClientController clientController = new ClientController(
                eventBroker,
                getClientSocket()
        );

        setCmb(clientController.getClientMessageBroker());
        clientController.start();
    }
}
