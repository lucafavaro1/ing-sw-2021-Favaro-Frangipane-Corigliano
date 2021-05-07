package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.util.EnumSet;

/**
 * abstract class that represents the interface to the user interface, weather it is the CLI or the GUI
 */
public abstract class UserInterface implements EventHandler {
    private final EventBroker eventBroker;

    public UserInterface(EventBroker eventBroker) {
        this.eventBroker = eventBroker;
        eventBroker.subscribe(this, EnumSet.of(Events_Enum.PRINT_MESSAGE));
    }

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
