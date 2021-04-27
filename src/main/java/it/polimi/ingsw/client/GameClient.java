package it.polimi.ingsw.client;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
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

        try {

            str = in.readLine();  //Ricezione di AskGameType

            System.out.println(str);


            userInput = stdIn.readLine();       //Scelta del gametype
            out.println(userInput);       //Invio del gametype al server
            str = in.readLine();            // risposta al primo gametype dal server
            //System.out.println("Server message: " + str);

            String invalid = "This option is not valid, choose again";
            String badReq = "Too many bad requests, application is closing";
            String single = "Singleplayer Mode chosen!";
            String multi = "Multiplayer Mode chosen!";
            String invNick = "Invalid nickname, choose again:";

            while(invalid.equals(str)) {
                str = in.readLine();
                System.out.println(invalid);
                System.out.println(str);
                userInput = stdIn.readLine(); //Scelta del gametype
                out.println(userInput);       //Invio del gametype
                str = in.readLine();
            }

            if(badReq.equals(str)){
                System.out.println(badReq);
                System.exit(-1);
            }

            ////////////////////////////////////////////////////////////////////////////////////////////////
            // SINGLE PLAYER MODE
            if(single.equals(str)){
                System.out.println(single);
                System.out.println(in.readLine());          // messaggio scegli il nickname
                str = stdIn.readLine();                     // scrivi da tastiera il nickname
                out.println(str);                           // manda nickname al server
                str = in.readLine();
                while(invNick.equals(str)) {
                    System.out.println(str);
                    str = stdIn.readLine();                     // scrivi da tastiera il nickname
                    out.println(str);                           // riprova a mandare
                    str = in.readLine();                        // messaggio di risposta
                }
                if(str.equals(badReq)) {
                    System.out.println(badReq);
                    System.exit(-1);
                }
                System.out.println(str);                        // stampa nickname valido

            }
            ////////////////////////////////////////////////////////////////////////////////////////////////
            // MULTIPLAYER MODE
            if(multi.equals(str)){
                System.out.println(multi);
                System.out.println(in.readLine());          // messaggio scegli lobby (accedi o crea)
                str = stdIn.readLine();                     //Scelta della lobby mode
                out.println(str);                           //Invio della lobby mode

                str = in.readLine();                        // ricevi messaggio dal server
                while(invalid.equals(str)) {
                    str = in.readLine();
                    System.out.println(invalid);
                    System.out.println(str);
                    userInput = stdIn.readLine();           //Scelta della lobby mode
                    out.println(userInput);                 //Invio della lobby mode
                    str = in.readLine();
                }

                if(badReq.equals(str)){
                    System.out.println(badReq);
                    System.exit(-1);
                }
                System.out.println(str);

            }

        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + addr);
            System.exit(1);
        }

    }
}
