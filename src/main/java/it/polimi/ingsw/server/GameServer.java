package it.polimi.ingsw.server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * TODO: pensare a come far si che il server sia sempre in ascolto (anche mentre sta mandando lui messaggio)
 * TODO: aggiungere più client connessi allo stesso server
 * TODO: implementare il multithreading per server e client
 */

public class GameServer {
    public static void main(String[] args) throws Exception {
        // definizione delle socket + buffer per lettura scrittura sia su socket che StdIn
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        BufferedReader in = null, stdIn=null;
        PrintWriter out = null;
        stdIn = new BufferedReader(new InputStreamReader(System.in));

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

        // connessione con il client
        try {
            clientSocket = serverSocket.accept();
            System.out.println("The client "+ clientSocket.getInetAddress() + "was accepted");
        } catch (IOException e) {
            System.err.println("There's no server ON at this port! ");
            System.exit(1);
        }

        // creazione stream di input da clientSocket
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // creazione stream di output su clientSocket
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out = new PrintWriter(bw, true);

        //stream di output da tastiera è StdIn definito sopra
        String serverInput = "";
        String str = "";

        System.out.println("Inizio comunicazione con il client: ");

        //ciclo di ricezione dal client e invio di risposta
        try{
            while (true) {
                str = in.readLine();
                System.out.println("Client message: " + str);
                if (str.equals("END")) break;
                serverInput = stdIn.readLine();
                out.println(serverInput);
                if (serverInput.equals("END")) break;
            }
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + clientSocket.getInetAddress());
            System.exit(1);
        }


        System.out.println("Server closing...");
        stdIn.close();
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

}
