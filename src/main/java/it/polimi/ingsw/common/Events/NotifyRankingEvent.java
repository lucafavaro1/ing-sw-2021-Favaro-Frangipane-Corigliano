package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;

import java.util.List;

/**
 * Event that signals the starting of the turn of a player
 */
public class NotifyRankingEvent extends Event {
    private final List<String> ranking;

    public NotifyRankingEvent(List<String> ranking) {
        eventType = Events_Enum.RANKING;
        this.ranking = ranking;
    }

    @Override
    public void handle(Object userInterface) {
        StringBuilder message = new StringBuilder("Classifica:\n");
        for (String nick : ranking)
            message.append(nick).append("\n");

        ((UserInterface) userInterface).printMessage(message.toString());
    }
}