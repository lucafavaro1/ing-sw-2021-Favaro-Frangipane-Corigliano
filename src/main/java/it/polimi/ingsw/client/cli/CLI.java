package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.setup.SetupPhase;

import java.io.*;
import java.net.*;
import java.util.*;

public class CLI {

    public static void main(String[] args){
        CLI cli = new CLI();
        cli.run();
    }

    public void run() {
        SetupPhase setup = new SetupPhase();
        try {
            setup.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
