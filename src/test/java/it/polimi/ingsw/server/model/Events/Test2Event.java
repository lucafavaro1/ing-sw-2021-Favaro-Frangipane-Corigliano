package it.polimi.ingsw.server.model.Events;

public class Test2Event extends Event {
    public Test2Event() {
        eventType = Events_Enum.TEST2;
    }

    @Override
    public void handle(Object target) {

    }
}
