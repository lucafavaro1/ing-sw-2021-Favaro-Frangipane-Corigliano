package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.AddProductionEvent;
import it.polimi.ingsw.common.Events.EndTurnEvent;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Player.*;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeSet;

/**
 * Punchboard controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */

public class PunchboardController extends Controller {
    private boolean leaderFirstTime = true;

    public boolean isLeaderFirstTime() {
        return leaderFirstTime;
    }

    public void setLeaderFirstTime(boolean leaderFirstTime) {
        this.leaderFirstTime = leaderFirstTime;
    }

    @FXML // LISTA PLAYER PER VEDERE PLANCE
    public MenuButton playerList;

    @FXML   // LEV 1 DEVCARD
    public ImageView devCardLev1SX;
    public ImageView devCardLev1MID;
    public ImageView devCardLev1DX;
    @FXML   // LEV 2 DEVCARD
    public ImageView devCardLev2SX;
    public ImageView devCardLev2MID;
    public ImageView devCardLev2DX;
    @FXML   // LEV 3 DEVCARD
    public ImageView devCardLev3SX;
    public ImageView devCardLev3MID;
    public ImageView devCardLev3DX;
    @FXML   // BONUS POINTS FAITHTRACK
    public ImageView bonusPointsFaith1;
    public ImageView bonusPointsFaith2;
    public ImageView bonusPointsFaith3;
    @FXML   // IS FIRST PLAYER? se non lo è metti opacity 0
    public ImageView calamaio_firstplayer;
    @FXML   // RISORSE DEPOSITO
    public ImageView res1slot1;
    public ImageView res1slot2;
    public ImageView res2slot2;
    public ImageView res1slot3;
    public ImageView res2slot3;
    public ImageView res3slot3;
    @FXML   // RISORSE FORZIERE
    public Label numCoin;
    public Label numStone;
    public Label numServant;
    public Label numShield;
    @FXML   // FAITHTRACK
    public ImageView ft0;
    public ImageView ft1;
    public ImageView ft2;
    public ImageView ft3;
    public ImageView ft4;
    public ImageView ft5;
    public ImageView ft6;
    public ImageView ft7;
    public ImageView ft8;
    public ImageView ft9;
    public ImageView ft10;
    public ImageView ft11;
    public ImageView ft12;
    public ImageView ft13;
    public ImageView ft14;
    public ImageView ft15;
    public ImageView ft16;
    public ImageView ft17;
    public ImageView ft18;
    public ImageView ft19;
    public ImageView ft20;
    public ImageView ft21;
    public ImageView ft22;
    public ImageView ft23;
    public ImageView ft24;
    public MenuButton playerlist;
    // only when you are looking somebody else punchboard
    public Label watching;
    public Button endTurn;
    public ImageView segnalini_azione;
    public Button someoneCards;
    public Label yourNickname;


    public Image faithImage = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/fede.png"));
    public Image blankImage = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/blank.png"));
    public Image backAction = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/retro_cerchi.png"));

    private TreeSet<DevelopmentCard> tree = new TreeSet<>();

    private static PunchboardController instance;

    /**
     * Method implementing the singleton for the controller
     *
     * @return the unique instance
     */
    public static PunchboardController getInstance() {
        if (instance == null)
            instance = new PunchboardController();
        return instance;
    }

