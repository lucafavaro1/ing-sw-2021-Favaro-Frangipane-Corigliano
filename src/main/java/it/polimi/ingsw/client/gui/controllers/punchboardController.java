package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUI;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.*;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

/**
 * DcBoard controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */

public class punchboardController extends Controller {
        public boolean isfirst=false;
        @FXML // LISTA PLAYER PER VEDERE PLANCE
        public MenuButton playerList;

        @FXML   // LEV 1 DEVCARD
        public ImageView devCardLev1SX;
        public ImageView devCardLevl1MID;
        public ImageView devCardLev1DX;
        @FXML   // LEV 2 DEVCARD
        public ImageView devCardLev2SX;
        public ImageView devCardLevl2MID;
        public ImageView devCardLev2DX;
        @FXML   // LEV 3 DEVCARD
        public ImageView devCardLev3SX;
        public ImageView devCardLevl3MID;
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
        // Arraylist che contine tutte le posizioni del faithtrack consecutive
        private static ArrayList<ImageView> faithTrackElems= new ArrayList<>();
        public Button endTurn;

        public ImageView segnalini_azione;
        public Image faithImage = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/fede.png"));
        public Image blankImage = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/blank.png"));
        public Image backAction = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/retro_cerchi.png"));

        private TreeSet<DevelopmentCard> tree = new TreeSet<>();

        private static punchboardController instance;

        public static punchboardController getInstance() {
                if(instance == null)
                        instance = new punchboardController();
                return instance;
        }

        public void toMarketTray(MouseEvent mouseEvent) {
            getPrimarystage().setScene(getMarkettray());
            getPrimarystage().show();
        }

        public void toOwnLeaderCard(MouseEvent mouseEvent) {
                getPrimarystage().setScene(getLeadercards());
                getPrimarystage().show();
        }

