package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Game client class
 * Method main selects between CLI and GUI
 */

public class GameClient {

    public static void main(String[] args) {
        System.out.println("Welcome to Master of Renaissance!\nChoose the interface:");
        System.out.println("1. CLI \n2. GUI");
        System.out.println(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, app closing...");
            System.exit(-1);
        }

        if (input == 1) {
            System.out.println("You have chosen the CLI. Have fun!\nStarting...");
            CLI.main(args);
        } else if (input == 2) {
            System.out.println("You have chosen the GUI. Have fun!\nStarting...");
            GUI.main(args);
        }
        else {
           System.err.println("Inavlid option, run the app again");
           System.exit(-1);
        }
    }

}