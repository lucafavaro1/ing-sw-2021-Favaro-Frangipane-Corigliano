package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.common.Events.ActivateProductionEvent;
import it.polimi.ingsw.common.Events.AddProductionEvent;
import it.polimi.ingsw.common.Events.Discard;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  * Class representing the gui user interface, extends the abstract UserInterface (see that for methods javadoc)
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
        if (toBeChosen.get(0).getClass() == LeaderCard.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      ArrayList<LeaderCard> leaderCards = (ArrayList<LeaderCard>) toBeChosen;
                                      Parent root = null;
                                      Stage pop = new Stage();
                                      pop.setTitle("Choosing Leader Card");

                                      FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/ChooseLeaderCard.fxml")));
                                      try {
                                          root = (Parent) loader.load();
                                      } catch (IOException e) {
                                          System.err.println("Loading error");
                                      }

                                      Scene leaderchoose = new Scene(root);

                                      // set delle 4 carte leader
                                      for(int i=0; i<leaderCards.size(); i++) {
                                          if(leaderCards.size() == 3) {
                                              Label pickCards = (Label) leaderchoose.lookup("#pickCards");
                                              pickCards.setText("Pick 1 more Leader card");
                                          }
                                          ImageView im = (ImageView) leaderchoose.lookup("#leadercard".concat(Integer.toString(i+1)));
                                          Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(i))));
                                          im.setImage(img);
                                      }

                                      pop.setScene(leaderchoose);
                                      pop.showAndWait();
                                  }
                              }
            );

        else if (toBeChosen.get(0).getClass() == Res_Enum.class) {
            // CHOOSE RESOURCES FOR ? PRODUCTIONS {QUESTION: 2 -> QUESTION: 1}
            if (message.equals("Choose the resource to spend") || message.equals("Choose the resource to take")) {
                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          Parent root = null;
                                          HBox layout = new HBox(175);
                                          Stage pop = new Stage();
                                          pop.initModality(Modality.APPLICATION_MODAL);
                                          pop.setMinWidth(700);
                                          pop.setMinHeight(200);
                                          pop.setTitle(message);

                                          layout.setStyle("-fx-background-color: #F8EFD1");

                                          for (int i = 0; i < toBeChosen.size(); i++) {
                                              ImageView img = new ImageView();
                                              Image coin = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.COIN)));
                                              Image shield = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.SHIELD)));
                                              Image stone = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.STONE)));
                                              Image servant = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.SERVANT)));
                                              Button button = new Button();
                                              button.setMaxSize(5,5);
                                              button.setMinSize(5,5);
                                              int x = i;
                                              button.setOnAction(e -> {
                                                  choose(x + 1);
                                                  pop.close();
                                              });
                                              button.setScaleX(0.5);
                                              button.setScaleY(0.5);
                                              switch (toBeChosen.get(i).toString()) {
                                                  case "COIN":
                                                      img.setImage(coin);
                                                      button.setGraphic(img);
                                                      break;
                                                  case "STONE":
                                                      img.setImage(stone);
                                                      button.setGraphic(img);
                                                      break;
                                                  case "SERVANT":
                                                      img.setImage(servant);
                                                      button.setGraphic(img);
                                                      break;
                                                  case "SHIELD":
                                                      img.setImage(shield);
                                                      button.setGraphic(img);
                                                      break;
                                              }
                                              layout.getChildren().add(button);
                                              layout.setAlignment(Pos.CENTER);
                                          }

                                          Scene scene = new Scene(layout);
                                          pop.setScene(scene);
                                          pop.showAndWait();
                                      }
                                  }
                );
            } else { // CHOOSE BONUS RESOURCES AT THE BEGINNING OF THE GAME
                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          Parent root = null;
                                          Stage pop = new Stage();
                                          pop.setTitle("Choose bonus resources");

                                          FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/ChooseResources.fxml")));
                                          try {
                                              root = (Parent) loader.load();
                                          } catch (IOException e) {
                                              System.err.println("Loading error");
                                          }

                                          Scene reschoose = new Scene(root);

                                          pop.setScene(reschoose);
                                          pop.showAndWait();
                                      }
                                  }
                );
            }
        }
            // CHOOSE BETWEEN DISCARD / WAREHOUSE / LEADER SLOT
        else if (toBeChosen.get(0).getClass() == Discard.class
                || toBeChosen.get(0).getClass() == WarehouseDepots.class
                || toBeChosen.get(0).getClass() == StrongBox.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      Parent root = null;
                                      Stage pop = new Stage();
                                      pop.initModality(Modality.APPLICATION_MODAL);
                                      pop.setMinWidth(550);
                                      pop.setMinHeight(200);

                                      pop.setTitle(message);
                                      HBox layout = new HBox(toBeChosen.size());
                                      layout.setStyle("-fx-background-color: #F8EFD1");
                                      layout.setSpacing(100);

                                      for (int i = 0; i < toBeChosen.size(); i++) {
                                          Button button = new Button(toBeChosen.get(i).getClass().getSimpleName());
                                          int x = i;
                                          button.setOnAction(e -> {
                                              choose(x + 1);
                                              pop.close();
                                          });
                                          button.setScaleX(1.8);
                                          button.setScaleY(1.8);
                                          layout.getChildren().add(button);
                                          layout.setAlignment(Pos.CENTER);
                                      }

                                      Scene scene = new Scene(layout);
                                      pop.setScene(scene);
                                      pop.showAndWait();
                                  }
                              }
            );
            // WHERE TO PUT THE DEV CARD AFTER BUYING IT
        else if (toBeChosen.get(0).getClass() == String.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      Parent root = null;
                                      Stage pop = new Stage();
                                      pop.initModality(Modality.APPLICATION_MODAL);
                                      pop.setMinWidth(550);
                                      pop.setMinHeight(200);

                                      pop.setTitle(message);
                                      HBox layout = new HBox(toBeChosen.size());
                                      layout.setStyle("-fx-background-color: #F8EFD1");
                                      layout.setSpacing(100);

                                      for (int i = 0; i < toBeChosen.size(); i++) {
                                          Button button = new Button(devcheck(toBeChosen.get(i).toString()));
                                          int x = i;
                                          button.setOnAction(e -> {
                                              choose(x + 1);
                                              pop.close();
                                          });
                                          button.setScaleX(1.8);
                                          button.setScaleY(1.8);
                                          layout.getChildren().add(button);
                                          layout.setAlignment(Pos.CENTER);
                                      }

                                      Scene scene = new Scene(layout);
                                      pop.setScene(scene);
                                      primary.setScene(personalpunchboard);
                                      pop.showAndWait();
                                  }
                              }
            );
        else {  // PRODUCTIONS
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    VBox left = null;
                    VBox right = null;

                    left = (VBox) productions.lookup("#addProduction");
                    left.setSpacing(50);
                    left.setAlignment(Pos.CENTER);
                    right = (VBox) productions.lookup("#activateProduction") ;
                    right.setSpacing(50);
                    right.setAlignment(Pos.CENTER);

                    Button topunchboard = (Button) productions.lookup("#topunchboard");

                    topunchboard.setOnMouseClicked(e -> {
                        VBox left1 = (VBox) productions.lookup("#addProduction");
                        if(left1.getChildren().size()!=0)
                            choose(toBeChosen.size());
                        left1.getChildren().clear();
                        primary.setScene(personalpunchboard);
                        primary.show();
                    });

                    Button activate = (Button) productions.lookup("#activate");

                    activate.setOnMouseClicked(e -> {
                        VBox right1 = (VBox) productions.lookup("#activateProduction");
                        VBox left1 = (VBox) productions.lookup("#addProduction");
                        if(left1.getChildren().size()!=0)
                            choose(toBeChosen.size());
                        primary.setScene(personalpunchboard);
                        primary.show();
                        Controller.getCmb().sendEvent(new ActivateProductionEvent());
                        right1.getChildren().clear();
                        left1.getChildren().clear();
                    });

                    for (int i = 0; i < toBeChosen.size()-1; i++) {
                        Button button = new Button(check(toBeChosen.get(i).toString()));

                        int x = i;
                        button.setOnAction(e -> {
                            choose(x + 1);
                        });
                        button.setScaleX(1.5);
                        button.setScaleY(1.5);
                        button.setMinWidth(150);
                        left.getChildren().add(button);
                    }

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

    /**
     * Method to convert the text of the basic production into a simple string
     * @param string the string received (for add production list)
     * @return "Base Production" string format
     */
    public String check(String string) {
        if (string.equals("{QUESTION=2} -> {QUESTION=1}"))
            return "Base Production";
        else
            return string;
    }

    public String devcheck(String string) {
        switch (string) {
            case "1":
                return "LEFT";
            case "2":
                return "MIDDLE";
            case "3":
                return "RIGHT";
            default:
                return string;
        }
    }
}
