package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.ActionDoneEvent;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;

// TODO javadoc
public class PrintEvent extends Event {
    protected String textMessage = "";

    public PrintEvent() {
        eventType = Events_Enum.PRINT_MESSAGE;
    }

    public PrintEvent(String textMessage) {
        eventType = Events_Enum.PRINT_MESSAGE;
        this.textMessage = textMessage;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);
        userInterface.printMessage(textMessage);
        userInterface.getEventBroker().post(new ActionDoneEvent(""), true);
    }
}
