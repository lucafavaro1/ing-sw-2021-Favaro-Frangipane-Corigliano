package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * GUI Controller: choosing nickname in case of joining an existing lobby in multiplayer
 */
public class ChooseNickJoinMultiController extends Controller {
    @FXML
    public TextField text;
    public Label invalidNick;
    private String nickname;
    private String returnmess;

    /**
     * Insert nickname and press okay
     * @param mouseEvent click on okay button
     * @throws IOException if the scene name is not correct
     */
    public void enterEvent(MouseEvent mouseEvent) throws IOException {
        String buff;
        nickname = text.getText();
        getOut().println(nickname);
        returnmess = getIn().readLine();
        buff = getIn().readLine();
        System.out.println(buff);

        if(returnmess.equals("Invalid nickname")) {
           loadScene("ChooseNickJoinMultiErr.fxml");
        }
        else if(returnmess.equals("You reconnected to Masters of Renaissance")) {
            setMynickname(nickname);
            loadItems();
            EventBroker eventBroker = new EventBroker();
            UserInterface.newInstance(false, eventBroker);
            GUIUserInterface guiUserInterface = (GUIUserInterface) UserInterface.getInstance();
            guiUserInterface.setMyNickname(getMynickname());

            ClientController clientController = new ClientController(
                    eventBroker,
                    getClientSocket()
            );

            setCmb(clientController.getClientMessageBroker());
            clientController.start();

            Label myNickname = (Label) getPersonalpunchboard().lookup("#yourNickname");
            myNickname.setText("  "+getMynickname()+"  ");

            getPrimarystage().setScene(getPersonalpunchboard());

        }
        else {
            setMynickname(nickname);
            loadItems();
            loadScene("WaitingForPlayersScene.fxml");
        }
    }
}
