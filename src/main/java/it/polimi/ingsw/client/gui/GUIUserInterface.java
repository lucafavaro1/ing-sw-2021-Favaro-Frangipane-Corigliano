package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.client.gui.toBeChosen.*;
import it.polimi.ingsw.common.Events.AddProductionEvent;
import it.polimi.ingsw.common.Events.Discard;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the gui user interface, extends the abstract UserInterface (see that for methods javadoc)
 */
public class GUIUserInterface extends UserInterface {
    private static Scene personalpunchboard;
    private static Scene markettray;
    private static Scene dcboard;
    private static Scene leadercards;
    private static Scene productions;
    private static Stage primary;
    private static int input = -1;

    /**
     * Basic constructor linking the eventbroker of the client to his guiUserInterface
     * @param eventBroker the event broker
     */
    public GUIUserInterface(EventBroker eventBroker) {
        super(eventBroker);
        personalpunchboard = Controller.getPersonalpunchboard();
        markettray = Controller.getMarkettray();
        dcboard = Controller.getDcboard();
        leadercards = Controller.getLeadercards();
        productions = Controller.getProductions();
        primary = Controller.getPrimarystage();
    }

    /**
     * Method that deals with showing to the user the different options the player could choose
     *
     * @param makePlayerChoose the makePlayerChoose list received
     * @return the option chosen by the user (corresponding index from the list passed)
     */
    @Override
    public synchronized int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();
        String message = makePlayerChoose.getMessage();
        int chosen;

        // CHOOSING THE 4 LEADER CARDS AT THE BEGINNING
        if (toBeChosen.get(0).getClass() == LeaderCard.class) {
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      preparationLeader leader = new preparationLeader();
                                      leader.run(toBeChosen);
                                  }
            });
        } else if (toBeChosen.get(0).getClass() == Res_Enum.class) {
            // CHOOSE RESOURCES FOR ? PRODUCTIONS {QUESTION: 2 -> QUESTION: 1}
            if (message.equals("Choose the resource to spend") || message.equals("Choose the resource to take")) {
                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          resourcesTakeSpend resources = new resourcesTakeSpend();
                                          resources.run(toBeChosen, message);
                                      }
                                  }
                );
            } else { // CHOOSE BONUS RESOURCES AT THE BEGINNING OF THE GAME
                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          preparationBonusResources bonus = new preparationBonusResources();
                                          bonus.run();
                                      }
                                  }
                );
            }
        }
            // CHOOSE BETWEEN DISCARD / WAREHOUSE / LEADER SLOT
        else if (toBeChosen.get(0).getClass() == Discard.class
                || toBeChosen.get(0).getClass() == WarehouseDepots.class
                || toBeChosen.get(0).getClass() == StrongBox.class
                || toBeChosen.get(0).getClass() == PlusSlot.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      resourcesWherePut ask = new resourcesWherePut();
                                      ask.run(toBeChosen,message);
                                  }
                              }
            );
            // WHERE TO PUT THE DEV CARD AFTER BUYING IT
        else if (toBeChosen.get(0).getClass() == String.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                     devcardWherePut choose = new devcardWherePut();
                                     choose.run(toBeChosen, message);
                                  }
                              }
            );
        else {  // PRODUCTIONS
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    productionsSetup productions = new productionsSetup();
                    productions.run(toBeChosen,message);
                }
            });
        }

        // CHOOSE + ANSWER
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

    /**
     * Method used in gui to link buttons to choices
     * @param chosen the corresponding number of the choice
     */
    @Override
    public synchronized void choose(int chosen) {
        input = chosen - 1;
        System.out.println("[GUI] player choose: " + chosen);
        notifyAll();
    }

    /**
     * Method to update the graphical view of the game
     * @param message message to be shown by graphics
     */
    @Override
    public void printMessage(Object message) {
        if (message.getClass() == MarketTray.class) {
            MarketTray mymarket = (MarketTray) message;
            MarketTrayController.getInstance().conversion(mymarket);
        } else if (message.getClass() == DcBoard.class) {
            DcBoard totboard = (DcBoard) message;
            DcBoardController.getInstance().conversion(totboard);
        } else if (message.getClass() == FaithTrack.class) {
            FaithTrack faithTrack = (FaithTrack) message;
            PunchboardController.getInstance().updateFaith(faithTrack, true);
        } else if (message.getClass() == DcPersonalBoard.class) {
            DcPersonalBoard personalBoard = (DcPersonalBoard) message;
            PunchboardController.getInstance().updateDCPersonalBoard(personalBoard,true);
        } else if (message.getClass() == ArrayList.class) {
            ArrayList<?> x = (ArrayList) message;
            if (!x.isEmpty() && x.get(0).getClass() == LeaderCard.class) {
                ArrayList<LeaderCard> leaderCards = (ArrayList<LeaderCard>) message;
                LeaderCardController.getInstance().updateLeader(leaderCards, true, null);
            } else if (!x.isEmpty() && x.get(0).getClass() == Production.class) {
                ArrayList<Production> productions = (ArrayList<Production>) message;
                ProductionsController.getInstance().updateAddedProductions(productions);
            }
        } else if (message.getClass() == StrongBox.class) {
            StrongBox strongBox = (StrongBox) message;
            PunchboardController.getInstance().updateStrongBox(strongBox,true);
        } else if (message.getClass() == WarehouseDepots.class) {
            WarehouseDepots warehouseDepots = (WarehouseDepots) message;
            PunchboardController.getInstance().updateWarehouseDepots(warehouseDepots,true);
            MarketTrayController.getInstance().updateWarehouseDepots(warehouseDepots);
        } else if (message.getClass() == ActionCard.class) {
            ActionCard actionCard = (ActionCard) message;
            PunchboardController.getInstance().updateAction(actionCard);
        }
    }

    /**
     * Method to warn the user of a fail event by a pop up with an error message
     * @param message message of the problem occurred
     */
    @Override
    public void printFailMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage pop = new Stage();
                pop.initModality(Modality.APPLICATION_MODAL);
                pop.setTitle("Warning - Error!");
                pop.setMinWidth(550);
                pop.setMinHeight(150);

                Label label = new Label();
                label.setText(message);
                if(message.equals("Production requirements not satisfiable!")) {
                    VBox left = (VBox) productions.lookup("#addProduction");
                    left.getChildren().clear();
                    Controller.getCmb().sendEvent(new AddProductionEvent());
                }
                if((message.equals("Main action already completed in this turn!")
                        || message.equals("Can't complete this action, it's not your turn!"))
                        && Controller.getPrimarystage().getScene().equals(productions)) {
                    primary.setScene(personalpunchboard);
                }
                if(message.equals("No more productions available!"))
                    return;
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
