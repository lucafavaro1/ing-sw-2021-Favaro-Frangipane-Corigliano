package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.PlayerRequest;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Thread that deals with the proceeding of the game and waits for the player to do an action
 */
public class CliClientController extends ClientController {

    /**
     * Basic constructor that links the clientcontroller with the eventbroker and the client socket
     *
     * @param eventBroker the eventbroker
     * @param socket      client socket
     */
    public CliClientController(EventBroker eventBroker, Socket socket) {
        super(eventBroker, socket);
    }

    /**
     * Main method that deals with the player rounds and the actions he wants to do
     */
    @Override
    public void run() {
        // waiting for the beginning of the game
        synchronized (this) {
            while (!gameRunning) {
                try {
                    System.out.println("Waiting for match to begin...");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            while (!gamePrepared) {
                try {
                    System.out.println("Waiting for initial preparation...");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // main cycle in which the player chooses the action he wants to do and waits for a response of the server
        while (gameRunning) {
            synchronized (this) {
                chooseOptions();
                while (waitingForResponse) {
                    //System.out.println("WaitingForResponse (still true)");
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    /**
     * Start Turn event send to every player at the begin of its turn
     */
    @Override
    public void startTurn() {
        synchronized (lockPlaying) {
            playing = true; // added here
            userInterface.printMessage("\nIT'S YOUR TURN!\n");
        }
    }

    /**
     * End Turn event send to every player at the end of its turn
     */
    @Override
    public void endTurn() {
        synchronized (lockPlaying) {
            playing = false;
            userInterface.printMessage("\nYOUR TURN ENDED!\n");
        }
    }

    /**
     * Start Game event send to every player at the begin of the game
     * This is also used to change view from waiting for players scene to the punchboard
     */
    @Override
    public synchronized void gameStarted() {
        gameRunning = true;
        userInterface.printMessage("\nGAME STARTED!\n");
        userInterface.printMessage(userInterface.getDcBoard().toString() + "\n" + userInterface.getMarketTray().toString());
        notifyAll();
    }

    /**
     * Method that makes the player do an action
     */
    private void chooseOptions() {
        List<Object> eventList = new ArrayList<>(Arrays.asList(PlayerViewOptions.values()));
        eventList.addAll(Arrays.asList(PlayerActionOptions.values()));

        if (userInterface.getLastActionCard() == null)
            eventList.remove(PlayerViewOptions.ACTION_CARD);

        Object request = eventList.get(userInterface.makePlayerChoose(
                new MakePlayerChoose<>(
                        userInterface.getMyNickname() +
                                (playing ?
                                        " YOU ARE PLAYING!" + (userInterface.getMyPlayer() != null && userInterface.getMyPlayer().isActionDone() ? " [main action done]" : " ") :
                                        ""
                                ),
                        eventList
                )
        ));

        if (!gameRunning) {
            return;
        }

        if (Arrays.asList(PlayerViewOptions.values()).contains(request)) {
            ((PlayerViewOptions) request).view();
            return;
        }

        try {
            PlayerRequest action = (PlayerRequest) request;
            // checks that if the player choose an action, sends the event only if the player is playing
            if (!playing /* && !Arrays.asList(PrintObjects_Enum.values()).contains(action)*/) {
                userInterface.printFailMessage("Can't do this action: it's not your turn!");
                return;
            }

            Event event = action.getRelativeEvent(userInterface);

            clientMessageBroker.sendEvent(event);
            //System.out.println("WaitingForResponse: true");
            waitingForResponse = true;

        } catch (IllegalArgumentException e) {
            userInterface.printFailMessage("Action aborted");
        }
    }
}
