package it.polimi.ingsw.server;

import it.polimi.ingsw.server.controller.GameHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Huge class representing the server on which the game runs
 */
public class GameServer {
    int port;
    InetAddress ipAddress;
    private static ArrayList<GameClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private static final Map<Integer, GameHandler> gameHandlers = new HashMap<>();

    public GameServer(InetAddress address, int port) {
        this.ipAddress = address;
        this.port = port;
    }

    /**
     * Method used to start the game server
     */
    public void startServer() {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port,10,ipAddress);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server Ready: ");
        System.out.println("SERVER IP: "+ ipAddress); //InetAddress.getLocalHost().toString());
        System.out.println("PORT: "+ port+"\n\n");
        System.out.println("Awaiting for client connections ... ");

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                System.out.println("The client " + clientSocket.getInetAddress() + " was accepted");

                // starting the client handler transceiver
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

    public String getHostname() {
        InetAddress ip;
        String hostname = null;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            //System.out.println("Your current IP address : " + ip);
            //System.out.println("Your current Hostname : " + hostname);


        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        return hostname;
    }

    /**
     * Main of the GameServer
     *
     * @param args as standard
     */
    public static void main(String[] args) {
        // definizione delle socket + buffer per lettura scrittura sia su socket che StdIn
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to Master of Renaissance Server");

        // scelta della porta su cui far partire il server
        int myport = 0;
        InetAddress myaddress = null;

        System.out.println("Choose IP address:");
        try {
            myaddress = InetAddress.getByName(stdIn.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Choose Port (>1024 and default 48000):");
        try {
            myport = Integer.parseInt(stdIn.readLine());
            if(myport == 0)
                myport = 48000;
        } catch (IOException e) {
            e.printStackTrace();
        }


        GameServer myserver = new GameServer(myaddress, myport);
        myserver.startServer();
    }

    public static Map<Integer, GameHandler> getGameHandlers() {
        return gameHandlers;
    }

    public static void addGameHandler(GameHandler gameHandler) {
        gameHandlers.put(gameHandlers.size() + 1, gameHandler);
    }

    public static ArrayList<GameClientHandler> getClients() {
        return clients;
    }
}


