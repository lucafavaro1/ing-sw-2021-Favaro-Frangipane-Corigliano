package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

/**
 * Event that signals the starting of the turn of a player
 */
public class FirstPlayerEvent extends Event {

    public FirstPlayerEvent() {
        eventType = Events_Enum.FIRST_PLAYER;
    }

    @Override
    public void handle(Object userInterface) {
        if (((UserInterface) userInterface).getClass() == CLIUserInterface.class)
            ((UserInterface) userInterface).printMessage("Sei il primo giocatore!");
        else {
            if (Controller.getSingleormulti() == 1) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ImageView x = (ImageView) Controller.getPersonalpunchboard().lookup("#calamaio_firstplayer");
                        x.setOpacity(1);
                    }
                });
            }
        }
    }
}
