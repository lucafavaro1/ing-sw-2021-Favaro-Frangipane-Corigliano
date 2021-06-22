package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.controller.CliClientController;
import it.polimi.ingsw.client.controller.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.EventBroker;

import java.io.IOException;
import java.net.Socket;

/**
 * Command Line Interface Class
 */
public class CLI {

    /**
     * Command Line Interface main
     * @param args standard
     */
    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * Run method for the CLI, deciding the print phases
     */
    public void run() {
        Socket socket;

        SetupPhase setup = new SetupPhase();

        try {
            // starting the client
            EventBroker eventBroker = new EventBroker();
            UserInterface.newInstance(true, eventBroker);

            // beginning with the setup phase
            socket = setup.run();

            // starting the clientController and the MessageBroker
            ClientController clientController = new CliClientController(
                    eventBroker,
                    socket
            );
            clientController.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
