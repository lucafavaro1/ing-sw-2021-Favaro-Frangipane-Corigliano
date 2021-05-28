package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.controllers.*;
import it.polimi.ingsw.common.Events.ActivateProductionEvent;
import it.polimi.ingsw.common.Events.AddProductionEvent;
import it.polimi.ingsw.common.Events.Discard;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
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

    @Override
    public synchronized int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();
        String message = makePlayerChoose.getMessage();
        int chosen;
        // SCELTA DELLE 4 LEADER INIZIALI
        if (toBeChosen.get(0).getClass() == LeaderCard.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      ArrayList<LeaderCard> leaderCards = (ArrayList<LeaderCard>) toBeChosen;
                                      Parent root = null;
                                      Stage pop = new Stage();
                                      pop.setTitle("Choosing Leader Card");

                                      FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/chooseLeaderCard.fxml")));
                                      try {
                                          root = (Parent) loader.load();
                                      } catch (IOException e) {
                                          System.err.println("Loading error");
                                      }

                                      Scene leaderchoose = new Scene(root);
                                      // set carta 1
                                      ImageView im = (ImageView) leaderchoose.lookup("#leadercard1");
                                      Image img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(0))));
                                      im.setImage(img);
                                      // set carta 2
                                      im = (ImageView) leaderchoose.lookup("#leadercard2");
                                      img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(1))));
                                      im.setImage(img);
                                      // set carta 3
                                      im = (ImageView) leaderchoose.lookup("#leadercard3");
                                      img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(2))));
                                      im.setImage(img);
                                      // se esiste, set carta 4
                                      if (leaderCards.size() == 4) {
                                          im = (ImageView) leaderchoose.lookup("#leadercard4");
                                          img = new Image(getClass().getResourceAsStream(Controller.leaderToUrl(leaderCards.get(3))));
                                          im.setImage(img);
                                      }

                                      pop.setScene(leaderchoose);
                                      pop.showAndWait();
                                  }
                              }
            );

        else if (toBeChosen.get(0).getClass() == Res_Enum.class) {
            // SCELTA RISORSE PER PRODUZIONE {QUESTION: 2 -> QUESTION: 1}
            if (message.equals("Choose the resource to spend") || message.equals("Choose the resource to take")) {
                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          Parent root = null;
                                          Stage pop = new Stage();
                                          pop.initModality(Modality.APPLICATION_MODAL);
                                          pop.setMinWidth(600);
                                          pop.setMinHeight(200);

                                          pop.setTitle(message);
                                          HBox layout = new HBox(toBeChosen.size());
                                          layout.setStyle("-fx-background-color: #F8EFD1");
                                          layout.setSpacing(10);

                                          for (int i = 0; i < toBeChosen.size(); i++) {
                                              ImageView img = new ImageView();
                                              Image coin = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.COIN)));
                                              Image shield = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.SHIELD)));
                                              Image stone = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.STONE)));
                                              Image servant = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(Res_Enum.SERVANT)));
                                              Button button = new Button();
                                              button.setMaxHeight(10);
                                              button.setMaxWidth(10);
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

                                              /*
                                              Button button = new Button(toBeChosen.get(i).toString());
                                              int x = i;
                                              button.setOnAction(e -> {
                                                  choose(x + 1);
                                                  pop.close();
                                              });
                                              button.setScaleX(1.8);
                                              button.setScaleY(1.8);
                                              layout.getChildren().add(button);
                                              layout.setAlignment(Pos.CENTER);
                                               */
                                          }

                                          Scene scene = new Scene(layout);
                                          pop.setScene(scene);
                                          pop.showAndWait();
                                      }
                                  }
                );
            } else { // SCELTA DELLE RISORSE INIZIO PARTITA
                Platform.runLater(new Runnable() {
                                      @Override
                                      public void run() {
                                          Parent root = null;
                                          Stage pop = new Stage();
                                          pop.setTitle("Choose bonus resources");

                                          FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/chooseResources.fxml")));
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
            // SCELTA SE DISCARD / WAREHOUSE / LEADER SLOT
        else if (toBeChosen.get(0).getClass() == Discard.class
                || toBeChosen.get(0).getClass() == WarehouseDepots.class
                || toBeChosen.get(0).getClass() == StrongBox.class)
            Platform.runLater(new Runnable() {
                                  @Override
                                  public void run() {
                                      Parent root = null;
                                      Stage pop = new Stage();
                                      pop.initModality(Modality.APPLICATION_MODAL);
                                      pop.setMinWidth(450);
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
            // DOVE METTERE DEV CARD DOPO ACQUISTO
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
                                          Button button = new Button(toBeChosen.get(i).toString());
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
        else {  // PRODUZIONI
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    VBox mybox = null;

                    Scene productions = primary.getScene();

                    mybox = (VBox) productions.lookup("#addProduction");
                    mybox.setSpacing(100);

                    Button topunchboard = (Button) productions.lookup("#topunchboard");
                    topunchboard.setOnMouseClicked(e -> {
                        choose(toBeChosen.size());
                        primary.setScene(personalpunchboard);
                        primary.show();
                    });
                    Button activate = (Button) productions.lookup("#activate");
                    activate.setOnMouseClicked(e -> {
                        VBox prod = (VBox) productions.lookup("#addProduction");
                        if(prod.getChildren().size()!=0)
                            choose(toBeChosen.size());
                        Controller.getCmb().sendEvent(new ActivateProductionEvent());
                        primary.setScene(personalpunchboard);
                        primary.show();
                    });
                    for (int i = 0; i < toBeChosen.size()-1; i++) {
                        Button button = new Button(check(toBeChosen.get(i).toString()));
                        int x = i;
                        button.setOnAction(e -> {
                            choose(x + 1);
                        });
                        button.setScaleX(1.8);
                        button.setScaleY(1.8);
                        button.setMinWidth(200);
                        mybox.getChildren().add(button);
                        mybox.setAlignment(Pos.CENTER);
                    }

                }
            });
        }

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

    @Override
    public synchronized void choose(int chosen) {
        input = chosen - 1;
        System.out.println("[GUI] player choose: " + chosen);
        notifyAll();
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
            punchboardController.getInstance().updateFaith(faithTrack);
        } else if (message.getClass() == DcPersonalBoard.class) {
            DcPersonalBoard personalBoard = (DcPersonalBoard) message;
            try {
                punchboardController.getInstance().updateDCPersonalBoard(personalBoard);
            } catch (BadSlotNumberException e) {
                e.printStackTrace();
            }
        } else if (message.getClass() == ArrayList.class) {
            ArrayList<?> x = (ArrayList) message;
            if (x.get(0).getClass() == LeaderCard.class) {
                ArrayList<LeaderCard> leaderCards = (ArrayList<LeaderCard>) message;
                leaderCardController.getInstance().updateLeader(leaderCards);
            } else if (x.get(0).getClass() == Production.class) {
                ArrayList<Production> productions = (ArrayList<Production>) message;
                productionsController.getInstance().updateAddedProductions(productions);
            }

        } else if (message.getClass() == StrongBox.class) {
            StrongBox strongBox = (StrongBox) message;
            punchboardController.getInstance().updateStrongBox(strongBox);
        } else if (message.getClass() == WarehouseDepots.class) {
            WarehouseDepots warehouseDepots = (WarehouseDepots) message;
            punchboardController.getInstance().updateWarehouseDepots(warehouseDepots);
            marketTrayController.getInstance().updateWarehouseDepots(warehouseDepots);
        } else if (message.getClass() == ActionCard.class) {
            ActionCard actionCard = (ActionCard) message;
            punchboardController.getInstance().updateAction(actionCard);
        }
    }

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

    public String check(String string) {
        if (string.equals("{QUESTION: 2 } -> {QUESTION: 1}"))
            return "Base Production";
        else
            return string;
    }
}
