package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ClientMessageBroker;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.ActionCards.Effect;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.Market.Marble_Enum;
import it.polimi.ingsw.server.model.Market.MarketMarble;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * Abstract class implementing the fundamentals parts of the view controllers (personal scenes, socket, client message broker)
 * NB: This class uses attributes of the Model but only accessing it through Getter methods, therefore MVC is respected.
 */
public abstract class Controller {
    private static Socket clientSocket = null;
    private static Scene personalpunchboard;
    private static Scene markettray;
    private static Scene dcboard;
    private static Scene leadercards;
    private static Stage primarystage;
    private static ClientMessageBroker cmb;
    private static String mynickname;
    private static int singleormulti;       // 0 per singleplayer, 1 per multi

    public static String getMynickname() {
        return mynickname;
    }

    public static void setMynickname(String mynickname) {
        Controller.mynickname = mynickname;
    }

    public static int getSingleormulti() {
        return singleormulti;
    }

    public static void setSingleormulti(int singleormulti) {
        Controller.singleormulti = singleormulti;
    }

    public static Stage getPrimarystage() {
        return primarystage;
    }

    public static void setPrimarystage(Stage primarystage) {
        Controller.primarystage = primarystage;
    }

    public static ClientMessageBroker getCmb() {
        return cmb;
    }

    public static void setCmb(ClientMessageBroker cmb) {
        Controller.cmb = cmb;
    }

    public static Scene getPersonalpunchboard() {
        return personalpunchboard;
    }

    public static void setPersonalpunchboard(Scene personalpunchboard) {
        Controller.personalpunchboard = personalpunchboard;
    }

    public static Scene getMarkettray() {
        return markettray;
    }

    public static void setMarkettray(Scene markettray) {
        Controller.markettray = markettray;
    }

    public static Scene getDcboard() {
        return dcboard;
    }

    public static void setDcboard(Scene dcboard) {
        Controller.dcboard = dcboard;
    }

    public static Scene getLeadercards() {
        return leadercards;
    }

    public static void setLeadercards(Scene leadercards) {
        Controller.leadercards = leadercards;
    }

    public static BufferedWriter getBw() {
        return bw;
    }

