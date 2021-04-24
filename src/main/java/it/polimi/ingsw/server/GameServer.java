package it.polimi.ingsw.server;

import it.polimi.ingsw.client.GameClientHandler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * TODO: pensare a come far si che il server sia sempre in ascolto (anche mentre sta mandando lui messaggio)
 */

public class GameServer {
    int port;
    private static ArrayList<GameClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private ArrayList<InetAddress> playerList = new ArrayList<>();                  // TODO: aggiungere anche hostname oltre che IP

    public GameServer(int port) {
        this.port = port;
    }

    public void startServer() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server Ready: ");
        System.out.println("Awaiting for client connections: ");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("The client " + clientSocket.getInetAddress() + "was accepted");
                playerList.add(clientSocket.getInetAddress());
                GameClientHandler clientThread = new GameClientHandler(clientSocket);
                clients.add(clientThread);
                pool.execute(clientThread);
            } catch (IOException e) {
                System.err.println("There's no server ON at this port! ");
                break;
            }
        }
        pool.shutdown();
    }

    public static void main(String[] args) {
        // definizione delle socket + buffer per lettura scrittura sia su socket che StdIn
        BufferedReader in = null, stdIn = null;
        PrintWriter out = null;
        stdIn = stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("<< Server Login >>");
        System.out.println("Insert server port (mandatory > 1024): ");

        // scelta della porta su cui far partire il server
        int port = 0;
        try {
            port = Integer.parseInt(stdIn.readLine());
        } catch (InputMismatchException | IOException e) {
            System.err.println("Numeric format requested, application is closing");
            System.exit(-1);

        }
        if (port < 0 || (port > 0 && port < 1024)) {
            System.err.println("Error: ports accepted started from 1024! Please insert a new value.");
            main(null);
        }
        GameServer myserver = new GameServer(port);
        myserver.startServer();
    }


}


