package it.polimi.ingsw.common.networkCommunication;

import com.google.gson.Gson;
import it.polimi.ingsw.common.Events.BuyDevCardEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

// TODO to be deleted
public class TestingCommunication {
    static ServerSocket serverSocket;

    public static void acceptClients() {
        /*Object makePlayerChoose = new MakePlayerChoose<>(List.of(1,2,3,4,5));
        ((MakePlayerChoose<?>) makePlayerChoose).choose();*/

        try {
            serverSocket = new ServerSocket(48000);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server Ready: ");
        System.out.println("Awaiting for client connections: ");

        while (true) {
            try {
                Game game = new Game(2);

                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted Client");

                ServerMessageBroker messageBroker = new ServerMessageBroker((HumanPlayer) game.getPlayers().get(0), clientSocket);
                (new Thread(() -> (new MakePlayerChoose<>(List.of("Sa", "ra", "no", "sì"))).choose(messageBroker))).start();
                messageBroker.start();
            } catch (IOException e) {
                System.err.println("There's no server ON at this port! ");
                break;
            }
        }
    }

    // TODO to be deleted
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket;
        Socket clientSocket;

        Gson gson = new Gson();
        System.out.println(gson.toJson(new Message(1, "ciao :)")));
        System.out.println(gson.toJson(new BuyDevCardEvent(new Tuple(TypeDevCards_Enum.BLUE, 1))));

        // starting the server
        (new Thread(TestingCommunication::acceptClients)).start();

        // starting the client
        ClientMessageBroker clientMessageBroker = new ClientMessageBroker(
                new ClientController(),
                new Socket("127.0.0.1", 48000)
        );
        clientMessageBroker.start();
    }
}
