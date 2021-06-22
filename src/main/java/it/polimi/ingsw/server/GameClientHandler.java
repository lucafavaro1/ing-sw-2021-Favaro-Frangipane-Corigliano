package it.polimi.ingsw.server;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.common.Message;
import it.polimi.ingsw.common.networkCommunication.Pingable;
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

import static java.lang.Thread.sleep;

/**
 * Class that manages the connections between client and server, letting multiple connections
 * thanks to Runnable interface
 */
public class GameClientHandler extends Pingable implements Runnable, EventHandler {
    private final Map<Integer, String> messagesReceived = new HashMap<>();
    private Socket client;
    private BufferedReader in, stdIn;
    private PrintWriter out;
    private HumanPlayer player;
    private GameHandler thisGame;
    private final Object connectionLock = new Object();
    private int maxKey = 1;

    private final String invOption = "Invalid option, choose again :";
    private final String gameTypeStr = "Choose a game mode : 1)SinglePlayer     2)MultiPlayer";
    private final String matchTypeStr = "Choose an option : 1)Create a new lobby     2)Join an existing lobby";
    private final String numOfPlayersStr = "Choose the number of players (2-4): ";
    private String matchIDStr = "Insert a valid Match ID (1 to 9):";

    /**
     * Constructor of the GameClientHandler
     *
     * @param clientSocket the client socket
     * @throws IOException in case of improper inputs
     */
    public GameClientHandler(Socket clientSocket) throws IOException {
        this.client = clientSocket;
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    /**
     * Standard method to choose a nickname in different modes
     *
     * @param in  BufferReader of server input on socket
     * @param out PrintWriter of server output on socket
     */
    public String chooseNick(BufferedReader in, PrintWriter out) throws IOException {
        String nickname;

        String str;

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
            out.println("Invalid nickname");

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
        return (gameToJoin.getClientHandlers().size() < gameToJoin.getMaxPlayers()) && !gameToJoin.isRunning();
    }

    /**
     * method that deals with the setup phase of the client
     */
    private void setupPhase() throws IOException {
        System.out.println("Starting client communication: ");

        //ciclo di ricezione dal client e invio di risposta

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

            while (option != 1 && option != 2) {             //Controllo gametype ( e eventuale nuova ricezione )
                try {
                    option = Integer.parseInt(invalidOption(invOption, gameTypeStr, in, out));
                } catch (NumberFormatException ignored) {
                }
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
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // notifying the players that the game is starting
                thisGame.getGame().getEventBroker().post(new GameStartedEvent(thisGame.getGame()), false);

                // preparing the game and starting the game
                thisGame.prepareGame();
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

                while (option != 1 && option != 2) {
                    try {
                        option = Integer.parseInt(invalidOption(invOption, matchTypeStr, in, out));
                    } catch (NumberFormatException ignored) {
                    }
                }

                if ((GameServer.getGameHandlers().isEmpty() ||
                        GameServer.getGameHandlers().keySet().stream().noneMatch(this::isJoinable)) && option == 2
                ) {
                    System.out.println("[SERVER] no lobbies: creating a new match");
                    option = 1;
                    out.println("There are no lobby available, creating a match");
                }
            }

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

                    while (option <= 1 || option > 4) {
                        try {
                            option = Integer.parseInt(invalidOption(invOption, numOfPlayersStr, in, out));
                        } catch (NumberFormatException ignored) {
                        }
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

                    while (!GameServer.getGameHandlers().containsKey(option) || !isJoinable(option)) {
                        try {
                            option = Integer.parseInt(invalidOption(invOption, matchIDStr, in, out));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }

                thisGame = GameServer.getGameHandlers().get(option);
                player = (HumanPlayer) thisGame.getGame()
                        .getPlayers()
                        .get(GameServer.getGameHandlers().get(option).getClientHandlers().size());
                player.setGameClientHandler(this);

                out.println("Succesfully joined lobby " + option);
                System.out.println("Successfully joined lobby " + option);          // DEBUG
                player.setNickname(chooseNick(in, out));
                System.out.println(player.getNickname());                                       // DEBUG

                thisGame.addGameClientHandler(this);

                if (isFull(option)) {
                    out.println("Match is about to start...");

                    (new Thread(() -> {
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // notifying the players that the game is starting
                        thisGame.getGame().getEventBroker().post(new GameStartedEvent(thisGame.getGame()), true);

                        // preparing the game and starting the game
                        thisGame.prepareGame();
                    })).start();
                } else out.println("Waiting for other players...");
            }
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
     *
     * @param event the event to send to the client
     */
    public void sendEvent(Event event) {
        // sending message to the other end
        out.println(event.getJsonFromEvent());
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
     * Method that restores the connection with the client, reopens the communication streams and sends the starting events
     *
     * @param socket new socket of the reconnected player
     */
    public void reconnect(Socket socket) {
        System.out.println(player.getNickname() + " reconnecting...");
        synchronized (connectionLock) {
            try {
                client.close();
                this.client = socket;

                // restoring the input and output streams with the client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
            } catch (IOException e) {
                e.printStackTrace();
            }

            nPingFails = 0;
            connected = true;
            out.println("You reconnected to Masters of Renaissance");

            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            sendEvent(new GameStartedEvent(thisGame.getGame()));

            // if the player disconnected during the setup phase
            if (!player.isPreparation()) {
                System.err.println(player.getNickname() + " preparation after previous disconnection");
                player.getLeaderCards().clear();
                player.getWarehouseDepots().clear();

                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                (new Thread(thisGame.getPreparation(player))).start();
            } else {
                sendEvent(new PreparationEndedEvent(thisGame.getGame()));
            }

            if (player.isFirstPlayer())
                sendEvent(new FirstPlayerEvent());

            if (player.isPlaying())
                sendEvent(new StartTurnEvent());

            connectionLock.notifyAll();
            System.out.println(player.getNickname() + " reconnected!");
        }
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
        } catch (IOException ignored1) {
            System.err.println("Cannot get I/O connection to: " + client.getInetAddress());
            return;
        }

        EventBroker eventBroker = player.getGame().getEventBroker();

        // subscribing to the print message events
        eventBroker.subscribe(this, EnumSet.of(
                Events_Enum.PRINT_MESSAGE, Events_Enum.GAME_STARTED, Events_Enum.GAME_ENDED, Events_Enum.RANKING,
                Events_Enum.PREPARATION_ENDED, Events_Enum.PING
        ));

        System.out.println("[SERVER] Ready to send/receive data from client!");
        new Thread(this::checkConnection).start();

        // cycle that reads from the socket the messages sent by the client
        while (thisGame.isRunning() || !thisGame.getGame().isLastRound()) {
            try {
                message = in.readLine();

                try {
                    Message msgReceived = Message.fromJson(message);
                    if (msgReceived != null) {
                        // if this is a message, then put it into the messages received
                        //System.out.println("received: " + msgReceived.getIdMessage() + " " + msgReceived.getMessage());
                        synchronized (this) {
                            if (insertResponse(msgReceived)) {
                                this.notifyAll();
                            } else {
                                System.out.println("[SERVER] " + "syntax error");
                            }
                        }
                    } else {
                        // if it hasn't been inserted, that's an event, so posts it to the player that sent it
                        Event event = Event.getEventFromJson(message);

                        //if it's a ping event handle it, otherwise dispatch it to the player
                        if (event.getEventType() == Events_Enum.PING) {
                            event.handle(this);
                        } else {
                            eventBroker.post(player, event, false);
                            // DEBUG
                            System.out.println("received: " + event);
                        }

                    }
                } catch (JsonSyntaxException ignore) {
                    System.out.println("[SERVER] " + "syntax error");
                }
            } catch (SocketException e) {
                // if the game isn't running anymore and the last round happened, end the thread
                if (!thisGame.isRunning() && thisGame.getGame().isLastRound()) {
                    break;
                }

                if (connected) {
                    notifyDisconnection();
                } else {
                    synchronized (connectionLock) {
                        try {
                            while (!connected) {
                                System.out.println("RUN waiting when disconnected");
                                connectionLock.wait();
                                connectionLock.notifyAll();
                            }
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("[SERVER] Client " + player.getNickname() + ": generic IOException.");
                e.printStackTrace();
                break;
            } catch (Exception e) {
                // if the player disconnects in a "bad moment" the player can still reconnect later without further
                System.err.println("[SERVER] Client " + player.getNickname() + " general exception: " + e.getMessage());
                e.printStackTrace();
                notifyDisconnection();
            }
        }

        GameServer.getClients().remove(this);
    }

    @Override
    protected void notifyDisconnection() {
        synchronized (connectionLock) {
            // Waiting for reconnection!
            System.err.println("[SERVER] Client " + player.getNickname() + " disconnected. waiting for his reconnection");
            connected = false;

            if (!thisGame.isRunning()) {
                // notifying to the preparation process that the player disconnected
                thisGame.deletePreparation(null);
            }

            while (!connected) {
                try {
                    System.out.println(player.getNickname() + " waiting 60 seconds for reconnection!");
                    // waiting for the player to reconnect for a minute, then the turn is passed to the successive player
                    connectionLock.wait(60 * 1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                if (!connected) {
                    System.out.println(player.getNickname() + " passing turn");
                    player.endTurn();
                }
            }
            connectionLock.notifyAll();
        }
        System.err.println("[SERVER] Client " + player.getNickname() + " reconnected!");
    }

    @Override
    protected void sendPing() {
        sendEvent(new PingEvent());
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
