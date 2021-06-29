package it.polimi.ingsw.client.gui.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GUI Controller: selecting the lobby in which you want to join (first press "Update", than choose, than click "Join")
 */
public class SelectLobbyController extends Controller {
    @FXML
    public ComboBox lobbyList;
    int numberlobby = 0;
    int count = 1;
    private String str1;

    /**
     * Join a lobby by clicking first on update, choose the lobby from the list and than press the join button
     *
     * @param mouseEvent click on join button
     * @throws IOException if the name scene is wrong
     */
    public void joinLobbyEvent(MouseEvent mouseEvent) throws IOException {
        String str;
        numberlobby = Integer.parseInt(lobbyList.getValue().toString().substring(lobbyList.getValue().toString().indexOf(" ") + 1));
        System.out.println(numberlobby);
        getOut().println(numberlobby);
        str = getIn().readLine();
        System.out.println(str);
        if (!str.contains("Invalid option, choose again :")) {
            loadScene("ChooseNickJoinMulti.fxml");
            String message;
            message = getIn().readLine();
            System.out.println(message);
            message = message.replace("Choose a valid nickname (", "");
            message = message.substring(0, message.length() - 1);
            Label choose = (Label) getPrimarystage().getScene().lookup("#choose");
            choose.setText(message);
        } else {
            Stage pop = new Stage();
            pop.initModality(Modality.APPLICATION_MODAL);
            pop.setTitle("Warning - Error!");
            pop.setMinWidth(550);
            pop.setMinHeight(150);

            Label label = new Label();
            label.setText("Lobby is full! Please choose a different one");

            label.setStyle("-fx-font-size: 50 ");
            label.setStyle("-fx-font-weight: bold");
            label.setStyle("-fx-text-fill: red");
            label.setScaleX(1.5);
            label.setScaleY(1.5);

            VBox layout = new VBox();
            layout.getChildren().add(label);
            layout.setAlignment(Pos.CENTER);
            layout.setStyle("-fx-background-color: #F8EFD1");

            Scene scene = new Scene(layout);
            pop.setScene(scene);
            pop.showAndWait();
            str = getIn().readLine();
        }
    }

    /**
     * Method to update the current status of non full lobbies (that can be joined)
     *
     * @param mouseEvent click on update button
     * @throws IOException if the name of the scene is wrong
     */
    public void updateLobbyEvent(MouseEvent mouseEvent) throws IOException {
        getOut().println(0);
        System.out.println(getIn().readLine());//multipl
        String str = getIn().readLine();

        System.out.println(str);//lista

        str1 = str.substring(str.indexOf('[') + 1, str.indexOf(']'));
        System.out.println(str1);
        List<Integer> list = Stream.of(str1.split(", "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        while (count <= list.size()) {
            lobbyList.getItems().add("Lobby " + count);
            count++;
        }
    }
}
