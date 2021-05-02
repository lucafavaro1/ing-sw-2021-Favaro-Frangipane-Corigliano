package it.polimi.ingsw.server;

import it.polimi.ingsw.client.GameClientHandler;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class GameServer {
    int port;
    private static ArrayList<GameClientHandler> clients = new ArrayList<>();
    private static ExecutorService pool = Executors.newCachedThreadPool();
    private ArrayList<NetTuple> playerList = new ArrayList<>();                  //FattoTODO 26/04: aggiungere anche hostname oltre che IP
    //playerList ora ha come object una tupla di hostname, ottenuto tramite metodo getHostname specificato sotto
    public GameServer(int port) {
        this.port = port;
    }

    public void startServer() {
        ServerSocket serverSocket;

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
                NetTuple newPlayer= new NetTuple(getHostname(),clientSocket.getInetAddress());
                //playerList.add(newPlayer);
                GameClientHandler clientThread = new GameClientHandler(clientSocket, playerList, newPlayer);
                clients.add(clientThread);
                pool.execute(clientThread);
            } catch (IOException e) {
                System.err.println("There's no server ON at this port! ");
                break;
            }
        }
        pool.shutdown();
    }

    public String getHostname(){
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

    public static void main(String[] args) {
        // definizione delle socket + buffer per lettura scrittura sia su socket che StdIn
        BufferedReader in = null, stdIn;
        PrintWriter out = null;
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to Master of Renaissance Server");
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


