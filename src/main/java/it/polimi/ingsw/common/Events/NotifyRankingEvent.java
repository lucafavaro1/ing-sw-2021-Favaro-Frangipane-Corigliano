package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;

import java.util.List;

/**
 * Event that signals the starting of the turn of a player
 */
public class NotifyRankingEvent extends Event {
    private final List<String> ranking;
    private final List<Integer> points;

    public NotifyRankingEvent(List<Integer> points, List<String> ranking) {
        eventType = Events_Enum.RANKING;
        this.ranking = ranking;
        this.points = points;
    }

    @Override
    public void handle(Object userInterface) {
        StringBuilder message = new StringBuilder("Classifica:\n");
        for (int i = 0; i < ranking.size(); i++) {
            message.append(i + 1).append(") ")
                    .append(ranking.get(i));

            if (!ranking.get(i).equals("Lorenzo (CPU)"))
                message.append(": ").append(points.get(i));

            message.append("\n");
        }

        ((UserInterface) userInterface).printMessage(message.toString());
    }
}