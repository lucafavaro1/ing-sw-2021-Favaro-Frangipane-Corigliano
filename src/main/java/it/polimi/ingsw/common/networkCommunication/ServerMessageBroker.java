package it.polimi.ingsw.common.networkCommunication;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.BuyDevCardEvent;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerMessageBroker extends Thread {
    private final HumanPlayer player;

    private BufferedReader in;
    private PrintWriter out;
    private int maxKey = 1;
    private final Map<Integer, String> messagesReceived = new HashMap<>();

    ServerMessageBroker(HumanPlayer player, Socket socket) {
        this.player = player;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * used in order to send a message and receive a response to it from the client (used for MakePlayerChoose)
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
        out.println(Events_Enum.getJsonFromEvent(event));
    }

    private boolean insertResponse(Message message) {
        if (message.getIdMessage() > 0 && messagesReceived.get(message.getIdMessage()) == null &&
                messagesReceived.containsKey(message.getIdMessage()) && message.getMessage() != null) {
            messagesReceived.put(message.getIdMessage(), message.getMessage().toString());
            return true;
        } else {
            return false;
        }
    }

    private synchronized int nextKey() {
        while (messagesReceived.containsKey(maxKey)) {
            maxKey++;
        }
        return maxKey;
    }

    @Override
    public void run() {
        String message;
        EventBroker eventBroker = player.getGame().getEventBroker();

        // cycle that reads from the socket the messages sent by the client
        while (true) {
            try {
                message = in.readLine();
                System.out.println("[SERVER] "+message);

                try {
                    Message msgReceived = Message.fromJson(message, Object.class);
                    if (msgReceived != null) {
                        System.out.println("[SERVER] "+"msgReceived: " + msgReceived.getIdMessage() + " " + msgReceived.getMessage());
                        // if this is a message, then put it into the messages received
                        synchronized (this) {
                            if (insertResponse(msgReceived)) {
                                System.out.println("[SERVER] "+messagesReceived);
                                this.notifyAll();
                            } else {
                                System.out.println("[SERVER] "+"syntax error");
                            }
                        }
                    } else {
                        // if it hasn't been inserted, that's an event, so posts it to the player that sent it
                        eventBroker.post(player, Events_Enum.getEventFromJson(message), false);
                        System.out.println("[SERVER] "+Events_Enum.getEventFromJson(message));
                    }
                } catch (JsonSyntaxException ignore) {
                    System.out.println("[SERVER] "+"syntax error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
