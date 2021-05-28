package it.polimi.ingsw.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.common.Message;
import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintMarketTrayEvent;
import it.polimi.ingsw.common.viewEvents.PrintPlayerEvent;
import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

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
    private Socket client;
    private BufferedReader in, stdIn;
    private PrintWriter out;
    private HumanPlayer player;
    private NetTuple newPlayer;
    private GameHandler thisGame;
    private final Object connectionLock = new Object();
    private int maxKey = 1;
    private boolean connected = true;

    private String invOption = "Invalid option, choose again :";
    private String lobbyIsFull = "Lobby is full, cannot join";
    private String gameTypeStr = "Choose a game mode : 1)SinglePlayer     2)MultiPlayer";
    private String matchTypeStr = "Choose an option : 1)Create a new lobby     2)Join an existing lobby";
    private String numOfPlayersStr = "Choose the number of players (2-4): ";
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
        String nickname;

        String str = "";

        try {
            List<String> nicknamesTaken = GameServer.getClients().stream()
                    .map(GameClientHandler::getNickname)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            nicknamesTaken.add("Lorenzo (CPU)");

            List<String> nicksDisconnectedPlayers = GameServer.getClients().stream()
                    .filter(gameClientHandler -> !gameClientHandler.isConnected())
                    .map(GameClientHandler::getNickname)
                    .collect(Collectors.toList());

            String chooseNickMessage;
            if (!nicknamesTaken.isEmpty()) {
                chooseNickMessage = "Choose a valid nickname (already taken: " + String.join(", ", nicknamesTaken);
                if (!nicksDisconnectedPlayers.isEmpty()) {
                    chooseNickMessage += "; Disconnected players: " + String.join(", ", nicksDisconnectedPlayers);
                }
                chooseNickMessage += ")";
            } else {
                chooseNickMessage = "Choose a valid nickname: ";
            }
            out.println(chooseNickMessage);

            str = in.readLine();

            String finalStr = str;
            while (finalStr.isBlank() || (nicknamesTaken.contains(finalStr) && !nicksDisconnectedPlayers.contains(finalStr))) {
                out.println("Invalid nicnkame");

                if (!nicknamesTaken.isEmpty()) {
                    chooseNickMessage = "Choose a valid nickname (already taken: " + String.join(", ", nicknamesTaken);
                    if (!nicksDisconnectedPlayers.isEmpty()) {
                        chooseNickMessage += "; Disconnected players: " + String.join(", ", nicksDisconnectedPlayers);
                    }
                    chooseNickMessage += ")";
                } else {
                    chooseNickMessage = "Choose a valid nickname: ";
                }
                out.println(chooseNickMessage);

                finalStr = in.readLine(); //ricezione nickname
                System.out.println(finalStr);
            }

            // if a player reconnects, calls the reconnect method of the relative player and ends this thread
            nickname = finalStr;
            if (nicksDisconnectedPlayers.contains(finalStr)) {
                GameServer.getClients().stream()
                        .filter(gameClientHandler -> gameClientHandler.getNickname() != null && gameClientHandler.getNickname().equals(nickname))
                        .collect(Collectors.toList())
                        .get(0)
                        .reconnect(client);

                throw new ReconnectedException();
            }

            out.println("Okay, nickname chosen:" + finalStr);
            str = finalStr;
        } catch (IOException e) {
            System.err.println("Cannot get I/O connection to: " + client.getInetAddress());
            System.exit(1);
        }

        return str;
    }

    /**
     * Standard block to print an invalid option message and send request again
     *
     * @param error error message
     * @param again try again message
     * @param in    BufferReader of server input on socket
     * @param out   PrintWriter of server output on socket
     * @return last string (used to update str in the code)
     * @throws IOException in case of improper inputs
     */
    public String invalidOption(String error, String again, BufferedReader in, PrintWriter out) throws IOException {
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
        System.out.println("Starting client communication: ");
        int count;

        //ciclo di ricezione dal client e invio di risposta
        try {
            int option;
            // singleplayer o multiplayer
            {
                option = -1;
                out.println("Choose a game mode : 1)Single Player     2)MultiPlayer");
                try {
                    //Ricezione gametype
                    option = Integer.parseInt(in.readLine());
                } catch (NumberFormatException ignored) {
                }

                count = 1;
                while (option != 1 && option != 2) {             //Controllo gametype ( e eventuale nuova ricezione )
                    try {
                        option = Integer.parseInt(invalidOption(invOption, gameTypeStr, in, out));
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

                System.out.println("SinglePlayer mode chosen!");            // DEBUG
                out.println("SinglePlayer mode chosen!");
                player.setNickname(chooseNick(in, out));// scelta nickname valido
                System.out.println(player.getNickname());                               // DEBUG
                out.println("Creating a new match...");

                (new Thread(() -> {
                    thisGame.prepareGame();
                    thisGame.start();
                })).start();
            } else {
                out.println("MultiPlayer mode chosen!");
                System.out.println("MultiPlayer Mode chosen!");                   // DEBUG

                // create o join
                {
                    option = -1;
                    out.println("Choose an option : 1)Create a new lobby     2)Join an existing lobby");
                    try {
                        option = Integer.parseInt(in.readLine());
                    } catch (NumberFormatException ignored) {
                    }
                    count = 1;
                    while (option != 1 && option != 2 && GameServer.getGameHandlers().isEmpty()) {
                        try {
                            option = Integer.parseInt(invalidOption(invOption, matchTypeStr, in, out));
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

                    out.println("Multiplayer: create a new match");
                    System.out.println("Multiplayer: create a new match");             // DEBUG
                    String nick = chooseNick(in, out);                // scelta nickname valido
                    out.println("Choose the number of players (2-4): ");
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
                                option = Integer.parseInt(invalidOption(invOption, numOfPlayersStr, in, out));
                            } catch (NumberFormatException ignored) {
                            }
                            count++;
                        }
                    }
                    System.out.println("Number of player chosen: " + option);             // DEBUG

                    thisGame = new GameHandler(option);
                    thisGame.addGameClientHandler(this);
                    GameServer.addGameHandler(thisGame);
                    player = (HumanPlayer) thisGame.getGame().getPlayers().get(0);
                    player.setGameClientHandler(this);
                    System.out.println(player.getNickname());                                       // DEBUG
                    player.setNickname(nick);

                    out.println("Multiplayer: creating a new lobby...");
                    out.println("Waiting for other players.. ");
                } else {
                    //////////////////////////////////////////
                    // MULTIPLAYER JOIN MATCH
                    out.println("Multiplayer: joining an existing match");
                    matchIDStr = "Choose a lobby  " + GameServer.getGameHandlers().keySet() + ":";
                    // ricezione lobby in cui entrare
                    {
                        option = -1;
                        out.println(matchIDStr);
                        try {
                            option = Integer.parseInt(in.readLine());
                        } catch (NumberFormatException ignored) {
                        }

                        while (option == 0) {
                            out.println("MultiPlayer : joining an existing match");
                            matchIDStr = "Choose a lobby " + GameServer.getGameHandlers().keySet() + ":";
                            out.println(matchIDStr);
                            try {
                                option = Integer.parseInt(in.readLine());
                            } catch (NumberFormatException ignored) {
                            }

                        }
                        count = 1;
                        while (!GameServer.getGameHandlers().containsKey(option) || !isJoinable(option)) {
                            try {
                                option = Integer.parseInt(invalidOption(invOption, matchIDStr, in, out));
                            } catch (NumberFormatException ignored) {
                            }
                            count++;
                        }
                    }

                    thisGame = GameServer.getGameHandlers().get(option);
                    player = (HumanPlayer) thisGame.getGame()
                            .getPlayers().get(GameServer.getGameHandlers().get(option).getClientHandlers().size());
                    player.setGameClientHandler(this);

                    out.println("Succesfully joined lobby " + option);
                    System.out.println("Successfully joined lobby " + option);          // DEBUG
                    player.setNickname(chooseNick(in, out));
                    System.out.println(player.getNickname());                                       // DEBUG


                    if (!isFull(option)) {
                        out.println("Match is about to start...");
                        thisGame.addGameClientHandler(this);
                        (new Thread(() -> {
                            thisGame.prepareGame();
                            thisGame.start();
                        })).start();
                    } else out.println("Waiting for other players...");
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot get I/O connection to: " + client.getInetAddress());
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

        try {
            setupPhase();
        } catch (ReconnectedException ignored) {
            return;
        }
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
                        // if this is a message, then put it into the messages received
                        System.out.println("[SERVER] " + "msgReceived: " + msgReceived.getIdMessage() + " " + msgReceived.getMessage());
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
                // Waiting for reconnection!
                System.err.println("[SERVER] Socket exception with client " + player.getNickname());

                synchronized (connectionLock) {
                    connected = false;
                    try {
                        in.close();
                        out.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    while (!connected) {
                        try {
                            connectionLock.wait();
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }
                System.err.println("[SERVER] Client " + player.getNickname() + "reconnected!");
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void reconnect(Socket socket) {
        synchronized (connectionLock) {
            this.client = socket;
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            connected = true;
            out.println("You reconnected to Masters of Renaissance");
            if (thisGame.isStarted())
                sendEvent(new GameStartedEvent());

            if (player.isPlaying())
                sendEvent(new StartTurnEvent());

            if (player.isPlaying())
                sendEvent(new StartTurnEvent());

            // updating the client about the game situation
            // TODO: send all the players situation
            sendEvent(new PrintPlayerEvent(player));
            sendEvent(new PrintDcBoardEvent(thisGame.getGame()));
            sendEvent(new PrintMarketTrayEvent(thisGame.getGame()));

            connectionLock.notifyAll();
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
        if (player == null)
            return null;

        return player.getNickname();
    }

    public HumanPlayer getPlayer() {
        return player;
    }

    public boolean isConnected() {
        return connected;
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
