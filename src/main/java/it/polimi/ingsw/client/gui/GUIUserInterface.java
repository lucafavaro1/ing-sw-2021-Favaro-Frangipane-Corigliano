package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GUIUserInterface extends UserInterface {
    private static Scene personalpunchboard;
    private static Scene markettray;
    private static Scene dcboard;
    private static Scene leadercards;
    private static Stage primary;

    private static int input = -1;

    public GUIUserInterface(EventBroker eventBroker) {
        super(eventBroker);
        personalpunchboard = Controller.getPersonalpunchboard();
        markettray = Controller.getMarkettray();
        dcboard = Controller.getDcboard();
        leadercards = Controller.getLeadercards();
        primary = Controller.getPrimarystage();
    }
    /*
    // CHANGE IN ORDER TO MAKE IT WORK WITH GUI
    @Override
    public synchronized int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();
        int chosen;

        do {
            while (input == -1) {
                try {
                    System.out.println("[GUI] waiting for an answer: ");
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            chosen = input;
            input = -1;
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        System.out.println("[GUI] returning the chosen element: " + chosen);
        return chosen;
    }

    public synchronized void choose(int chosen) {
        input = chosen;
        System.out.println("[GUI] player choose: " + chosen);
        notifyAll();
    }

     */

    @Override
    public synchronized int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();

        if(toBeChosen.get(0).getClass() == LeaderCard.class) {

        }
        // printing a nice message
        StringBuilder message = new StringBuilder(makePlayerChoose.getMessage() + "\n");
        message.append("Choose one of the following" + "\n");
        for (int i = 0; i < toBeChosen.size(); i++) {
            message.append(i + 1).append(")").append(toBeChosen.get(i).toString()).append("\n");
        }
        System.out.print(message);

        do {
            System.out.println("Insert a number between 1 and " + (toBeChosen.size()) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine()) - 1;
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return chosen;
    }

    @Override
    public void printMessage(Object message) {
        if (message.getClass() == MarketTray.class) {
            MarketTray mymarket = (MarketTray) message;
            marketTrayController.getInstance().conversion(mymarket);
        } else if (message.getClass() == DcBoard.class) {
            DcBoard totboard = (DcBoard) message;
            DcBoardController.getInstance().conversion(totboard);
        } else if (message.getClass() == FaithTrack.class) {
            FaithTrack faithTrack = (FaithTrack) message;
            //punchboardController.getInstance().populate();
            punchboardController.getInstance().updateFaith(faithTrack);
        } else if (message.getClass() == DcPersonalBoard.class) {
            DcPersonalBoard personalBoard = (DcPersonalBoard) message;
            try {
                punchboardController.getInstance().updateDCPersonalBoard(personalBoard);
            } catch (BadSlotNumberException e) {
                e.printStackTrace();
            }
        } else if (message.getClass() == ArrayList.class) {
            ArrayList<LeaderCard> leaderCards = (ArrayList<LeaderCard>) message;
            leaderCardController.getInstance().updateLeader(leaderCards);
        } else if (message.getClass() == StrongBox.class) {
            StrongBox strongBox = (StrongBox) message;
            punchboardController.getInstance().updateStrongBox(strongBox);
        } else if (message.getClass() == WarehouseDepots.class) {
            WarehouseDepots warehouseDepots = (WarehouseDepots) message;
            punchboardController.getInstance().updateWarehouseDepots(warehouseDepots);
        }
    }

    @Override
    public void printMessage(String message) {
        if (message.equals("\nGAME STARTED!\n")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Controller.getPrimarystage().setMaxHeight(788);
                    Controller.getPrimarystage().setMaxWidth(1005);
                    Controller.getPrimarystage().setScene(Controller.getPersonalpunchboard());
                    Controller.getPrimarystage().show();
                }
            });
        } else if (message.equals("You are the first player!") && Controller.getSingleormulti()==1) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ImageView x = (ImageView) Controller.getPersonalpunchboard().lookup("#calamaio_firstplayer");
                    x.setOpacity(1);
                }
            });
        } else if (message.equals("\nYOUR TURN STARTED!\n")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Label x = (Label) Controller.getPersonalpunchboard().lookup("#yourTurn");
                    Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                    x.setOpacity(1);
                    y.setOpacity(1);
                }
            });
        } else if (message.equals("\nYOUR TURN ENDED!\n")) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Label x = (Label) Controller.getPersonalpunchboard().lookup("#yourTurn");
                    Button y = (Button) Controller.getPersonalpunchboard().lookup("#endTurn");
                    x.setOpacity(0);
                    y.setOpacity(0);
                }
            });
        } else
            System.out.println(message);
    }

    public static int getInput() {
        return input;
    }

    public static void setInput(int input) {
        GUIUserInterface.input = input;
    }

    @Override
    public void printFailMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage pop = new Stage();
                pop.initModality(Modality.APPLICATION_MODAL);
                pop.setTitle("Attenzione - Errore!");
                pop.setMinWidth(450);
                pop.setMinHeight(150);

                Label label = new Label();
                label.setText(message);
                label.setStyle("-fx-font-size: 50 ");
                label.setStyle("-fx-font-weight: bold");
                label.setStyle("-fx-text-fill: red");
                label.setScaleX(1.5);
                label.setScaleY(1.5);

                VBox layout = new VBox();
                layout.getChildren().add(label);
                layout.setAlignment(Pos.CENTER);
                layout.setStyle("-fx-background-color: #F8EFD1");

                Scene scene = new Scene(layout);
                pop.setScene(scene);
                pop.showAndWait();
            }
        });
    }
}
