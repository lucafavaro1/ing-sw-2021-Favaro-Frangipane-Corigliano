package it.polimi.ingsw.server.model.Events;

public class Test1Event extends Event {
    public Test1Event() {
        eventType = Events_Enum.TEST1;
    }

    @Override
    public void handle(Object target) {

    }
}
