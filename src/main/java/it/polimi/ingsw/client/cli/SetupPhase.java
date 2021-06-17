package it.polimi.ingsw.client.cli;

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

        if (str.contains("You reconnected to Masters of Renaissance")) {
            if (!userInput.equals("You reconnected to Masters of Renaissance")) {
                UserInterface.getInstance().setMyNickname(userInput);
            }
            throw new ReconnectedException();
        }

        if (str.contains("Okay, nickname chosen:"))
            userInput = userInput.split("Okay, nickname chosen:")[0];

        if (str.contains("mode chosen!") || str.contains("Multiplayer: "))
            return str;

        return userInput;
    }

    /**
     * Method run for the setup phase, from connection to game start
     *
     * @throws IOException in case of improper inputs
     */
    public Socket run() throws IOException {
        InetAddress addr = InetAddress.getByName(null);

        // definition of client socket and in/out buffer
        Socket clientSocket = null;
        BufferedReader in, stdIn;
        PrintWriter out;
        int port = 0;
        String ip = "";

        stdIn = new BufferedReader(new InputStreamReader(System.in)); // input stream (from socket) creation

        System.out.println("<< Client Login >>");

        // ip and port read
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

        // attemp to connect of a specified ip and port number

        System.out.println("Connecting to " + ip + " through port " + port);
        try {
            clientSocket = new Socket(ip, port);
            System.out.println("Connected!");
        } catch (IOException e) {
            System.err.println("Connection failed...");
            System.exit(1);
        }

        String userInput;
        String str;

        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        // output stream creation (on socket)
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        out = new PrintWriter(bw, true);

        System.out.println("Communication with server started :");


        // send message - recive answer between client and server
        try {
            try {
                str = in.readLine();  //get AskGameType
                System.out.println(str);

                userInput = stdIn.readLine();       // choose gametype
                out.println(userInput);       // send gametype to the server

                str = in.readLine();            // server answer for the gametype

                str = chooseSomething(str, invalid, in, stdIn, out, addr);         // checking gametype
                System.out.println(str);

                String nick;
                if (single.equals(str)) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    // SINGLE PLAYER MODE
                    System.out.println(in.readLine());          // choose nickname message
                    str = stdIn.readLine();                     // write nickname on keyboard
                    out.println(str);                           // send nickname to the server
                    nick = str;

                    str = in.readLine();                        // receive answer from the server

                    UserInterface.getInstance().setMyNickname(nick);
                    str = chooseSomething(str, invNick, in, stdIn, out, addr);     // checking nickname
                    if (!str.isBlank() && !str.isEmpty())
                        UserInterface.getInstance().setMyNickname(str);

                    str = in.readLine();
                    System.out.println(str);
                } else if (multi.equals(str)) {
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    // MULTIPLAYER MODE
                    System.out.println(in.readLine());          // choose lobby message (create or join)
                    str = stdIn.readLine();                     // choose lobby mode
                    out.println(str);                           // sending lobby mode
                    str = in.readLine();                        // receive answer from the server

                    str = chooseSomething(str, invalid, in, stdIn, out, addr);             // checking lobby mode
                    System.out.println(str);
                    if (multiNew.equals(str)) {                                   // if create lobby choosen
                        System.out.println(in.readLine());          // choose nickname message
                        str = stdIn.readLine();                     // type nickname on the keyboard
                        out.println(str);                           // send nickname to the server
                        nick = str;

                        str = in.readLine();                        // receive message from the server

                        UserInterface.getInstance().setMyNickname(nick);
                        str = chooseSomething(str, invNick, in, stdIn, out, addr);     // choosing nickname
                        if (!str.isBlank() && !str.isEmpty())
                            UserInterface.getInstance().setMyNickname(str);

                        System.out.println(in.readLine());              // message choose number of players
                        str = stdIn.readLine();                           // choose number from keyboard
                        out.println(str);                               // send number
                        str = in.readLine();
                        chooseSomething(str, invalid, in, stdIn, out, addr); // check number
                        System.out.println(in.readLine());
                    } else if (multiJoin.equals(str)) {                          // if join lobby chosen
                        System.out.println(in.readLine());                      // message insert ID
                        str = stdIn.readLine();                                  // insert ID from keyboard
                        out.println(str);                                       // send ID to the server
                        str = in.readLine();

                        chooseSomething(str, invalid, in, stdIn, out, addr);         // check ID

                        System.out.println(in.readLine());          // choose nickname message
                        str = stdIn.readLine();                     // write nickname on the keyboard
                        out.println(str);                           // send nickname to the server
                        nick = str;
                        str = in.readLine();                        // receive message from the server

                        UserInterface.getInstance().setMyNickname(nick);
                        str = chooseSomething(str, invNick, in, stdIn, out, addr);     // check nickname
                        if (!str.isBlank() && !str.isEmpty())
                            UserInterface.getInstance().setMyNickname(str);

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