    /**
     * Go to market tray scene
     *
     * @param mouseEvent click on Market Tray button
     */
    public void toMarketTray(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getMarkettray());
        getPrimarystage().show();
    }

    /**
     * Go to Leader Card scene
     *
     * @param mouseEvent click on Leader Card button
     */
    public void toOwnLeaderCard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getLeadercards());
        getPrimarystage().show();
    }

    /**
     * Go to development card board scene
     *
     * @param mouseEvent click on Dc Board button
     */
    public void toDcBoard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getDcboard());
        getPrimarystage().show();

        // NEL CASO DI PRIMA SCELTA DELLE LEADER PRIMA INIZIO PARTITA NON HO IL MODEL
        ImageView leader1 = (ImageView) getLeadercards().lookup("#leadercard1");
        ImageView leader2 = (ImageView) getLeadercards().lookup("#leadercard2");
        if (leader1.getImage() == null && leader2.getImage() == null && leaderFirstTime) {
            PunchboardController.getInstance().setLeaderFirstTime(false);
            return;
        }
        updateTotalResources(getDcboard());
    }

    /**
     * Go to production scene (add and than activate productions)
     *
     * @param mouseEvent click on Productions button
     */
    public void toProductions(MouseEvent mouseEvent) throws IOException {

        getCmb().sendEvent(new AddProductionEvent());
        getPrimarystage().setScene(getProductions());

        updateTotalResources(getProductions());
    }

    /**
     * Send end turn event by clicking on end turn button
     *
     * @param mouseEvent click on End Turn button (top right of the screen)
     */
    public void endturn(MouseEvent mouseEvent) {
        getCmb().sendEvent(new EndTurnEvent());
    }

    /**
     * Update your faithtrack position and bonus points by receiving the faithtrack object from the server
     *
     * @param ft       the faithtrack object
     * @param personal 1 if you want to update your personal, 0 if want to update the currentscene
     */
    public synchronized void updateFaith(FaithTrack ft, boolean personal) {
        int index = 0;
        // Arraylist che contine tutte le posizioni del faithtrack consecutive
        ArrayList<ImageView> faithTrackElems = new ArrayList<>();

        Scene x = null;
        if (personal)
            x = getPersonalpunchboard();
        else
            x = getPrimarystage().getScene();

        while (index <= 24 && faithTrackElems.size() != 25) {
            ImageView im = (ImageView) x.lookup("#ft".concat(String.valueOf(index)));
            faithTrackElems.add(index, im);
            index++;
        }
        Image bonus2 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_giallo_2.PNG"));
        Image notbonus2 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_giallo.png"));
        Image bonus3 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_arancione_3.PNG"));
        Image notbonus3 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_arancione.png"));
        Image bonus4 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_rosso_4.PNG"));
        Image notbonus4 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_rosso.png"));


        int indice = 0;
        while (indice < faithTrackElems.size()) {
            ImageView im = faithTrackElems.get(indice);
            if (ft.getTrackPos() == indice) {
                im.setImage(faithImage);
            } else {
                if (indice == 0) im.setImage(blankImage);
                else im.setImage(null);
            }
            indice++;
        }

        // primo bonus +2
        ImageView im = (ImageView) x.lookup("#bonusPointsFaith1");

        if (ft.getBonusPoints()[0] == 0 && !ft.getSecAsFirst()[0]) {
            im.setImage(null);
        } else if (ft.getBonusPoints()[0] == 0 && ft.getSecAsFirst()[0]) {
            im.setImage(notbonus2);
        } else if (ft.getBonusPoints()[0] == 2 && !ft.getSecAsFirst()[0]) {
            im.setImage(bonus2);
        }
        // secondo bonus +3
        im = (ImageView) x.lookup("#bonusPointsFaith2");

        if (ft.getBonusPoints()[1] == 0 && !ft.getSecAsFirst()[1]) {
            im.setImage(null);
        } else if (ft.getBonusPoints()[1] == 0 && ft.getSecAsFirst()[1]) {
            im.setImage(notbonus3);
        } else if (ft.getBonusPoints()[1] == 3 && !ft.getSecAsFirst()[1]) {
            im.setImage(bonus3);
        }
        // terzo bonus +4
        im = (ImageView) x.lookup("#bonusPointsFaith3");

        if (ft.getBonusPoints()[2] == 0 && !ft.getSecAsFirst()[2]) {
            im.setImage(null);
        } else if (ft.getBonusPoints()[2] == 0 && ft.getSecAsFirst()[2]) {
            im.setImage(notbonus4);
        } else if (ft.getBonusPoints()[2] == 4 && !ft.getSecAsFirst()[2]) {
            im.setImage(bonus4);
        }


    }

    /**
     * Update your strongbox view by receiving the strongbox object from the server
     *
     * @param strongBox the strongbox object
     * @param personal  1 if you want to update your personal, 0 if want to update the currentscene
     */
    public synchronized void updateStrongBox(StrongBox strongBox, boolean personal) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene x = null;
                if (personal)
                    x = getPersonalpunchboard();
                else
                    x = getPrimarystage().getScene();

                Label coin = (Label) x.lookup("#numCoin");
                coin.setText("" + strongBox.getRes(Res_Enum.COIN));
                Label servant = (Label) x.lookup("#numServant");
                servant.setText("" + strongBox.getRes(Res_Enum.SERVANT));
                Label shield = (Label) x.lookup("#numShield");
                shield.setText("" + strongBox.getRes(Res_Enum.SHIELD));
                Label stone = (Label) x.lookup("#numStone");
                stone.setText("" + strongBox.getRes(Res_Enum.STONE));
            }
        });

    }

    /**
     * Update your warehousedepots view by receiving the warehouse object from the server
     *
     * @param warehouseDepots the warehousedepots object
     * @param personal        1 if you want to update your personal, 0 if want to update the currentscene
     */
    public synchronized void updateWarehouseDepots(WarehouseDepots warehouseDepots, boolean personal) {
        Scene x = null;
        if (personal)
            x = getPersonalpunchboard();
        else
            x = getPrimarystage().getScene();

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j<=i; j++) {
                ImageView im = (ImageView) x.lookup("#res".concat(Integer.toString(j+1))
                        .concat("slot").concat(Integer.toString(i+1)));
                try {
                    Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(i+1).get(j))));
                    im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                    im.setImage(null);
                }
            }
        }
    }

    /**
     * Update your personal development card board view by receiving the development card board object from the server
     *
     * @param board    the strongbox object
     * @param personal 1 if you want to update your personal, 0 if want to update the currentscene
     */

    public void updateDCPersonalBoard(DcPersonalBoard board, boolean personal) {
        int index = 0;
        ArrayList<DevelopmentCard> list = new ArrayList<>();
        while (index <= 2) {
            tree = board.getSlots().get(index);
            populateList(list, tree);
            //System.out.println("LIST SIZE: "+ (list.size()));;
            populateSlot(index, list, personal);
            list.clear();
            index++;
        }
    }

    /**
     * Update the action card (singleplayer) display in view by receiving the strongbox object from the server
     * The last action card picked up at the end of the turn is displayed for 3 seconds
     *
     * @param actionCard the action card object
     */

    public void updateAction(ActionCard actionCard) {
        ImageView im = (ImageView) getPersonalpunchboard().lookup("#segnalini_azione");
        Image img = new Image(getClass().getResourceAsStream(Controller.actionToUrl(actionCard)));
        im.setImage(img);
        PauseTransition pause = new PauseTransition(Duration.seconds(3));   // how long display the action card
        pause.setOnFinished(e -> im.setImage(backAction));
        pause.play();

    }

    /**
     * Methods used to populate one slot of the dc personal board from an array of development card
     *
     * @param slot     the slot (0 = left, 1 = mid, 2 = right)
     * @param list     the list of development card
     * @param personal 1 if you want to update your personal, 0 if want to update the currentscene
     */
    public void populateSlot(int slot, ArrayList<DevelopmentCard> list, boolean personal) {
        ImageView im;
        Image img;

        Scene x = null;
        if (personal)
            x = getPersonalpunchboard();
        else
            x = getPrimarystage().getScene();

        int count = 0;
        while (count < list.size()) {
            if (slot == 0 && list.get(count) != null) {
                im = (ImageView) x.lookup("#devCardLev".concat(String.valueOf(count + 1)).concat("SX"));
                img = new Image(PunchboardController.class.getResourceAsStream(devCardToUrl(list.get(count))));
                im.setImage(img);
            } else if (slot == 1 && list.get(count) != null) {
                im = (ImageView) x.lookup("#devCardLev".concat(String.valueOf(count + 1)).concat("MID"));
                img = new Image(PunchboardController.class.getResourceAsStream(devCardToUrl(list.get(count))));
                im.setImage(img);
            } else if (slot == 2 && list.get(count) != null) {
                im = (ImageView) x.lookup("#devCardLev".concat(String.valueOf(count + 1)).concat("DX"));
                img = new Image(PunchboardController.class.getResourceAsStream(devCardToUrl(list.get(count))));
                im.setImage(img);
            }
            count++;
        }

    }

    /**
     * Method used to convert a tree of development cards into an arraylist
     *
     * @param list a list in which you put the cards extracted by the tree
     * @param tree the tree that contains the current development card board
     */
    public void populateList(ArrayList<DevelopmentCard> list, TreeSet<DevelopmentCard> tree) {
        int count = 0;
        while (count <= tree.size()) {
            list.add(tree.pollLast());
            count++;
        }
    }

    /**
     * Method used to look other players punchboard.
     * In case of singleplayer is possible to see the faìthtrack of Lorenzo, nothing else.
     * In multiplayer mode is possible to see all information regarding his punchboard, if a leadercard is activated
     * will be showed, otherwise you will see the back of the card.
     *
     * @param mouseEvent click on one of player usernames in the top left list
     */
    public void lookforplayers(MouseEvent mouseEvent) {
        Map<String, Player> allusers = UserInterface.getInstance().getPlayers();
        MenuButton list = (MenuButton) getPersonalpunchboard().lookup("#playerList");
        list.getItems().clear();
        for (String key : allusers.keySet()) {
            MenuItem item = null;

            if (allusers.get(key).getNickname().equals(UserInterface.getInstance().getMyNickname()))
                continue;
            else
                item = new MenuItem(allusers.get(key).getNickname());

            list.getItems().add(item);
            item.setOnAction(e -> {
                try {
                    loadScene("OthersPunchboard.fxml");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                if (allusers.get(key).getNickname().equals("Lorenzo (CPU)")) {
                    CPUPlayer player = (CPUPlayer) allusers.get(key);
                    Pane total = (Pane) getPrimarystage().getScene().lookup("#totalpane");
                    Button leader = (Button) getPrimarystage().getScene().lookup("#someoneCards");
                    // if cpu player doesnt make sense having the leader card window
                    total.getChildren().remove(leader);
                    Label watch = (Label) getPrimarystage().getScene().lookup("#watching");
                    watch.setText("You are watching " + player.getNickname() + " punchboard");
                    // update only the faithtrack in case of singleplayer
                    updateFaith(player.getFaithTrack(), false);
                } else {
                    HumanPlayer player = (HumanPlayer) allusers.get(key);
                    // checking if is the first player, if yes display the calamaio
                    if (player.isFirstPlayer()) {
                        ImageView x = (ImageView) getPrimarystage().getScene().lookup("#calamaio_firstplayer");
                        x.setOpacity(1);
                    }
                    Label watch = (Label) getPrimarystage().getScene().lookup("#watching");
                    watch.setText("You are watching " + player.getNickname() + " punchboard");
                    // update all his personal objects on the board
                    updateFaith(player.getFaithTrack(), false);
                    updateDCPersonalBoard(player.getDevelopmentBoard(), false);
                    updateStrongBox(player.getStrongBox(), false);
                    updateWarehouseDepots(player.getWarehouseDepots(), false);
                    // if you wanna see the leader cards on another player
                    Button someoneCards = (Button) getPrimarystage().getScene().lookup("#someoneCards");

                    someoneCards.setOnMouseClicked(p -> {
                        Stage pop = new Stage();
                        pop.initModality(Modality.APPLICATION_MODAL);
                        pop.setTitle("Watching leader cards");

                        FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/OthersLeaderCard.fxml")));
                        Parent root = null;
                        Scene leader = null;
                        try {
                            root = (Parent) loader.load();
                            leader = new Scene(root);
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        pop.setScene(leader);
                        Label watching = (Label) pop.getScene().lookup("#watching");
                        watching.setText("You are watching " + player.getNickname() + " leader cards");
                        LeaderCardController.getInstance().updateLeader(player.getLeaderCards(), false, pop);
                        pop.show();
                    });
                }
            });
        }
    }


    /**
     * Event when you are looking someone else punchboard and wanna come back to the personal one
     * @param mouseEvent click on back to punchboard button
     */
    public void backtopunchboard(MouseEvent mouseEvent) {
        getPrimarystage().setScene(getPersonalpunchboard());
        getPrimarystage().show();
    }

    public void updateTotalResources(Scene whereUpdate) {
        Label coin = (Label) whereUpdate.lookup("#numCoin");
        coin.setText("" + UserInterface.getInstance().getMyPlayer().
                getTotalResources().get(Res_Enum.COIN));
        Label servant = (Label) whereUpdate.lookup("#numServant");
        servant.setText("" + UserInterface.getInstance().getMyPlayer().
                getTotalResources().get(Res_Enum.SERVANT));
        Label shield = (Label) whereUpdate.lookup("#numShield");
        shield.setText("" + UserInterface.getInstance().getMyPlayer().
                getTotalResources().get(Res_Enum.SHIELD));
        Label stone = (Label) whereUpdate.lookup("#numStone");
        stone.setText("" + UserInterface.getInstance().getMyPlayer().
                getTotalResources().get(Res_Enum.STONE));
    }


}

