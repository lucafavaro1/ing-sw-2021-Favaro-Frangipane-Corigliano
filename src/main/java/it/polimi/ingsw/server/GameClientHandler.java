package it.polimi.ingsw.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Message;
import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages the connections between client and server, letting multiple connections
 * thanks to Runnable interface
 * TODO: Implementare controllo del nickname con quelli già presenti in lobby
 */
public class GameClientHandler implements Runnable{
    private final Map<Integer, String> messagesReceived = new HashMap<>();
    private Socket client;
    private BufferedReader in, stdIn;
    private PrintWriter out;
    private HumanPlayer player;
    private NetTuple newPlayer;
    private GameHandler thisGame;
    private int maxKey = 1;

    private String invOption = "This option is not valid, choose again";
    private String lobbyIsFull = "Lobby is full, cannot join";
    private String gameTypeStr = "Choose game type : 1)Single Player" +
            "     2)MultiPlayer";
    private String matchTypeStr = "Choose an option : 1)Create a new match       2)Join an existing match";
    private String numOfPlayersStr = "Choose the number of players:";
    private String matchIDStr = "Insert a valid Match ID (1 to 9):";

    /**
     * Constructor of the GameClientHandler
     *
     * @param clientSocket the client socket
     * @param newPlayer    datas of connecting player
     * @throws IOException in case of improper inputs
     */
    public GameClientHandler(Socket clientSocket, NetTuple newPlayer) throws IOException {
        this.client = clientSocket;
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
        this.newPlayer = newPlayer;
    }

    /**
     * Standard method to choose a nickname in different modes
     *
     * @param in    BufferReader of server input on socket
     * @param out   PrintWriter of server output on socket
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
     *
     * @param error error message
     * @param again try again message
     * @param tries number of max retries
     * @param count number of attempts done
     * @param in    BufferReader of server input on socket
     * @param out   PrintWriter of server output on socket
     * @return last string (used to update str in the code)
     * @throws IOException in case of improper inputs
     */
    public String invalidOption(String error, String again, int tries, int count, BufferedReader in, PrintWriter out) throws IOException {
        out.println(error);
        out.println(again);
        String str = in.readLine();
        if (count > tries) {
            out.println("Too many bad requests, application is closing");
            System.exit(-1);
        }
        return str;
    }

    /**
     * Method used to ask if the lobby is actually full
     *
     * @return if is full -> 1, otherwise -> 0
     */
    public boolean isFull(int lobby) {
        return GameServer.getGameHandlers().get(lobby).getClientHandlers().size() == GameServer.getGameHandlers().get(lobby).getMaxPlayers();
    }

    /**
     * Method used to ask if it is possible for a client to join this lobby
     *
     * @return if its possible -> 1, otherwise -> 0
     */
    public boolean isJoinable(int lobby) {
        System.out.println();
        return GameServer.getGameHandlers().get(lobby).getClientHandlers().size() < GameServer.getGameHandlers().get(lobby).getMaxPlayers();
    }

