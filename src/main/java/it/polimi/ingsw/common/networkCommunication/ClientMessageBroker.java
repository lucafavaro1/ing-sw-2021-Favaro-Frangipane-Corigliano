package it.polimi.ingsw.common.networkCommunication;

import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMessageBroker extends Thread {
    private final ClientController controller;
    private EventBroker eventBroker;
    private BufferedReader in;
    private PrintWriter out;

    // TODO: to be deleted/ integrated
    ClientMessageBroker(ClientController controller, Socket socket) {
        this.controller = controller;
        eventBroker = controller.getEventBroker();

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
        out.println(Events_Enum.getJsonFromEvent(event));
    }

    @Override
    public void run() {
        String message;
        System.out.println("[CLIENT] Welcome client!");
        // cycle that reads from the socket the messages sent by the client
        while (true) {
            try {
                message = in.readLine();
                System.out.println("[CLIENT] " + message);

                try {
                    Message msgReceived = Message.fromJson(message, MakePlayerChoose.class);
                    if (msgReceived != null) {
                        System.out.println("[CLIENT] msgReceived: " + msgReceived.getIdMessage() + " " + msgReceived.getMessage());
                        // if this is a message, then put it into the messages received
                        (new Thread(() -> sendMessage(
                                new Message(
                                        msgReceived.getIdMessage(),
                                        ((MakePlayerChoose<?>) msgReceived.getMessage()).choose()
                                )
                        ))).start();
                    } else {
                        System.out.println("[CLIENT] " + Events_Enum.getEventFromJson(message));
                        // if it hasn't been inserted, that's an event, so posts it to the player that sent it
                        eventBroker.post(Events_Enum.getEventFromJson(message), false);
                    }
                } catch (JsonSyntaxException ignore) {
                    System.out.println("[CLIENT] syntax error");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
