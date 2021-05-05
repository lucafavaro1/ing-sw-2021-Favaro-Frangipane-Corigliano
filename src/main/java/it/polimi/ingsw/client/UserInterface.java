package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

/**
 * abstract class that represents the interface to the user interface, wether it is the CLI or the GUI
 */
public abstract class UserInterface implements EventHandler {
    /* TODO magari inserire variabili per memorizzare l'attuale stato? */

    /**
     * method that deals with showing to the user the different options the player could choose
     *
     * @param makePlayerChoose the makePlayerChoose received
     * @return the option chosen by the user
     */
    public abstract int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose);

    /**
     * method that shows a message on the screen
     *
     * @param message message to be shown
     */
    public abstract void printMessage(String message);

    /**
     * method that updates the objects showed to the player
     *
     * @param event the particular view Event received
     */
    public void updateView(Event event){
        // TODO develop? (if we keep a version of the model in the client)
    }
}
