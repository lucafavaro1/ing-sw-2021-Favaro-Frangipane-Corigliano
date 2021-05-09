package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.GameServer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.management.MemoryNotificationInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: fornirgli in qualche modo i numeri disponibili o leggere il mess ricevuto dal server che dice se è valido o meno
// TODO: il problema è che il buffer in ha tutti i mess precedenti non letti quindi se adesso fai in.readline legge il primo mess
// TODO: spedito dal server al client quando ha fatto l'accesso alla prima schermata
// TODO: in questo momento funziona perfetto solo se metti il numero della lobby che sai già che esiste e il numero è corretto

public class selectLobbyController extends Controller{

    @FXML
    private ComboBox lobbyList;
    int numberlobby=0;
    int count=1;
    private String str1;

    public void joinLobbyEvent(MouseEvent mouseEvent) throws IOException {

            numberlobby=Integer.parseInt(lobbyList.getValue().toString().substring(lobbyList.getValue().toString().indexOf(" ")+1));
            System.out.println(numberlobby);
            getOut().println(numberlobby);
            System.out.println(getIn().readLine());
            System.out.println(getIn().readLine());
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/joiningLobbyScene.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            window.show();
    }

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
