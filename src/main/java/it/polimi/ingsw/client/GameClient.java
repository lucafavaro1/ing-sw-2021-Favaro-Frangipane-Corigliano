package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;
import java.util.*;

public class GameClient {
    public static void main(String[] args) throws IOException {
        // acquisizione dell'ip del client che esegue il programma
        InetAddress addr;
        if (args.length == 0) addr = InetAddress.getByName(null);

        else addr = InetAddress.getByName(args[0]);

        // definizione della client socket e buffer per in/out socket e stdin
        Socket clientSocket = null;
        BufferedReader in = null, stdIn = null;
        PrintWriter out = null;
        int port = 0;
        stdIn = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("<< Client Login >>");

        System.out.println("Insert server port you want to connect (mandatory > 1024):");

        // lettura della porta su cui connettersi

        try {
            port = Integer.parseInt(stdIn.readLine());
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, application is closing");
            System.exit(-1);
        }

        // tentativo di connessione al server su quella porta
        System.out.println("Connecting to port " + port);
        try {
            clientSocket = new Socket(addr, port);
            System.out.println("Connected!");
        } catch (IOException e) {
            System.err.println("Accept failed");
            System.exit(1);
        }

        // creazione stream di input da socket
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // creazione stream di output su socket
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out = new PrintWriter(bw, true);

        //stream di output da tastiera è StdIn definito sopra
        String userInput = "";
        String str = "";

        System.out.println("Inizio comunicazione con il server: ");


        // ciclo di send message e receive answer dal client al server
        while (true) {
            try {
                userInput = stdIn.readLine();
                out.println(userInput);
                if (userInput.equals("END")) break;
                str = in.readLine();
                System.out.println("Server message: " + str);
                if (str.equals("END")) break;

            } catch (IOException e) {
                System.err.println("Couldn’t get I/O for the connection to: " + addr);
                System.exit(1);
            }
        }
    }
}
