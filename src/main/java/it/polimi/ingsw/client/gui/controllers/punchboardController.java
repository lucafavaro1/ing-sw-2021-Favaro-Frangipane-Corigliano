package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.AddFaithEvent;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Player.FaithTrack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.*;

/**
 * DcBoard controller (singleton) for the GUI: graphical interaction method + conversion methods to apply view changes
 * received thanks to an event sent by the EventBroker
 */

public class punchboardController extends Controller {
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
        private static ArrayList<ImageView> faithTrackElems= new ArrayList<>();
        private TreeSet<DevelopmentCard> tree = new TreeSet<>();


        private Image faithImage;
        private Image blankImage;

        public Image getBlankImage() {
                return blankImage;
        }

        public void setBlankImage(Image blankImage) {
                this.blankImage = blankImage;
        }

        public Image getFaithImage() {
                return faithImage;
        }

        public void setFaithImage(Image faithImage) {
                this.faithImage = faithImage;
        }


        private static punchboardController instance;

        public static punchboardController getInstance() {
                if(instance == null)
                        instance = new punchboardController();
                return instance;
        }

        public punchboardController() {
                EventBroker eventBroker = new EventBroker();
                GUIUserInterface guiUserInterface = new GUIUserInterface(eventBroker);

        ClientController clientController = new ClientController(
                guiUserInterface,
                eventBroker,
                getClientSocket()
        );

        faithImage=new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/fede.png"));
        blankImage=new Image(getClass().getResourceAsStream("/GraphicsGUI/punchboard/blank.png"));


        setCmb(clientController.getClientMessageBroker());
        clientController.start();


        }

        //Image img = new Image(getClass().getResourceAsStream("/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png"));
        //devCardLev1SX.setImage(img);
        //devCardLev1SX.setImage(null);

        public void toMarketTray(MouseEvent mouseEvent) {
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            Scene x = getMarkettray();
            window.setScene(x);
            window.show();
        }

        public void toOwnLeaderCard(MouseEvent mouseEvent) {
                Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Scene x = getLeadercards();
                window.setScene(x);
                window.show();
        }

        public void toDcBoard(MouseEvent mouseEvent) {
                Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                Scene x = getDcboard();
                window.setScene(x);
                window.show();
        }

        public void updateFaith(FaithTrack ft){          //Aggiornamento del FaithTrack

                int index =0;
                while(index<faithTrackElems.size()){
                        ImageView im=faithTrackElems.get(index);
                        if(ft.getTrackPos()==index){
                                im.setImage(getFaithImage());
                        }
                        else{
                                if(index==0) im.setImage(getBlankImage());
                                else im.setImage(null);
                        }
                        index++;
                }

        }


        public static void populate(){    //riempie l'array di ImageView del FaithTrack
                int index=0;
                while(index <=24){
                        ImageView im= (ImageView) getPersonalpunchboard().lookup("#ft".concat(String.valueOf(index)));
                        faithTrackElems.add(index,im);
                        index++;
                }

        }


        public void updateDCPersonalBoard(DcPersonalBoard board) throws BadSlotNumberException {
                int index=0;
                ArrayList<DevelopmentCard> list= new ArrayList<>();
                while(index<=2){
                        tree=board.getSlots().get(index);
                        populateList(list, tree);
                        System.out.println("LIST SIZE: "+ (list.size()));;
                        populateSlot(index, list);
                        list.removeAll(list);
                        index++;
                }


        }

        public void populateSlot(int slot, ArrayList<DevelopmentCard> list){
                ImageView im;
                Image img;
                int count=0;
                while(count< list.size()){
                        if(list.get(count)==null){
                                System.out.println("È VUOTO");
                        }
                        else if(slot==0 && list.get(count)!=null){
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
                        list.add(tree.pollFirst());
                        count++;
                }
        }
}

