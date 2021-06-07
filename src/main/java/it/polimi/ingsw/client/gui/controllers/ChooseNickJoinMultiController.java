package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
            Label choose = (Label) getPrimarystage().getScene().lookup("#choose");
            choose.setText("Nickname not valid, try again!");
            choose.setStyle("-fx-font-size: 24");
            choose.setStyle("-fx-text-fill: red");
            choose.setScaleX(1.5);
            choose.setScaleY(1.5);
        }
        else if(returnmess.equals("You reconnected to Masters of Renaissance")) {
            setMynickname(nickname);
            loadItems();
            procedure();

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
