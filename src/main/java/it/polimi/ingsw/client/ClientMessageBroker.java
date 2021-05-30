package it.polimi.ingsw.client;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Message;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Client Message Broker that sends / receives message to / from the server using Message class
 */
public class ClientMessageBroker extends Thread {
    private final UserInterface userInterface;
    private final EventBroker eventBroker;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Basic constructor for the client message broker
     * @param eventBroker the eventbroker to link
     * @param socket the client socket
     */
    public ClientMessageBroker(EventBroker eventBroker, Socket socket) {
        this.eventBroker = eventBroker;
        this.userInterface = UserInterface.getInstance();

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to send a message to the server. Usually used to answer to a MakePlayerChoose method
     *
     * @param payload the payload to send to the server
     */
    public void sendMessage(Message payload) {
        System.out.println(payload);
        // sending message to the other end
        out.println(payload.toJson());
    }

    /**
     * Method to send an event to the server, without waiting for a response
     *
     * @param event the event to send to the server
     */
    public void sendEvent(Event event) {
        System.out.println(event);
        // sending message to the other end
        out.println(event.getJsonFromEvent());
    }

    /**
     * The core of the client message broker with the print of messages received / sent
     */
    @Override
    public void run() {
        String message;
        System.out.println("[CLIENT] Welcome client!");
        System.out.println("[CLIENT] Ready to send/receive data from server!");
        // cycle that reads from the socket the messages sent by the client
        while (true) {
            try {
                // waiting for something from the server
                // TODO: what to do when game is over?
                message = in.readLine();
                System.out.println("[CLIENT] " + message);

                // converting the received json formatted string to the right object
                try {
                    Message msgReceived = Message.fromJson(message, MakePlayerChoose.class);
                    // dispatches the messages and the events
                    if (msgReceived != null) {
                        // if this is a message, then put it into the messages received
                        (new Thread(() -> sendMessage(
                                new Message(
                                        msgReceived.getIdMessage(),
                                        userInterface.makePlayerChoose((MakePlayerChoose<?>) msgReceived.getMessage())
                                )
                        ))).start();
                    } else {
                        // if it hasn't been inserted, that's an event, so posts it to the player that sent it
                        eventBroker.post(Event.getEventFromJson(message), false);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    System.out.println("[CLIENT] syntax error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public EventBroker getEventBroker() {
        return eventBroker;
    }
}