    public static void setBw(BufferedWriter bw) {
        Controller.bw = bw;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static void setOut(PrintWriter out) {
        Controller.out = out;
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static void setIn(BufferedReader in) {
        Controller.in = in;
    }

    private static BufferedReader in;
    private static BufferedWriter bw;
    private static PrintWriter out;
    @FXML
    private int lobby = -1;

    public int getLobby() {
        return lobby;
    }

    public void setLobby(int lobby) {
        this.lobby = lobby;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    // FUNZIONI PER OTTENERE GLI URL DELLE IMMAGINI A PARTIRE DA GLI OGGETTI IN GAME
    public static String devCardToUrl(DevelopmentCard dev) {
        if (dev.getCardType().getLevel() == 1) {
            if (dev.getCardType().getType() == TypeDevCards_Enum.BLUE) {
                if (dev.getCardVictoryPoints() == 1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-3-1.png";
                else if (dev.getCardVictoryPoints() == 2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-7-1.png";
                else if (dev.getCardVictoryPoints() == 3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-11-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-15-1.png";
            } else if (dev.getCardType().getType() == TypeDevCards_Enum.GREEN) {
                if (dev.getCardVictoryPoints() == 1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png";
                else if (dev.getCardVictoryPoints() == 2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-5-1.png";
                else if (dev.getCardVictoryPoints() == 3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-9-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-13-1.png";
            } else if (dev.getCardType().getType() == TypeDevCards_Enum.YELLOW) {
                if (dev.getCardVictoryPoints() == 1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-4-1.png";
                else if (dev.getCardVictoryPoints() == 2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-8-1.png";
                else if (dev.getCardVictoryPoints() == 3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-12-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-16-1.png";
            } else {
                if (dev.getCardVictoryPoints() == 1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-2-1.png";
                else if (dev.getCardVictoryPoints() == 2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-6-1.png";
                else if (dev.getCardVictoryPoints() == 3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-10-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-14-1.png";
            }
        } else if (dev.getCardType().getLevel() == 2) {
            if (dev.getCardType().getType() == TypeDevCards_Enum.BLUE) {
                if (dev.getCardVictoryPoints() == 5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-19-1.png";
                else if (dev.getCardVictoryPoints() == 6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-23-1.png";
                else if (dev.getCardVictoryPoints() == 7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-27-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-31-1.png";
            } else if (dev.getCardType().getType() == TypeDevCards_Enum.GREEN) {
                if (dev.getCardVictoryPoints() == 5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-17-1.png";
                else if (dev.getCardVictoryPoints() == 6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-21-1.png";
                else if (dev.getCardVictoryPoints() == 7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-25-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-29-1.png";
            } else if (dev.getCardType().getType() == TypeDevCards_Enum.YELLOW) {
                if (dev.getCardVictoryPoints() == 5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-20-1.png";
                else if (dev.getCardVictoryPoints() == 6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-24-1.png";
                else if (dev.getCardVictoryPoints() == 7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-28-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-32-1.png";
            } else {
                if (dev.getCardVictoryPoints() == 5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-18-1.png";
                else if (dev.getCardVictoryPoints() == 6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-22-1.png";
                else if (dev.getCardVictoryPoints() == 7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-26-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-30-1.png";
            }
        } else {
            if (dev.getCardType().getType() == TypeDevCards_Enum.BLUE) {
                if (dev.getCardVictoryPoints() == 9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-35-1.png";
                else if (dev.getCardVictoryPoints() == 10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-39-1.png";
                else if (dev.getCardVictoryPoints() == 11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-43-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-47-1.png";
            } else if (dev.getCardType().getType() == TypeDevCards_Enum.GREEN) {
                if (dev.getCardVictoryPoints() == 9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-33-1.png";
                else if (dev.getCardVictoryPoints() == 10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-37-1.png";
                else if (dev.getCardVictoryPoints() == 11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-41-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-45-1.png";
            } else if (dev.getCardType().getType() == TypeDevCards_Enum.YELLOW) {
                if (dev.getCardVictoryPoints() == 9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-36-1.png";
                else if (dev.getCardVictoryPoints() == 10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-40-1.png";
                else if (dev.getCardVictoryPoints() == 11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-44-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-48-1.png";
            } else {
                if (dev.getCardVictoryPoints() == 9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-34-1.png";
                else if (dev.getCardVictoryPoints() == 10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-38-1.png";
                else if (dev.getCardVictoryPoints() == 11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-42-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-46-1.png";
            }
        }
    }

    public static String marbleToUrl(MarketMarble marble) {
        if (marble.getMarbleColor() == Marble_Enum.BLUE)
            return "/GraphicsGUI/punchboard/bluemarble.png";
        else if (marble.getMarbleColor() == Marble_Enum.GREY)
            return "/GraphicsGUI/punchboard/greymarble.PNG";
        else if (marble.getMarbleColor() == Marble_Enum.RED)
            return "/GraphicsGUI/punchboard/redmarble.PNG";
        else if (marble.getMarbleColor() == Marble_Enum.YELLOW)
            return "/GraphicsGUI/punchboard/yellowmarble.PNG";
        else if (marble.getMarbleColor() == Marble_Enum.PURPLE)
            return "/GraphicsGUI/punchboard/purplemarble.PNG";
        else    // white
            return "/GraphicsGUI/punchboard/whitemarble.PNG";
    }

    public static String resourceToUrl(Res_Enum ris) {
        if (ris == Res_Enum.COIN)
            return "/GraphicsGUI/punchboard/coin.png";
        else if (ris == Res_Enum.SERVANT)
            return "/GraphicsGUI/punchboard/servant.png";
        else if (ris == Res_Enum.STONE)
            return "/GraphicsGUI/punchboard/stone.png";
        else // if shield
            return "/GraphicsGUI/punchboard/shield.png";
    }

    public static String leaderToUrl(LeaderCard lea) {

        if (lea.getCardVictoryPoints() == 2) {// DISCOUNT
            if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.YELLOW &&
                    lea.getCardRequirements().getCardReq().get(1).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(1).getType() == TypeDevCards_Enum.GREEN) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-49-1.png";

            } else if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.BLUE &&
                    lea.getCardRequirements().getCardReq().get(1).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(1).getType() == TypeDevCards_Enum.PURPLE) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-50-1.png";
            } else if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.GREEN &&
                    lea.getCardRequirements().getCardReq().get(1).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(1).getType() == TypeDevCards_Enum.BLUE) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-51-1.png";
            } else
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-52-1.png";

        } else if (lea.getCardVictoryPoints() == 3) {// PLUS SLOT
            if (lea.getResRequirements().getResourcesReq().get(0) == Res_Enum.COIN) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-53-1.png";
            } else if (lea.getResRequirements().getResourcesReq().get(0) == Res_Enum.STONE) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-54-1.png";
            } else if (lea.getResRequirements().getResourcesReq().get(0) == Res_Enum.SERVANT) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-55-1.png";
            } else
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-56-1.png";

        } else if (lea.getCardVictoryPoints() == 4) {// PRODUCTION
            if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 2 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.YELLOW) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-61-1.png";
            } else if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 2 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.BLUE) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-62-1.png";
            } else if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 2 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.PURPLE) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-63-1.png";
            } else
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-64-1.png";

        } else {// WHITE MARBLE
            if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.YELLOW &&
                    lea.getCardRequirements().getCardReq().get(1).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(1).getType() == TypeDevCards_Enum.YELLOW) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-57-1.png";
            } else if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.GREEN &&
                    lea.getCardRequirements().getCardReq().get(1).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(1).getType() == TypeDevCards_Enum.GREEN) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-58-1.png";
            } else if (lea.getCardRequirements().getCardReq().get(0).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(0).getType() == TypeDevCards_Enum.BLUE &&
                    lea.getCardRequirements().getCardReq().get(1).getLevel() == 1 &&
                    lea.getCardRequirements().getCardReq().get(1).getType() == TypeDevCards_Enum.BLUE) {
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-59-1.png";
            } else
                return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-60-1.png";
        }

    }

    public static String actionToUrl(ActionCard actionCard) {
        if (actionCard.getEffect() == Effect.PLUS_TWO_FAITH)
            return "/GraphicsGUI/punchboard/cerchio5.png";
        else if (actionCard.getEffect() == Effect.PLUS_ONE_FAITH_SHUFFLE)
            return "/GraphicsGUI/punchboard/cerchio7.png";
        else {  // discard two cards
            if (actionCard.getDevCardToDiscard() == TypeDevCards_Enum.BLUE)
                return "/GraphicsGUI/punchboard/cerchio1.png";
            else if (actionCard.getDevCardToDiscard() == TypeDevCards_Enum.YELLOW)
                return "/GraphicsGUI/punchboard/cerchio4.png";
            if (actionCard.getDevCardToDiscard() == TypeDevCards_Enum.PURPLE)
                return "/GraphicsGUI/punchboard/cerchio3.png";
            else    // green
                return "/GraphicsGUI/punchboard/cerchio2.png";
        }
    }

    public static void loadItems() throws IOException {
        Parent root;
        if(Controller.getSingleormulti()==0) {
            FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/PunchboardSingle.fxml")));
            root = (Parent) loader.load();
        }
        else {
            FXMLLoader loader = new FXMLLoader((Controller.class.getResource("/Client/Punchboard.fxml")));
            root = (Parent) loader.load();
        }
        FXMLLoader loader1 = new FXMLLoader((Controller.class.getResource("/Client/marketTray.fxml")));
        Parent root1 = (Parent) loader1.load();
        FXMLLoader loader2 = new FXMLLoader((Controller.class.getResource("/Client/DcBoard.fxml")));
        Parent root2 = (Parent) loader2.load();
        FXMLLoader loader3 = new FXMLLoader((Controller.class.getResource("/Client/leaderCard.fxml")));
        Parent root3 = (Parent) loader3.load();

        Scene punchboard = new Scene(root);
        Scene market = new Scene(root1);
        Scene dcboard = new Scene(root2);
        Scene leader = new Scene(root3);
        setPersonalpunchboard(punchboard);
        setMarkettray(market);
        setDcboard(dcboard);
        setLeadercards(leader);
    }

    public static void loadScene(String name) throws IOException {
        FXMLLoader loader =  new FXMLLoader((Controller.class.getResource("/Client/"+name)));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);

        getPrimarystage().setScene(singleScene);
        getPrimarystage().show();
    }

}
