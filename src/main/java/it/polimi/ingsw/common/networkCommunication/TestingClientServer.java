package it.polimi.ingsw.common.networkCommunication;

import it.polimi.ingsw.client.GameClient;
import it.polimi.ingsw.server.GameServer;

import java.io.IOException;
import java.net.UnknownHostException;

// todo delete
public class TestingClientServer {
    public static void main(String[] args) {
        (new Thread(() -> {
            try {
                GameServer.main(null);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        })).start();

        GameClient.main(null);
    }
}
