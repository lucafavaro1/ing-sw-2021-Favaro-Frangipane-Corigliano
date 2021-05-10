package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI Controller: choosing the multiplayer mode to plat (create a lobby or join an existing one)
 */
public class multiJoinOrCreateController extends Controller{

    public void joinlobbyEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println(2);
        String str=getIn().readLine();
        System.out.println(str); //messaggio multiplayer : joining o create
        if(str.equals("Multiplayer: create a new match")){ //se non ci sono lobby, se ne crea una nuova
            System.out.println(getIn().readLine());
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/ChooseNickCreateMulti.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();

        }
        else{

            //getOut().println(1);
            System.out.println(getIn().readLine()); //messaggio lista partite
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/SelectLobby.fxml")));
            Parent root = (Parent) loader.load();

            Scene singleScene = new Scene(root);
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(singleScene);
            window.show();

        }





    }


    public void createlobbyEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println("1");
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/Client/ChooseNickCreateMulti.fxml")));
        Parent root = (Parent) loader.load();

        Scene singleScene = new Scene(root);
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(singleScene);
        window.show();
        System.out.println(getIn().readLine());
        System.out.println(getIn().readLine());
    }

}
