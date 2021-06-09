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
 * class representing the server on which the game runs
 */
public class GameServer {
    private final int port;
    private static final ArrayList<GameClientHandler> clients = new ArrayList<>();
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private static final Map<Integer, GameHandler> gameHandlers = new HashMap<>();

    public GameServer(int port) {
        this.port = port;
    }

    /**
     * Main of the GameServer
     *
     * @param args as standard
     */
    public static void main(String[] args) throws UnknownHostException {
        System.out.println("Welcome to Master of Renaissance Server");

        // TODO: choosing the port on which the server will run
        int port = 48000;

        GameServer myserver = new GameServer(port);
        myserver.startServer();
    }

    /**
     * Method used to start the game server
     */
    public void startServer() throws UnknownHostException {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server Ready: ");
        System.out.println("SERVER IP: " + InetAddress.getLocalHost().toString());
        System.out.println("PORT: " + port + "\n\n");
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

    public static void addGameHandler(GameHandler gameHandler) {
        gameHandlers.put(gameHandlers.size() + 1, gameHandler);
    }

    public static Map<Integer, GameHandler> getGameHandlers() {
        return gameHandlers;
    }

    public static ArrayList<GameClientHandler> getClients() {
        return clients;
    }
}


