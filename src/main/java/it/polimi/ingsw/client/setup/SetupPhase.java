package it.polimi.ingsw.client.setup;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.ReconnectedException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.InputMismatchException;

/**
 * First phase of the game corresponding to Sequence 1 (connection, choose type, choose nick, fill game, start)
 */
public class SetupPhase {
    private static String invalid = "Invalid option, choose again :";
    private static String badReq = "Too many bad requests, application is closing";
    private static String single = "SinglePlayer mode chosen!";
    private static String multi = "MultiPlayer mode chosen!";
    private static String invNick = "Invalid nickname";
    private static String multiNew = "Multiplayer: create a new match";
    private static String multiJoin = "Multiplayer: joining an existing match";

    /**
     * Common method used to choose something
     *
     * @param str   string passed by the client
     * @param inv   invalid message
     * @param in    BufferReader input of the client on socket
     * @param stdIn BufferReader stdin of the client
     * @param out   PrintWriter output of the clint on socket
     * @param addr  address of the client
     * @return string for str update
     */
    public static String chooseSomething(String str, String inv, BufferedReader in, BufferedReader stdIn, PrintWriter out, InetAddress addr) {
        String userInput = str;
        try {
            while (inv.equals(str)) {
                str = in.readLine();
                System.out.println(inv);
                System.out.println(str);
                userInput = stdIn.readLine();
                out.println(userInput);
                str = in.readLine();
            }

            if (badReq.equals(str)) {
                System.out.println(badReq);
                System.exit(-1);
            }

        } catch (IOException e) {
            System.err.println("Cannot get I/O connection to: " + addr);
            System.exit(1);
        }

        if (str.contains("You reconnected to Masters of Renaissance"))
            throw new ReconnectedException();

        if (str.contains("Okay, nickname chosen:"))
            userInput = userInput.split("Okay, nickname chosen:")[0];

        return userInput;
    }

    /**
     * Method run for the setup phase, from connection to game start
     *
     * @throws IOException in case of improper inputs
     */
    public Socket run() throws IOException {
        InetAddress addr = InetAddress.getByName(null);

        // definizione della client socket e buffer per in/out socket e stdin
        Socket clientSocket = null;
        BufferedReader in, stdIn;
        PrintWriter out;
        int port = 0;
        String ip = "";

        stdIn = new BufferedReader(new InputStreamReader(System.in)); // creazione stream di input da socket

        System.out.println("<< Client Login >>");

        // lettura dell'ip e della porta su cui connettersi
        System.out.println("Insert the server IP:");

        try {
            ip = stdIn.readLine();
        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, app shutting down...");
            System.exit(-1);
        }
        System.out.println("Insert the port number ( > 1024 ):");

        try {
            port = Integer.parseInt(stdIn.readLine());

        } catch (InputMismatchException e) {
            System.err.println("Numeric format requested, app is shutting down...");
            System.exit(-1);
        }

        // tentativo di connessione al server su quella porta e quell'ip

        System.out.println("Connecting to " + ip + " through port " + port);
        try {
            clientSocket = new Socket(ip, port);
            System.out.println("Connected!");
        } catch (IOException e) {
            System.err.println("Connection failed...");
            System.exit(1);
        }

        //stream di output da tastiera è StdIn definito sopra
        String userInput;
        String str;

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // creazione stream di output su socket
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out = new PrintWriter(bw, true);

        System.out.println("Communication with server started :");


        // ciclo di send message e receive answer dal client al server
        try {
            try {
                str = in.readLine();  //Ricezione di AskGameType
                System.out.println(str);

                userInput = stdIn.readLine();       //Scelta del gametype
                out.println(userInput);       //Invio del gametype al server

                str = in.readLine();            // risposta al primo gametype dal server

                str = chooseSomething(str, invalid, in, stdIn, out, addr);         // scelta del gametype
                System.out.println(str);

                String nick;
                if (single.equals(str)) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    // SINGLE PLAYER MODE
                    System.out.println(in.readLine());          // messaggio scegli il nickname
                    str = stdIn.readLine();                     // scrivi da tastiera il nickname
                    out.println(str);                           // manda nickname al server
                    nick = str;

                    str = in.readLine();                        // ricevi messaggio dal server

                    str = chooseSomething(str, invNick, in, stdIn, out, addr);     // scelta nickname
                    if (!str.isBlank() && !str.isEmpty())
                        nick = str;

                    UserInterface.getInstance().setMyNickname(nick);
                    str = in.readLine();
                    System.out.println(str);
                } else if (multi.equals(str)) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    // MULTIPLAYER MODE
                    System.out.println(in.readLine());          // messaggio scegli lobby (accedi o crea)
                    str = stdIn.readLine();                     //Scelta della lobby mode
                    out.println(str);                           //Invio della lobby mode
                    str = in.readLine();                        // ricevi messaggio dal server

                    str = chooseSomething(str, invalid, in, stdIn, out, addr);             // scelta lobby mode
                    System.out.println(str);
                    if (multiNew.equals(str)) {                                   // se è stato scelto crea nuova lobby
                        System.out.println(in.readLine());          // messaggio scegli il nickname
                        str = stdIn.readLine();                     // scrivi da tastiera il nickname
                        out.println(str);                           // manda nickname al server
                        nick = str;

                        str = in.readLine();                        // ricevi messaggio dal server

                        str = chooseSomething(str, invNick, in, stdIn, out, addr);     // scelta nickname
                        if (!str.isBlank() && !str.isEmpty())
                            nick = str;

                        UserInterface.getInstance().setMyNickname(nick);

                        System.out.println(in.readLine());              //messaggio scegli numero dal server
                        str = stdIn.readLine();                           //scegli numero da tastiera
                        out.println(str);                               //invio numero
                        str = in.readLine();
                        System.out.println(str);//ricevi messaggio dal server
                        chooseSomething(str, invalid, in, stdIn, out, addr); //controllo validità
                        System.out.println(in.readLine());
                    } else if (multiJoin.equals(str)) {                                  // se è stato scelto join una lobby
                        System.out.println(in.readLine());                      //messaggio inserisci matchID
                        str = stdIn.readLine();                                  //inserisci ID da tastiera
                        out.println(str);                                       //invia ID al server
                        str = in.readLine();

                        chooseSomething(str, invalid, in, stdIn, out, addr);         //controllo validità

                        System.out.println(in.readLine());          // messaggio scegli il nickname
                        str = stdIn.readLine();                     // scrivi da tastiera il nickname
                        out.println(str);                           // manda nickname al server
                        nick = str;
                        str = in.readLine();                        // ricevi messaggio dal server

                        str = chooseSomething(str, invNick, in, stdIn, out, addr);     // scelta nickname
                        if (!str.isBlank() && !str.isEmpty())
                            nick = str;

                        UserInterface.getInstance().setMyNickname(nick);
                        System.out.println(in.readLine()); // "starting match..."
                    }
                }

            } catch (IOException e) {
                System.err.println("Cannot get I/O connection to " + addr);
                System.exit(1);
            }
        } catch (ReconnectedException ignored) {
            System.out.println("Welcome back to Masters of Renaissance!");
        }

        return clientSocket;
    }
}
