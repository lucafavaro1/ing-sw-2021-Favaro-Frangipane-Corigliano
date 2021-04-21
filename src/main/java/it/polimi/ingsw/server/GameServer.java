package it.polimi.ingsw.server;

import it.polimi.ingsw.client.GameClientHandler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * TODO: pensare a come far si che il server sia sempre in ascolto (anche mentre sta mandando lui messaggio)
 * TODO: aggiungere più client connessi allo stesso server
 * TODO: implementare il multithreading per server e client
 */

public class GameServer {

    private static ArrayList<GameClientHandler> clients=new ArrayList<>();
    private static ExecutorService pool=Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws Exception {
        // definizione delle socket + buffer per lettura scrittura sia su socket che StdIn
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null, stdIn=null;
        PrintWriter out = null;
        stdIn=stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("<< Server Login >>");
        System.out.println("Insert server port (mandatory > 1024): ");

        // scelta della porta su cui far partire il server
        int port = 0;
        try {
            port = Integer.parseInt(stdIn.readLine());
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application is closing");
            System.exit(-1);

        }
        if (port < 0 || (port > 0 && port < 1024)) {
            System.err.println("Error: ports accepted started from 1024! Please insert a new value.");
            main(null);
        }

        // accensione del server
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Accepting...");









        // connessione con i client
        try {
            while (true) {

                clientSocket = serverSocket.accept();
                // creazione stream di input da clientSocket
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // creazione stream di output su clientSocket
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                out = new PrintWriter(bw, true);

                System.out.println("The client " + clientSocket.getInetAddress() + "was accepted");
                GameClientHandler clientThread = new GameClientHandler(clientSocket);
                clients.add(clientThread);
                pool.execute(clientThread);
            }
        }catch (IOException e) {
                System.err.println("There's no server ON at this port! ");
                System.exit(1);
            }


        }


}


