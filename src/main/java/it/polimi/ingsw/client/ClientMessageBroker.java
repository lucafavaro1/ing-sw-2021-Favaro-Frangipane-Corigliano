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

public class ClientMessageBroker extends Thread {
    private final UserInterface userInterface;
    private final EventBroker eventBroker;
    private final ClientController clientController;
    private BufferedReader in;
    private PrintWriter out;

    public ClientMessageBroker(EventBroker eventBroker, UserInterface userInterface, Socket socket) {
        this.clientController = new ClientController(this, userInterface, eventBroker);
        this.eventBroker = eventBroker;
        this.userInterface = userInterface;

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        clientController.start();
    }

    /**
     * Method to send a message to the server. Usually used to answer to a MakePlayerChoose method
     *
     * @param payload the payload to send to the server
     */
    public void sendMessage(Message payload) {
        // sending message to the other end
        out.println(payload.toJson());
    }

    /**
     * Method to send an event to the server, without waiting for a response
     *
     * @param event the event to send to the server
     */
    public void sendEvent(Event event) {
        // sending message to the other end
        out.println(Event.getJsonFromEvent(event));
    }

    @Override
    public void run() {
        String message;
        System.out.println("[CLIENT] Welcome client!");
        // cycle that reads from the socket the messages sent by the client
        while (true) {
            try {
                message = in.readLine();

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
                        System.out.println("[CLIENT] " + message);
                        System.out.println("[CLIENT] " + Event.getEventFromJson(message));
                        eventBroker.post(Event.getEventFromJson(message), false);
                    }
                } catch (JsonSyntaxException ignore) {
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