        public void toDcBoard(MouseEvent mouseEvent) {
                getPrimarystage().setScene(getDcboard());
                getPrimarystage().show();
                Label coin = (Label)getDcboard().lookup("#numCoin");
                coin.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.COIN));
                Label servant = (Label)getDcboard().lookup("#numServant");
                servant.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.SERVANT));
                Label shield = (Label)getDcboard().lookup("#numShield");
                shield.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.SHIELD));
                Label stone = (Label)getDcboard().lookup("#numStone");
                stone.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.STONE));
        }

        public void toProductions(MouseEvent mouseEvent) throws IOException {
                getCmb().sendEvent(new AddProductionEvent());
                loadScene("productions.fxml");
                Label coin = (Label)getPrimarystage().getScene().lookup("#numCoin");
                coin.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.COIN));
                Label servant = (Label)getPrimarystage().getScene().lookup("#numServant");
                servant.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.SERVANT));
                Label shield = (Label)getPrimarystage().getScene().lookup("#numShield");
                shield.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.SHIELD));
                Label stone = (Label)getPrimarystage().getScene().lookup("#numStone");
                stone.setText(""+ UserInterface.getInstance().getPlayers().get(Controller.getMynickname()).
                        getTotalResources().get(Res_Enum.STONE));
        }

        public void endturn(MouseEvent mouseEvent) {
                getCmb().sendEvent(new EndTurnEvent());
        }

        public synchronized void updateFaith(FaithTrack ft){          //Aggiornamento del FaithTrack
                int index=0;
                while (index <= 24 && faithTrackElems.size()!=25) {
                        ImageView im = (ImageView) getPersonalpunchboard().lookup("#ft".concat(String.valueOf(index)));
                        faithTrackElems.add(index, im);
                        index++;
                }
                Image bonus2 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_giallo_2.PNG"));
                Image notbonus2 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_giallo.png"));
                Image bonus3 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_arancione_3.PNG"));
                Image notbonus3 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_arancione.png"));
                Image bonus4 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_rosso_4.PNG"));
                Image notbonus4 = new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/quadrato_rosso.png"));


                int indice =0;
                while(indice<faithTrackElems.size()){
                        ImageView im=faithTrackElems.get(indice);
                        if(ft.getTrackPos()==indice){
                                im.setImage(faithImage);
                        }
                        else{
                                if(indice==0) im.setImage(blankImage);
                                else im.setImage(null);
                        }
                        indice++;
                }

                // primo bonus +2
                if(ft.getBonusPoints()[0] == 0 && !ft.getSecAsFirst()[0]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith1");
                        im.setImage(null);
                }
                else if(ft.getBonusPoints()[0] == 0 && ft.getSecAsFirst()[0]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith1");
                        im.setImage(notbonus2);
                }
                else if(ft.getBonusPoints()[0] == 2 && !ft.getSecAsFirst()[0]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith1");
                        im.setImage(bonus2);
                }
                // secondo bonus +3
                if(ft.getBonusPoints()[1] == 0 && !ft.getSecAsFirst()[1]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith2");
                        im.setImage(null);
                }
                else if(ft.getBonusPoints()[1] == 0 && ft.getSecAsFirst()[1]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith2");
                        im.setImage(notbonus3);
                }
                else if(ft.getBonusPoints()[1] == 3 && !ft.getSecAsFirst()[1]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith2");
                        im.setImage(bonus3);
                }
                // terzo bonus +4
                if(ft.getBonusPoints()[2] == 0 && !ft.getSecAsFirst()[2]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith3");
                        im.setImage(null);
                }
                else if(ft.getBonusPoints()[2] == 0 && ft.getSecAsFirst()[2]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith3");
                        im.setImage(notbonus4);
                }
                else if(ft.getBonusPoints()[2] == 4 && !ft.getSecAsFirst()[2]) {
                        ImageView im  = (ImageView)getPersonalpunchboard().lookup("#bonusPointsFaith3");
                        im.setImage(bonus4);
                }


        }

        public synchronized void updateStrongBox(StrongBox strongBox) {
                Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                                Label coin = (Label)getPersonalpunchboard().lookup("#numCoin");
                                coin.setText(""+strongBox.getRes(Res_Enum.COIN));
                                Label servant = (Label)getPersonalpunchboard().lookup("#numServant");
                                servant.setText(""+strongBox.getRes(Res_Enum.SERVANT));
                                Label shield = (Label)getPersonalpunchboard().lookup("#numShield");
                                shield.setText(""+strongBox.getRes(Res_Enum.SHIELD));
                                Label stone = (Label)getPersonalpunchboard().lookup("#numStone");
                                stone.setText(""+strongBox.getRes(Res_Enum.STONE));
                        }
                });

        }

        public synchronized void updateWarehouseDepots(WarehouseDepots warehouseDepots) {
                ImageView im = (ImageView) getPersonalpunchboard().lookup("#res1slot1");
                try {
                        Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(1).get(0))));
                        im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                        im.setImage(null);
                }

                im = (ImageView) getPersonalpunchboard().lookup("#res1slot2");
                try {
                        Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(2).get(0))));
                        im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                        im.setImage(null);
                }

                im = (ImageView) getPersonalpunchboard().lookup("#res2slot2");
                try {
                        Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(2).get(1))));
                        im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                        im.setImage(null);
                }

                im = (ImageView) getPersonalpunchboard().lookup("#res1slot3");
                try {
                        Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(3).get(0))));
                        im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                        im.setImage(null);
                }

                im = (ImageView) getPersonalpunchboard().lookup("#res2slot3");
                try {
                        Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(3).get(1))));
                        im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                        im.setImage(null);
                }

                im = (ImageView) getPersonalpunchboard().lookup("#res3slot3");
                try {
                        Image img = new Image(getClass().getResourceAsStream(Controller.resourceToUrl(warehouseDepots.get_dp(3).get(2))));
                        im.setImage(img);
                } catch (IndexOutOfBoundsException e) {
                        im.setImage(null);
                }

        }


        public void updateDCPersonalBoard(DcPersonalBoard board) throws BadSlotNumberException {
                int index=0;
                ArrayList<DevelopmentCard> list= new ArrayList<>();
                while(index<=2){
                        tree=board.getSlots().get(index);
                        populateList(list, tree);
                        //System.out.println("LIST SIZE: "+ (list.size()));;
                        populateSlot(index, list);
                        list.clear();
                        index++;
                }


        }

        public void updateAction(ActionCard actionCard) {
                ImageView im = (ImageView) getPersonalpunchboard().lookup("#segnalini_azione");
                Image img = new Image(getClass().getResourceAsStream(Controller.actionToUrl(actionCard)));
                im.setImage(img);
                PauseTransition pause = new PauseTransition(Duration.seconds(3));
                pause.setOnFinished(e -> im.setImage(backAction));
                pause.play();

        }

        public void populateSlot(int slot, ArrayList<DevelopmentCard> list){
                ImageView im;
                Image img;
                int count=0;
                while(count< list.size()){
                        if(slot==0 && list.get(count)!=null){
                                im= (ImageView) getPersonalpunchboard().lookup("#devCardLev".concat(String.valueOf(count+1)).concat("SX"));
                                img= new Image(punchboardController.class.getResourceAsStream(devCardToUrl(list.get(count))));
                                im.setImage(img);
                        }
                        else if(slot==1 && list.get(count)!=null){
                                im= (ImageView) getPersonalpunchboard().lookup("#devCardLev".concat(String.valueOf(count+1)).concat("MID"));
                                img= new Image(punchboardController.class.getResourceAsStream(devCardToUrl(list.get(count))));
                                im.setImage(img);
                        }
                        else if(slot==2 && list.get(count)!=null){
                                im= (ImageView) getPersonalpunchboard().lookup("#devCardLev".concat(String.valueOf(count+1)).concat("DX"));
                                img= new Image(punchboardController.class.getResourceAsStream(devCardToUrl(list.get(count))));
                                im.setImage(img);
                        }
                        count++;
                }

        }

        public void populateList(ArrayList<DevelopmentCard> list, TreeSet<DevelopmentCard>tree){
                int count =0;
                while(count<=tree.size()){
                        list.add(tree.pollLast());
                        count++;
                }
        }
}

