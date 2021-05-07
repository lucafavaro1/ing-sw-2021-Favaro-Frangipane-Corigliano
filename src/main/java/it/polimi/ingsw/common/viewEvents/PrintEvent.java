package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;

// TODO javadoc
public class PrintEvent extends Event {
    protected String textMessage="";

    @Override
    public void handle(Object userInterface) {
        ((UserInterface) userInterface).printMessage(textMessage);
    }
}
