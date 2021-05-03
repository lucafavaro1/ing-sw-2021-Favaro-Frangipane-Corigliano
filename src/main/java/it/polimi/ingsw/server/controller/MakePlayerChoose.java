package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.common.networkCommunication.ServerMessageBroker;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * TODO: test MakePlayerChoose? (Emilio ) -> "si andrebbe fatto ma non saprei come onestamente (Luca)"
 * Class that permits the player to choose one between different objects
 *
 * @param <T> the type of objects to use
 */
public class MakePlayerChoose<T> {
    private final List<T> toBeChosen;
    private String message = "";

    public MakePlayerChoose(List<T> toBeChosen) {
        this.toBeChosen = toBeChosen;
    }

    public MakePlayerChoose(String message, List<T> toBeChosen) {
        this.message = message;
        this.toBeChosen = toBeChosen;
    }

    public T choose(ServerMessageBroker serverMessageBroker) {
        int chosen = -1;

        do {
            try {
                String option = serverMessageBroker.sendMessageGetResponse(this);
                chosen = (int) Float.parseFloat(option);
                System.out.println("chosen: " + chosen);
            } catch (NumberFormatException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return toBeChosen.get(chosen);
    }

    // TODO to be deleted
    public T choose(HumanPlayer player) {
        return toBeChosen.get(choose());
    }

    /**
     * Method that makes the player choose which one of the elements in the list chooses
     * TODO test
     *
     * @return the element chosen by the player
     */
    public int choose() {
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;

        // printing a nice message
        // TODO: change in order to use gui/cli
        String message = this.message + "\n";
        for (int i = 0; i < toBeChosen.size(); i++) {
            message += i + ")" + toBeChosen.get(i) + "\n";
        }
        System.out.println(message);

        do {
            System.out.println("Insert a number between 0 and " + (toBeChosen.size() - 1) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine());
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return chosen;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
