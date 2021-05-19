package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
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

    public T choose(HumanPlayer player) {
        int chosen = -1;
        if (toBeChosen.size() == 0) {
            throw new IllegalArgumentException("No options in the MakePlayerChoose");
        }

        do {
            try {
                // creating a new makePlayerChoose object so that we send only the info we want to show the client
                // TODO modify to send objects, and not only strings
                String option = player.getGameClientHandler().sendMessageGetResponse(/*this*/
                        new MakePlayerChoose<>(message, toBeChosen.stream().map(Objects::toString).collect(Collectors.toList()))
                );
                chosen = (int) Float.parseFloat(option);
                System.out.println("chosen: " + chosen);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return toBeChosen.get(chosen);
    }

    public List<T> getToBeChosen() {
        return toBeChosen;
    }

    public String getMessage() {
        return message;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return "MakePlayerChoose{" +
                "toBeChosen=" + toBeChosen +
                ", message='" + message + '\'' +
                '}';
    }
}
