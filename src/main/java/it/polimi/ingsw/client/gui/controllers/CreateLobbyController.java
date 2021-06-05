package it.polimi.ingsw.client.gui.controllers;

import javafx.scene.input.MouseEvent;

import java.io.IOException;
/**
 * GUI Controller: choosing number of players in case of new multiplayer game
 */
public class CreateLobbyController extends Controller {

    /**
     * Choosing a two player game
     * @param mouseEvent click on "2" image
     * @throws IOException if the name of the scene is wrong
     */
    public void TwoPlayersEvent(MouseEvent mouseEvent) throws IOException{
        System.out.println("Due Giocatori");
        getOut().println("2");

        loadItems();

        System.out.println(getIn().readLine());

        loadScene("WaitingForPlayersScene.fxml");
    }

    /**
     * Choosing a three player game
     * @param mouseEvent click on "3" image
     * @throws IOException if the name of the scene is wrong
     */
    public void ThreePlayersEvent(MouseEvent mouseEvent) throws IOException{
        System.out.println("Tre Giocatori");
        getOut().println("3");

        loadItems();

        System.out.println(getIn().readLine());

        loadScene("WaitingForPlayersScene.fxml");

    }

    /**
     * Choosing a four player game
     * @param mouseEvent click on "4" image
     * @throws IOException if the name of the scene is wrong
     */
    public void FourPlayersEvent(MouseEvent mouseEvent) throws  IOException{
        System.out.println("Quattro Giocatori");
        getOut().println("4");

        loadItems();

        System.out.println(getIn().readLine());

        loadScene("WaitingForPlayersScene.fxml");
    }

}
