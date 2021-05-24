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
        System.out.println("Benvenuto in Maestri del Rinascimento!\nCome vuoi giocare?");
        System.out.println("1. CLI \n2. GUI");
        System.out.println(">");
        Scanner scanner = new Scanner(System.in);
        int input = 0;
        try {
            input = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("È richiesto un intero, l'app sta per chiudersi...");
            System.exit(-1);
        }

        if (input == 1) {
            System.out.println("Hai scelto la CLI, buon divertimento!\nIn avvio...");
            CLI.main(args);
        } else if (input == 2) {
            System.out.println("Hai scelto la GUI, buon divertimento!\nIn avvio...");
            GUI.main(args);
        }
        else {
           System.err.println("Scelta invalida, avvia nuovamente l'applicazione");
           System.exit(-1);
        }
    }

}