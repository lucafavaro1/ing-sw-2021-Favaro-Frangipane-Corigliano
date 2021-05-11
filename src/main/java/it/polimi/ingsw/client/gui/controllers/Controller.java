package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.NetTuple;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Controller {
    private static Socket clientSocket=null;

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
    private int lobby=-1;

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


    public String decCardToUrl(DevelopmentCard dev) {
        if(dev.getCardType().getLevel()==1) {
            if(dev.getCardType().getType() == TypeDevCards_Enum.BLUE) {
                if(dev.getCardVictoryPoints()==1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-3-1.png";
                else if (dev.getCardVictoryPoints()==2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-7-1.png";
                else if (dev.getCardVictoryPoints()==3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-11-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-15-1.png";
            }
            else if(dev.getCardType().getType() == TypeDevCards_Enum.GREEN) {
                if(dev.getCardVictoryPoints()==1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-1-1.png";
                else if (dev.getCardVictoryPoints()==2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-5-1.png";
                else if (dev.getCardVictoryPoints()==3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-9-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-13-1.png";
            }
            else if(dev.getCardType().getType() == TypeDevCards_Enum.YELLOW) {
                if(dev.getCardVictoryPoints()==1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-4-1.png";
                else if (dev.getCardVictoryPoints()==2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-8-1.png";
                else if (dev.getCardVictoryPoints()==3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-12-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-16-1.png";
            }
            else {
                if(dev.getCardVictoryPoints()==1)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-2-1.png";
                else if (dev.getCardVictoryPoints()==2)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-6-1.png";
                else if (dev.getCardVictoryPoints()==3)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-10-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-14-1.png";
            }
        }
        else if (dev.getCardType().getLevel()==2) {
            if(dev.getCardType().getType() == TypeDevCards_Enum.BLUE) {
                if(dev.getCardVictoryPoints()==5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-19-1.png";
                else if (dev.getCardVictoryPoints()==6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-23-1.png";
                else if (dev.getCardVictoryPoints()==7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-27-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-31-1.png";
            }
            else if(dev.getCardType().getType() == TypeDevCards_Enum.GREEN) {
                if(dev.getCardVictoryPoints()==5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-17-1.png";
                else if (dev.getCardVictoryPoints()==6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-21-1.png";
                else if (dev.getCardVictoryPoints()==7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-25-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-29-1.png";
            }
            else if(dev.getCardType().getType() == TypeDevCards_Enum.YELLOW) {
                if(dev.getCardVictoryPoints()==5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-20-1.png";
                else if (dev.getCardVictoryPoints()==6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-24-1.png";
                else if (dev.getCardVictoryPoints()==7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-28-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-32-1.png";
            }
            else {
                if(dev.getCardVictoryPoints()==5)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-18-1.png";
                else if (dev.getCardVictoryPoints()==6)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-22-1.png";
                else if (dev.getCardVictoryPoints()==7)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-26-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-30-1.png";
            }
        }
        else {
            if(dev.getCardType().getType() == TypeDevCards_Enum.BLUE) {
                if(dev.getCardVictoryPoints()==9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-35-1.png";
                else if (dev.getCardVictoryPoints()==10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-39-1.png";
                else if (dev.getCardVictoryPoints()==11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-43-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-47-1.png";
            }
            else if(dev.getCardType().getType() == TypeDevCards_Enum.GREEN) {
                if(dev.getCardVictoryPoints()==9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-33-1.png";
                else if (dev.getCardVictoryPoints()==10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-37-1.png";
                else if (dev.getCardVictoryPoints()==11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-41-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-45-1.png";
            }
            else if(dev.getCardType().getType() == TypeDevCards_Enum.YELLOW) {
                if(dev.getCardVictoryPoints()==9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-36-1.png";
                else if (dev.getCardVictoryPoints()==10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-40-1.png";
                else if (dev.getCardVictoryPoints()==11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-44-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-48-1.png";
            }
            else {
                if(dev.getCardVictoryPoints()==9)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-34-1.png";
                else if (dev.getCardVictoryPoints()==10)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-38-1.png";
                else if (dev.getCardVictoryPoints()==11)
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-42-1.png";
                else
                    return "/GraphicsGUI/front/Masters of Renaissance_Cards_FRONT_3mmBleed_1-46-1.png";
            }
        }
    }

}
