package it.polimi.ingsw.common.networkCommunication;

import it.polimi.ingsw.client.GameClient;
import it.polimi.ingsw.server.GameServer;

import java.io.IOException;

public class TestingClientServer {
    public static void main(String[] args) {
        (new Thread(() -> GameServer.main(null))).start();

        GameClient.main(null);
    }
}
