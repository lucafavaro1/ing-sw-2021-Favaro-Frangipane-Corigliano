package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.server.GameClientHandler;
import it.polimi.ingsw.server.GameServer;
import it.polimi.ingsw.server.controller.GameHandler;
import it.polimi.ingsw.server.model.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class selectLobbyController extends  loginsceneController{

    private int count=1; //usato per mostrare il numero della lobby



    public void backevent(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/multiplayerscene1.fxml")));
        Parent root = (Parent) loader.load();

        Scene firstscene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(firstscene);
        window.show();
    }

    @FXML
    private ComboBox lobbyList;
    @FXML
    private Label fullLobbyError;
    @FXML
    private TextField nick;
    @FXML
    private Label nickError;
    public void joinLobbyEvent(MouseEvent mouseEvent) throws IOException{

        String input=lobbyList.getValue().toString();
        setLobby((Integer.parseInt(input.substring(input.indexOf(' ')+1, input.length())))); //prende dalla lista il numero della lobby scelta
        System.out.println("Hai scelto la Lobby: "+ getLobby());
        /*GameHandler gameToJoin = GameServer.getGameHandlers().get(getLobby());
        if(gameToJoin.getClientHandlers().size()<gameToJoin.getMaxPlayers()
            && !gameToJoin.isStarted()
            && !nick.getText().isBlank()
            && !(GameServer.getGameHandlers().get(getLobby()).getClientHandlers().stream()
                .map(GameClientHandler::getNickname)
                .anyMatch(nick::equals))){
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/joiningLobbyScene.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            window.show();
        }
        else if (gameToJoin.getClientHandlers().size()==gameToJoin.getMaxPlayers() || gameToJoin.isStarted()){ //lobby piena
            fullLobbyError.setOpacity(1.0);
        }
        else if((GameServer.getGameHandlers().get(getLobby()).getClientHandlers().stream() //nick già in uso
                .map(GameClientHandler::getNickname)
                .anyMatch(nick.getText()::equals)) || nick.getText().isBlank()){
                   nickError.setOpacity(1.0);
                }

*/
        if(nick.getText().isBlank()){ //da canmcellare e sostituire con la parte commentata sopra
            nickError.setOpacity(1.0);
        }
        else{
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/joiningLobbyScene.fxml")));
            Parent root = (Parent) loader.load();

            Scene firstscene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(firstscene);
            window.show();
        }


    }

    //Ogni volta che il mouse passa sopra la lista delle lobby, questa viene aggiornata automaticamente
    public void updateListEvent(MouseEvent mouseEvent) throws IOException{
        System.out.println(GameServer.getGameHandlers().size());//da cancellare una volta funzionante
        //System.out.println(getClientSocket().getInetAddress().toString());
        System.out.println(getClientSocket().getPort());//da cancellare una volta funzionante
        while(count<=5){  // va cambiato con while(count<GameServer.getGameHandlers().size() solo che non mi funziona :)
            lobbyList.getItems().add("Lobby "+ count);
            count++;
        }

    }

}
