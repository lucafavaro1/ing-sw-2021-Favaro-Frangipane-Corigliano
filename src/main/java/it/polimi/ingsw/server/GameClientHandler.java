package it.polimi.ingsw.server;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.NetTuple;
import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class that manages the connections between client and server, letting multiple connections
 * thanks to Runnable interface
 */

/**
 * TODO: Per ora si può inviare un solo messaggio alla volta, quindi la corrispondenza è semplicemente 1 a 1, implementare possibilità di inviare più messaggi?(da discutere)
 * TODO: Implementare controllo del nickname con quelli già presenti in lobby
 */

public class GameClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in, stdIn;
    private PrintWriter out;
    private ArrayList<NetTuple> clientsList;
    private HumanPlayer player;
    private NetTuple newPlayer;
    private GameHandler thisGame;
    private int numOfPlayers;

    public ArrayList<NetTuple> getClientsList() {
        return clientsList;
    }

    public void setClientsList(ArrayList<NetTuple> clientsList) {
        this.clientsList = clientsList;
    }

    private String invOption = "This option is not valid, choose again";
    private String lobbyIsFull = "Lobby is full, cannot join";
    private String gameTypeStr = "Choose game type : 1)Single Player" +
            "     2)MultiPlayer";
    private String matchTypeStr = "Choose an option : 1)Create a new match       2)Join an existing match";
    private String numOfPlayersStr = "Choose the number of players:";
    private String matchIDStr = "Insert a valid Match ID (1 to 9):";

    /**
     * Constructor of the GameClientHandler
     * @param clientSocket the client socket
     * @param clients arraylist of clients connected
     * @param newPlayer datas of connecting player
     * @throws IOException in case of improper inputs
     */
    public GameClientHandler(Socket clientSocket, ArrayList<NetTuple> clients, NetTuple newPlayer) throws IOException {
        this.client = clientSocket;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        this.clientsList = clients;
        this.newPlayer=newPlayer;
    }

    /**
     * Standard method to choose a nickname in different modes
     * @param in BufferReader of server input on socket
     * @param out PrintWriter of server output on socket
     * @param first if the player choosing is the first of the game (1) or not (0)
     */
    public void chooseNick(BufferedReader in, PrintWriter out, boolean first) {
        String str;
        int count = 0;

        try {
            out.println("Choose a valid nickname: ");
            str = in.readLine();

            while (str.isBlank()) {
                out.println("Invalid nickname");
                out.println("Choose a valid nickname: ");
                str = in.readLine(); //ricezione nickname
                count++;
                if (count > 2) {
                    out.println("Too many bad requests, application is closing");
                    System.exit(-1);
                }
            }
            out.println("Okay, chosen nickname:" + str);
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + client.getInetAddress());
            System.exit(1);
        }
    }

    /**
     * Standard block to print an invalid option message and send request again
     * @param error error message
     * @param again try again message
     * @param tries number of max retries
     * @param str a string passed by the method
     * @param count number of attempts done
     * @param in BufferReader of server input on socket
     * @param out PrintWriter of server output on socket
     * @return last string (used to update str in the code)
     * @throws IOException in case of improper inputs
     */
    public String invalidOption(String error, String again, int tries, String str, int count, BufferedReader in, PrintWriter out) throws IOException {
        out.println(error);
        out.println(again);
        str = in.readLine();
        if (count > tries) {
            out.println("Too many bad requests, application is closing");
            System.exit(-1);
        }
        return str;

    }

    /**
     * Method used to ask if the lobby is actually full
     * @return if is full -> 1, otherwise -> 0
     */
    public boolean isFull() {
        // TODO: change?
        return getClientsList().size() == 2;

    }

    /**
     * Method used to ask if it is possible for a client to join this lobby
     * @return if its possible -> 1, otherwise -> 0
     */
    public boolean isJoinable() {
        // TODO: change?
        return getClientsList().size() < 2;

    }


    public boolean isFilled(int x) {
        return true;
    }

    public void sendToClient(String message) {
        out.println(message);
    }

    //TODO: Verificare se è corretto il gestore degli eventi che riceve dal server eventi e applica sul model
    /**
     * waits something sent by the client on the net, converts the event and posts it to the eventBroker
     * TODO: only first implementation, check
     */
    public void waitForEvents() {
        while (true) {
            try {
                thisGame.getGame()
                        .getEventBroker()
                        .post(player, Events_Enum.getEventFromJson(in.readLine()), true);
            } catch (IOException ignore) {
            }
        }
    }

    @Override
    public void run() {
        numOfPlayers=0;
        System.out.println("Inizio comunicazione con il client: ");
        String serverInput = "";
        String str;
        int count = 1;

        //ciclo di ricezione dal client e invio di risposta
        try {
            out.println("Choose game type : 1)Single Player" +                                                          //AskGameType
                    "     2)MultiPlayer");

            str = in.readLine();//Ricezione gametype

            while (Integer.parseInt(str) != 2 && Integer.parseInt(str) != 1) {             //Controllo gametype ( e eventuale nuova ricezione )
                str = invalidOption(invOption, gameTypeStr, 3, str, count, in, out);
                count++;
            }

            if (Integer.parseInt(str) == 1) {
                out.println("Singleplayer Mode chosen!");

                chooseNick(in, out, true);                // scelta nickname valido
                out.println("Creating a new match ...");
                thisGame = new GameHandler(1);
                thisGame.addGameClientHandler(this);
                player = (HumanPlayer) thisGame.getGame().getPlayers().get(0);
                player.setGameClientHandler(this);
                thisGame.startGame();
            } else if (Integer.parseInt(str) == 2) {
                out.println("Multiplayer Mode chosen!");
                out.println("Choose an option : 1)Create a new match       2)Join an existing match");
                str = in.readLine();
                count = 1;

                while (Integer.parseInt(str) != 1 && Integer.parseInt(str) != 2) {
                    str = invalidOption(invOption, matchTypeStr, 3, str, count, in, out);
                    count++;
                }

                if (Integer.parseInt(str) == 1) {
                    ////////////////////////////////////////////
                    // MULTIPLAYER CREATE MATCH

                    out.println("Multiplayer: create a new match");
                    chooseNick(in, out, true);                // scelta nickname valido
                    out.println("Choose the number of players (2-4): ");

                    str = in.readLine();
                    count = 0;
                    while (Integer.parseInt(str) <= 1 || Integer.parseInt(str) > 4) {
                        str = invalidOption(invOption, numOfPlayersStr, 3, str, count, in, out);
                        count++;
                    }
                    out.println("Multiplayer: creating match...");
                    this.clientsList.add(newPlayer);
                    thisGame = new GameHandler(Integer.parseInt(str));
                    thisGame.addGameClientHandler(this);
                    player = (HumanPlayer) thisGame.getGame().getPlayers().get(0);
                    player.setGameClientHandler(this);
                    out.println("Waiting for other players to join... ");
                    while(!isFull()){

                    }
                    out.println("Starting match...");
                } else if (Integer.parseInt(str) == 2) {
                    //////////////////////////////////////////
                    // MULTIPLAYER JOIN MATCH
                    out.println("Multiplayer: joining an existing match");
                    out.println("Insert a valid Match ID (1 to 9): ");
                    str = in.readLine();                                                                                  //ricezione matchID
                    count = 0;
                    while (Integer.parseInt(str) < 1 || Integer.parseInt(str) > 9) {                                          //controllo matchID
                        str = invalidOption(invOption, matchIDStr, 3, str, count, in, out);
                        count++;
                    }
                    out.println("Lobby " + str);
                    while (!isJoinable()) {                                                          //controllo lobby
                        str = invalidOption(lobbyIsFull, matchIDStr, 3, str, count, in, out);
                        count++;
                    }
                    out.println("Successfully joined lobby " + str);
                    this.clientsList.add(newPlayer);
                    chooseNick(in, out, true);
                    //player = (HumanPlayer) thisGame.getGame().getPlayers().get(getClientsList().size() - 1);
                    //player.setGameClientHandler(this);

                    //chooseNick(in, out, true);
                    //TODO: check nickname con lista di player attualmente nella partita (da fare con controller)
                    if (isFull()) {
                        out.println("Starting match...");
                        //thisGame.startGame();
                    } else out.println("Waiting for other players to join...");
                    while(!isFull()){

                    }
                    out.println("Starting match...");

                }
            }

        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + client.getInetAddress());
            System.exit(1);
        } finally {
            System.out.println("Server closing...");
            try {
                in.close();
                out.close();
                stdIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }
}
