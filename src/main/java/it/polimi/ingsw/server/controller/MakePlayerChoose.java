package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.common.networkCommunication.MessageBroker;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * TODO: test MakePlayerChoose?
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

    public T choose(MessageBroker messageBroker) {
        int chosen = -1;

        /* TODO: composing the message (to be moved in the client)
        String message = "Choose one of the following elements: \n";
        for (int i = 0; i < toBeChosen.size(); i++) {
            message += i + ")" + toBeChosen.get(i) + "\n";
        }
        message += "Insert a number between 0 and " + (toBeChosen.size() - 1) + ": ";
        */

        do {
            try {
                String option = messageBroker.sendMessageGetResponse(this);
                chosen = (int) Float.parseFloat(option);
                System.out.println("chosen: " + chosen);
            } catch (NumberFormatException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return toBeChosen.get(chosen);
    }

    /**
     * method that makes the player choose which one of the elements in the list chooses
     * TODO test
     *
     * @param player player that has to choose the element
     * @return the element chosen by the player
     */
    public T choose(HumanPlayer player) {
        // TODO: replace with the internet communication (maybe using an event)
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;

        System.out.println("Choose one of the following elements:\n");
        for (int i = 0; i < toBeChosen.size(); i++) {
            System.out.println(i + ")" + toBeChosen.get(i));
        }

        do {
            System.out.println("Insert a number between 0 and " + (toBeChosen.size() - 1) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine());
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return toBeChosen.get(chosen);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
