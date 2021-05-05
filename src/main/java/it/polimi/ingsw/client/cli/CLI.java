package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientMessageBroker;
import it.polimi.ingsw.client.setup.SetupPhase;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class CLI {
    /**
     * Command Line Interface class
     *
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
            socket = setup.run();

            // starting the client
            ClientMessageBroker clientMessageBroker = new ClientMessageBroker(
                    new ClientController(),
                    new CLIUserInterface(),
                    socket
            );
            clientMessageBroker.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
