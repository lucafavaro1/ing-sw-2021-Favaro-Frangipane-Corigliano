package it.polimi.ingsw.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.Message;
import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that manages the connections between client and server, letting multiple connections
 * thanks to Runnable interface
 * TODO: Implementare controllo del nickname con quelli già presenti in lobby
 */
public class GameClientHandler implements Runnable, EventHandler {
    private final Map<Integer, String> messagesReceived = new HashMap<>();
    private final Socket client;
    private final BufferedReader in, stdIn;
    private final PrintWriter out;
    private HumanPlayer player;
    private NetTuple newPlayer;
    private GameHandler thisGame;
    private int maxKey = 1;

    private String invOption = "Opzione non valida, scegli di nuovo";
    private String lobbyIsFull = "La Lobby scelta è piena";
    private String gameTypeStr = "Scegli una modalità di gioco : 1)Single Player     2)MultiPlayer";
    private String matchTypeStr = "Scegli tra : 1)Crea una nuova lobby     2)Entra in una lobby esistente";
    private String numOfPlayersStr = "Scegli il numero di giocatori (2-4): ";
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
     * @param in  BufferReader of server input on socket
     * @param out PrintWriter of server output on socket
     */
    public String chooseNick(BufferedReader in, PrintWriter out) {
        String str = "";

        try {

            List<String> nicknamesTaken = (thisGame != null ? thisGame.getGame().getPlayers().stream().map(Player::getNickname).filter(Objects::nonNull).collect(Collectors.toList()) : List.of());
            if (thisGame != null && !nicknamesTaken.isEmpty())
                out.println("Scegli un nickname valido (già in uso: " + String.join(", ", nicknamesTaken) + "): ");
            else
                out.println("Scegli un nickname valido : ");

            str = in.readLine();

            String finalStr = str;
            while (finalStr.isBlank() || (thisGame != null && thisGame.getGame().getPlayers().stream()
                    .map(Player::getNickname)
                    .anyMatch(finalStr::equals))) {
                out.println("Nickname non valido");

                if (thisGame != null && !nicknamesTaken.isEmpty())
                    out.println("Scegli un nickname valido (già in uso: " + String.join(", ", nicknamesTaken) + "): ");
                else
                    out.println("Scegli un nickname valido : ");
                finalStr = in.readLine(); //ricezione nickname
            }
            out.println("Okay, nickname scelto:" + finalStr);
            str = finalStr;
        } catch (IOException e) {
            System.err.println("Impossibile ottenere la connessione I/O verso: " + client.getInetAddress());
            System.exit(1);
        }

        return str;
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
        return in.readLine();
    }

    /**
     * Method used to ask if the lobby is actually full
     *
     * @return if is full -> 1, otherwise -> 0
     */
    public boolean isFull(int lobby) {
        GameHandler gameToJoin = GameServer.getGameHandlers().get(lobby);
        return gameToJoin.getClientHandlers().size() == gameToJoin.getMaxPlayers();
    }

    /**
     * Method used to ask if it is possible for a client to join this lobby
     *
     * @return if its possible -> 1, otherwise -> 0
     */
    public boolean isJoinable(int lobby) {
        GameHandler gameToJoin = GameServer.getGameHandlers().get(lobby);
        return (gameToJoin.getClientHandlers().size() < gameToJoin.getMaxPlayers()) && !gameToJoin.isStarted();
    }

