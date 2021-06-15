package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Game client class
 * Method main makes player choose between CLI and GUI
 * Use -cli or -gui parameters when running to choose the interface (default gui)
 */

public class GameClient {

    public static void main(String[] args) {
        List<String> argsCopy = new ArrayList<>(Arrays.asList(args));

        if (argsCopy.size() == 0)
            GUI.main(args);
        else if (argsCopy.size() == 1) {
            if (argsCopy.get(0).equals("-cli"))
                CLI.main(args);
            else if (argsCopy.get(0).equals("-gui"))
                GUI.main(args);
            else {
                System.err.println("Invalid option, run the app again\n" +
                        "use the parameter: '-cli' to play in cli mode or '-gui' to play in gui mode"
                );
                System.exit(-1);
            }
        } else {
            System.err.println("Invalid option, run the app again");
            System.exit(-1);
        }
    }

}