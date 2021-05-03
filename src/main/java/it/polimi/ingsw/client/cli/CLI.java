package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.setup.SetupPhase;
import it.polimi.ingsw.common.Events.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class CLI {
    /**
     * Command Line Interface class
     * @param args standard
     */
    public static void main(String[] args){
        CLI cli = new CLI();
        cli.run();
    }

    /**
     * Run method for the CLI, deciding the print phases
     */
    public void run() {
        SetupPhase setup = new SetupPhase();

        try {
            setup.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
