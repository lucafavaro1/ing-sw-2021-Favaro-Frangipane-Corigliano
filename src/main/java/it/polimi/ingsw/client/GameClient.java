package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Method main selects between CLI and GUI
 */
public class GameClient {

    public static void main(String[] args){
        System.out.println("Welcome to Master of Renaissance!\nHow do you want to play?");
        System.out.println("1. CLI INTERFACE \n2. GUI INTERFACE");
        System.out.println(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application closing...");
            System.exit(-1);
        }
        if(input == 1) {
            System.out.println("You selected the CLI interface!\nStarting...");
            CLI.main(args);
        }
        else if(input == 2) {
            System.out.println("You selected the GUI interface, have fun!\nStarting...");
            //GUI.main(args);
        }
        else {
           System.err.println("Invalid choose, please run the executable again");
        }
    }

}