    /**
     * method that deals with the setup phase of the client
     */
    private void setupPhase() {
        System.out.println("Inizio comunicazione con il client: ");
        String str;
        int count;

        //ciclo di ricezione dal client e invio di risposta
        try {
            // gametype
            out.println("Choose game type : 1)Single Player     2)MultiPlayer");
            str = in.readLine();//Ricezione gametype
            count = 1;
            while (Integer.parseInt(str) != 1 && Integer.parseInt(str) != 2) {             //Controllo gametype ( e eventuale nuova ricezione )
                str = invalidOption(invOption, gameTypeStr, 3, count, in, out);
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
                thisGame.start();
            } else if (Integer.parseInt(str) == 2) {
                out.println("Multiplayer Mode chosen!");
                out.println("Choose an option : 1)Create a new match       2)Join an existing match");
                str = in.readLine();
                count = 1;
                while (Integer.parseInt(str) != 1 && Integer.parseInt(str) != 2) {
                    str = invalidOption(invOption, matchTypeStr, 3, count, in, out);
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
                        str = invalidOption(invOption, numOfPlayersStr, 3, count, in, out);
                        count++;
                    }
                    out.println("Multiplayer: creating match...");
                    thisGame = new GameHandler(Integer.parseInt(str));
                    thisGame.addGameClientHandler(this);
                    GameServer.addGameHandler(thisGame);
                    player = (HumanPlayer) thisGame.getGame().getPlayers().get(0);
                    player.setGameClientHandler(this);
                    out.println("Waiting for other players to join... ");
                } else if (Integer.parseInt(str) == 2) {
                    //////////////////////////////////////////
                    // MULTIPLAYER JOIN MATCH
                    out.println("Multiplayer: joining an existing match");
                    out.println("Insert a valid Match ID (1 to 9): ");
                    int lobby = Integer.parseInt(in.readLine());                                                                                  //ricezione matchID
                    count = 0;
                    while (lobby < 1 || lobby > 9) {                                          //controllo matchID
                        lobby = Integer.parseInt(invalidOption(invOption, matchIDStr, 3, count, in, out));
                        count++;
                    }
                    out.println("Lobby " + lobby);
                    while (!isJoinable(lobby)) {                                                          //controllo lobby
                        lobby = Integer.parseInt(invalidOption(lobbyIsFull, matchIDStr, 3, count, in, out));
                        count++;
                    }
                    out.println("Successfully joined lobby " + lobby);
                    thisGame = GameServer.getGameHandlers().get(lobby);
                    chooseNick(in, out, true);
                    player = (HumanPlayer) thisGame.getGame()
                            .getPlayers().get(GameServer.getGameHandlers().get(lobby).getClientHandlers().size());
                    player.setGameClientHandler(this);

                    //chooseNick(in, out, true);
                    //TODO: check nickname con lista di player attualmente nella partita (da fare con controller)

                    if (!isFull(lobby)) {
                        out.println("Starting match...");
                        thisGame.prepareGame();
                        thisGame.start();
                    } else out.println("Waiting for other players to join...");
                }
            }
        } catch (IOException e) {
            System.err.println("Couldn’t get I/O for the connection to: " + client.getInetAddress());
            System.exit(1);
        }
    }

    /**
     * Used in order to send a message and receive a response to it from the client (used for MakePlayerChoose)
     *
     * @param payload the object to send to the client
     * @return the response of the client
     */
    public synchronized String sendMessageGetResponse(Object payload) {
        // creating the message to send
        Message message = new Message(nextKey(), payload);

        // sending message to the other end
        messagesReceived.put(message.getIdMessage(), null);
        out.println(message.toJson());

        // waiting for a response
        while (messagesReceived.get(message.getIdMessage()) == null) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }

        // when the response is received takes the response and removes the message from the queue
        String response = messagesReceived.get(message.getIdMessage());
        messagesReceived.remove(message.getIdMessage());
        return response;
    }

    /**
     * Method to send a string to the client, without waiting for a response. Usually used to update the view or
     * to send the starting of the round event.
     * TODO: check if it's useful or not
     *
     * @param payload the payload to send to the client
     */
    public void sendMessage(String payload) {
        // sending message to the other end
        out.println(payload);
    }

    /**
     * Method to send a string to the client, without waiting for a response. Usually used to update the view or
     * to send the starting of the round event.
     *
     * @param event the event to send to the client
     */
    public void sendEvent(Event event) {
        // sending message to the other end
        out.println(Event.getJsonFromEvent(event));
    }

    /**
     * method that receives from the client messages and events and dispatches them in the right way
     */
    @Override
    public void run() {
        String message;

        setupPhase();

        EventBroker eventBroker = player.getGame().getEventBroker();
        // cycle that reads from the socket the messages sent by the client
        while (true) {
            try {
                System.out.println("[SERVER] waiting for client messages");
                message = in.readLine();
                System.out.println("[SERVER] " + message);

                try {
                    Message msgReceived = Message.fromJson(message, Object.class);
                    if (msgReceived != null) {
                        System.out.println("[SERVER] " + "msgReceived: " + msgReceived.getIdMessage() + " " + msgReceived.getMessage());
                        // if this is a message, then put it into the messages received
                        synchronized (this) {
                            if (insertResponse(msgReceived)) {
                                System.out.println("[SERVER] " + messagesReceived);
                                this.notifyAll();
                            } else {
                                System.out.println("[SERVER] " + "syntax error");
                            }
                        }
                    } else {
                        // if it hasn't been inserted, that's an event, so posts it to the player that sent it
                        eventBroker.post(player, Event.getEventFromJson(message), false);
                        System.out.println("[SERVER] " + Event.getEventFromJson(message));
                    }
                } catch (JsonSyntaxException ignore) {
                    System.out.println("[SERVER] " + "syntax error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * permit to insert a message in the map of messages only if it's a valid message
     *
     * @param message message to be sent
     * @return true if it's inserted in the map, false otherwise
     */
    private boolean insertResponse(Message message) {
        if (message != null && message.getIdMessage() > 0 && messagesReceived.get(message.getIdMessage()) == null &&
                messagesReceived.containsKey(message.getIdMessage()) && message.getMessage() != null) {
            messagesReceived.put(message.getIdMessage(), message.getMessage().toString());
            return true;
        } else {
            return false;
        }
    }

    /**
     * generates the next key that can be used to identificate a message
     *
     * @return the next id available
     */
    private synchronized int nextKey() {
        while (messagesReceived.containsKey(maxKey)) {
            maxKey++;
        }
        return maxKey;
    }
}
