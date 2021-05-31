package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import javafx.application.Platform;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.io.IOException;
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
        if (userInterface.getClass() == CLIUserInterface.class) {
            StringBuilder message = new StringBuilder("Rankings:\n");
            for (int i = 0; i < ranking.size(); i++) {
                message.append(i + 1).append(") ")
                        .append(ranking.get(i));

                if (!ranking.get(i).equals("Lorenzo (CPU)"))
                    message.append(": ").append(points.get(i));

                message.append("\n");
            }

            ((UserInterface) userInterface).printMessage(message.toString());
        }
        else
        {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    try {
                        Controller.loadScene("finalRankings.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < ranking.size(); i++) {
                        Label name = (Label) Controller.getPrimarystage().getScene().lookup("#nickname".concat(String.valueOf(i+1)));
                        name.setText(ranking.get(i));
                        Label res = (Label) Controller.getPrimarystage().getScene().lookup("#score".concat(String.valueOf(i+1)));
                        if(points.get(i) != -1)
                            res.setText("" + points.get(i));
                    }
                }
            });
        }
    }

}