    /**
     * method that deals with the setup phase of the client
     */
    private void setupPhase() {
        System.out.println("Inizio comunicazione con il client: ");
        int count;

        //ciclo di ricezione dal client e invio di risposta
        try {
            int option;
            // singleplayer o multiplayer
            {
                option = -1;
                out.println("Scegli una modalità di gioco : 1)Single Player     2)MultiPlayer");
                try {
                    //Ricezione gametype
                    option = Integer.parseInt(in.readLine());
                } catch (NumberFormatException ignored) {
                }

                count = 1;
                while (option != 1 && option != 2) {             //Controllo gametype ( e eventuale nuova ricezione )
                    try {
                        option = Integer.parseInt(invalidOption(invOption, gameTypeStr, 3, count, in, out));
                    } catch (NumberFormatException ignored) {
                    }
                    count++;
                }
            }

            if (option == 1) {
                thisGame = new GameHandler(1);
                thisGame.addGameClientHandler(this);
                player = (HumanPlayer) thisGame.getGame().getPlayers().get(0);
                player.setGameClientHandler(this);

                System.out.println("Hai scelto la modalità SinglePlayer");            // DEBUG
                out.println("Hai scelto la modalità SinglePlayer");
                player.setNickname(chooseNick(in, out));// scelta nickname valido
                System.out.println(player.getNickname());                               // DEBUG
                out.println("Creazione di una nuova partita in corso...");

                (new Thread(() -> {
                    thisGame.prepareGame();
                    thisGame.start();
                })).start();
            } else {
                out.println("Hai scelto la modalità MultiPlayer!");
                System.out.println("Multiplayer Mode chosen!");                   // DEBUG

                // create o join
                {
                    option = -1;
                    out.println("Scegli tra : 1)Crea una nuova lobby     2)Entra in una lobby esistente");
                    try {
                        option = Integer.parseInt(in.readLine());
                    } catch (NumberFormatException ignored) {
                    }
                    count = 1;
                    while (option != 1 && option != 2 && GameServer.getGameHandlers().isEmpty()) {
                        try {
                            option = Integer.parseInt(invalidOption(invOption, matchTypeStr, 3, count, in, out));
                        } catch (NumberFormatException ignored) {
                        }
                        count++;
                    }

                    if ((GameServer.getGameHandlers().isEmpty() || GameServer.getGameHandlers().keySet().stream().noneMatch(this::isJoinable)) && option == 2) {
                        option = 1;
                        //TODO out.println("There are no lobby available, creating a match");
                    }
                }
                //System.out.println( "DEBUG DEBUG DEBUG "+ option);
                if (option == 1) {
                    ////////////////////////////////////////////
                    // MULTIPLAYER CREATE MATCH

                    out.println("Hai scelto di creare una nuova lobby!");
                    System.out.println("Multiplayer: create a new match");             // DEBUG
                    String nick = chooseNick(in, out);                // scelta nickname valido
                    out.println("Scegli il numero di giocatori (2-4): ");
                    // number of players of the match
                    {
                        option = -1;
                        try {
                            option = Integer.parseInt(in.readLine());
                        } catch (NumberFormatException ignored) {
                        }

                        count = 1;
                        while (option <= 1 || option > 4) {
                            try {
                                option = Integer.parseInt(invalidOption(invOption, numOfPlayersStr, 3, count, in, out));
                            } catch (NumberFormatException ignored) {
                            }
                            count++;
                        }
                    }
                    System.out.println("Number of player choosen: " + option);             // DEBUG

                    thisGame = new GameHandler(option);
                    thisGame.addGameClientHandler(this);
                    GameServer.addGameHandler(thisGame);
                    player = (HumanPlayer) thisGame.getGame().getPlayers().get(0);
                    player.setGameClientHandler(this);
                    System.out.println(player.getNickname());                                       // DEBUG
                    player.setNickname(nick);

                    out.println("Multiplayer: creo la lobby...");
                    out.println("In attesa di altri giocatori... ");
                } else {
                    //////////////////////////////////////////
                    // MULTIPLAYER JOIN MATCH
                    out.println("Hai scelto di entrare in una lobby esistente!");
                    matchIDStr = "Scegli in quale lobby entrare : " + GameServer.getGameHandlers().keySet() + ":";
                    // ricezione lobby in cui entrare
                    {
                        option = -1;
                        out.println(matchIDStr);
                        try {
                            option = Integer.parseInt(in.readLine());
                        } catch (NumberFormatException ignored) {
                        }

                        while (option == 0) {
                            out.println("Hai scelto di entrare in una lobby esistente!");
                            matchIDStr = "Scegli in quale lobby entrare : " + GameServer.getGameHandlers().keySet() + ":";
                            out.println(matchIDStr);
                            try {
                                option = Integer.parseInt(in.readLine());
                            } catch (NumberFormatException ignored) {
                            }

                        }
                        count = 1;
                        while (!GameServer.getGameHandlers().containsKey(option) || !isJoinable(option)) {
                            try {
                                option = Integer.parseInt(invalidOption(invOption, matchIDStr, 3, count, in, out));
                            } catch (NumberFormatException ignored) {
                            }
                            count++;
                        }
                    }

                    thisGame = GameServer.getGameHandlers().get(option);
                    player = (HumanPlayer) thisGame.getGame()
                            .getPlayers().get(GameServer.getGameHandlers().get(option).getClientHandlers().size());
                    player.setGameClientHandler(this);

                    out.println("Sei entrato nella lobby " + option);
                    System.out.println("Successfully joined lobby " + option);          // DEBUG
                    player.setNickname(chooseNick(in, out));
                    System.out.println(player.getNickname());                                       // DEBUG


                    if (!isFull(option)) {
                        out.println("La partita sta per iniziare...");
                        thisGame.addGameClientHandler(this);
                        (new Thread(() -> {
                            thisGame.prepareGame();
                            thisGame.start();
                        })).start();
                    } else out.println("In attesa di altri giocatori...");
                }
            }
        } catch (IOException e) {
            System.err.println("Impossibile ottenere la connessione I/O verso: " + client.getInetAddress());
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
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        out.println(event.getJsonFromEvent());
    }

    /**
     * method that receives from the client messages and events and dispatches them in the right way
     */
    @Override
    public void run() {
        String message;

        setupPhase();

        EventBroker eventBroker = player.getGame().getEventBroker();

        // subscribing to the print message events
        eventBroker.subscribe(this, EnumSet.of(
                Events_Enum.PRINT_MESSAGE, Events_Enum.GAME_STARTED, Events_Enum.GAME_ENDED, Events_Enum.RANKING
        ));

        System.out.println("[SERVER] Ready to send/receive data from client!");

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
            } catch (SocketException e) {
                System.err.println("[SERVER] Socket exception with client");
                e.printStackTrace();
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
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

    public String getNickname() {
        return player.getNickname();
    }

    public HumanPlayer getPlayer() {
        return player;
    }

    /**
     * Instead of handling itself the event, sends it to the client
     *
     * @param event event to be sent to the client
     */
    @Override
    public void handleEvent(Event event) {
        sendEvent(event);
    }
}
