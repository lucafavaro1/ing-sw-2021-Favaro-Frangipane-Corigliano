package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.NetTuple;
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

}
