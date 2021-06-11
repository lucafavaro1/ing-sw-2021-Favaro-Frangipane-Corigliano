package it.polimi.ingsw.client.controller;

import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.common.Events.EventBroker;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.Socket;

/**
 * Thread that deals with the proceeding of the game and waits for the player to do an action
 */
public class GuiClientController extends ClientController {

    /**
     * Basic constructor that links the clientcontroller with the eventbroker and the client socket
     *
     * @param eventBroker the eventbroker
     * @param socket      client socket
     */
    public GuiClientController(EventBroker eventBroker, Socket socket) {
        super(eventBroker, socket);
    }

    /**
     * Start Game event send to every player at the begin of the game
     * This is also used to change view from waiting for players scene to the punchboard
     */
    public synchronized void gameStarted() {
        gameRunning = true;
        userInterface.printMessage("\nGAME STARTED!\n");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Controller.getPrimarystage().setMinHeight(788);
                Controller.getPrimarystage().setMaxHeight(788);
                Controller.getPrimarystage().setMinWidth(1005);
                Controller.getPrimarystage().setMaxWidth(1005);
                Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
                Controller.getPrimarystage().show();
            }
        });
        notifyAll();
    }

    /**
     * Start Turn event send to every player at the begin of its turn
     */
    public void startTurn() {
        synchronized (lockPlaying) {
            playing = true; // added here
            userInterface.printMessage("\nIT'S YOUR TURN!\n");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                    y.setOpacity(1);
                }
            });
        }
    }

    /**
     * End Turn event send to every player at the end of its turn
     */
    public void endTurn() {
        synchronized (lockPlaying) {
            playing = false;
            userInterface.printMessage("\nYOUR TURN ENDED!\n");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    VBox right = (VBox) Controller.getProductions().lookup("#activateProduction");
                    VBox left = (VBox) Controller.getProductions().lookup("#addProduction");
                    right.getChildren().clear();
                    left.getChildren().clear();
                    Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                    y.setOpacity(0);
                }
            });
        }
    }
}
