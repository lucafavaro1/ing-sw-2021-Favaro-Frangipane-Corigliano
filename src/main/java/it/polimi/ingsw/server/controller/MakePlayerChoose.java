package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.common.networkCommunication.GsonSerializerDeserializer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;

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
        this(toBeChosen);
        this.message = message;
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
                String option = player.getGameClientHandler().sendMessageGetResponse(this);
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
        Gson gson = GsonSerializerDeserializer.getGson();

        System.out.println(gson.toJson(this));
        return gson.toJson(this);
    }

    public static MakePlayerChoose<?> fromJson(String mpcStr) {
        Gson gson = GsonSerializerDeserializer.getGson();

        return gson.fromJson(mpcStr, MakePlayerChoose.class);
    }

    @Override
    public String toString() {
        return "MakePlayerChoose{" +
                "toBeChosen=" + toBeChosen +
                ", message='" + message + '\'' +
                '}';
    }
}
