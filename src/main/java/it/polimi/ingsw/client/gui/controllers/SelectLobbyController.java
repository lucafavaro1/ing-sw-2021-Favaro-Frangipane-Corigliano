package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GUI Controller: selecting the lobby in which you want to join (first press "Update", than choose, than click "Join")
 */
public class SelectLobbyController extends Controller{

    @FXML
    private ComboBox lobbyList;
    int numberlobby=0;
    int count=1;
    private String str1;

    /**
     * Join a lobby by clicking first on update, choose the lobby from the list and than press the join button
     * @param mouseEvent click on join button
     * @throws IOException if the name scene is wrong
     */
    public void joinLobbyEvent(MouseEvent mouseEvent) throws IOException {

            numberlobby=Integer.parseInt(lobbyList.getValue().toString().substring(lobbyList.getValue().toString().indexOf(" ")+1));
            System.out.println(numberlobby);
            getOut().println(numberlobby);
            System.out.println(getIn().readLine());
            System.out.println(getIn().readLine());

            loadScene("ChooseNickJoinMulti.fxml");
    }

    /**
     * Method to update the current status of non full lobbies (that can be joined)
     * @param mouseEvent click on update button
     * @throws IOException if the name of the scene is wrong
     */
    public void updateLobbyEvent(MouseEvent mouseEvent)throws IOException{
        getOut().println(0);
        System.out.println(getIn().readLine());//multipl
        String str= getIn().readLine();

        System.out.println(str);//lista

        str1= str.substring(str.indexOf('[') +1, str.indexOf(']'));
        System.out.println(str1);
        List<Integer> list = Stream.of(str1.split(", "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        while(count<=list.size()){
            lobbyList.getItems().add("Lobby "+count);
            count++;
        }




    }




}
