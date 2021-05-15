package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.client.gui.controllers.DcBoardController;
import it.polimi.ingsw.client.gui.controllers.marketTrayController;
import it.polimi.ingsw.client.gui.controllers.punchboardController;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import javafx.scene.Scene;
import javafx.scene.image.Image;


import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class GUIUserInterface extends UserInterface {
    private static Scene personalpunchboard;
    private static Scene markettray;
    private static Scene dcboard;
    private static Scene leadercards;

    public GUIUserInterface(EventBroker eventBroker)
    {
        super(eventBroker);
        personalpunchboard = Controller.getPersonalpunchboard();
        markettray = Controller.getMarkettray();
        dcboard = Controller.getDcboard();
        leadercards = Controller.getLeadercards();

    }

    // CHANGE IN ORDER TO MAKE IT WORK WITH GUI
    @Override
    public synchronized int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();

        // printing a nice message
        StringBuilder message = new StringBuilder(makePlayerChoose.getMessage() + "\n");
        message.append("Choose one of the following" + "\n");
        for (int i = 0; i < toBeChosen.size(); i++) {
            message.append(i + 1).append(")").append(toBeChosen.get(i)).append("\n");
        }
        System.out.print(message);

        do {
            System.out.println("Insert a number between 1 and " + (toBeChosen.size()) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine())-1;
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return chosen;
    }

    //@TODO: replicare singleton sugli altri controller (+getinstance())
    @Override
    public void printMessage(Object message) {
        if(message.getClass() == MarketTray.class) {
            MarketTray mymarket = (MarketTray) message;
            marketTrayController.getInstance().conversion(mymarket);
        }
        else if(message.getClass() == DcBoard.class) {
            DcBoard totboard = (DcBoard) message;
            DcBoardController.getInstance().conversion(totboard);
        }
        else if(message.getClass() == FaithTrack.class) {
            FaithTrack faithTrack=(FaithTrack) message;
            punchboardController.populate();
            punchboardController.getInstance().update(faithTrack);
            // punch controller
        }
        else if(message.getClass() == DcPersonalBoard.class) {
            // punch controller
        }
        else if(message.getClass() == LeaderCard.class) {
            // aspettare a farlo
        }
        else if(message.getClass() == StrongBox.class) {
            // aspettare a farlo
        }
        else if(message.getClass() == WarehouseDepots.class) {
            // aspettare a farlo
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printFailMessage(String message) {
        System.err.println(message);
    }
}
