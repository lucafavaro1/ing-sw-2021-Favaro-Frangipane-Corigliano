package it.polimi.ingsw.server.controller;

import com.google.gson.Gson;
import it.polimi.ingsw.common.networkCommunication.GsonSerializerDeserializer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that permits the player to choose one between different objects
 *
 * @param <T> the type of objects to use
 */
public class MakePlayerChoose<T> {
    private final List<T> toBeChosen;
    private String message = "";

    /**
     * Constructor of the class only with the list
     * @param toBeChosen list of things from which the client can choose
     */
    public MakePlayerChoose(List<T> toBeChosen) {
        this.toBeChosen = toBeChosen;
    }

    /**
     * Constructor of the class with the message
     * @param message the message for the client
     * @param toBeChosen list of things from which the client can choose
     */
    public MakePlayerChoose(String message, List<T> toBeChosen) {
        this(toBeChosen);
        this.message = message;
    }

    /**
     * method that makes the player choose among the elements of the list toBeChosen
     *
     * @param player the player who has to choose the element
     * @return the element chosen by the player
     */
    public T choose(HumanPlayer player) {
        int chosen = -1;
        if (toBeChosen.size() == 0) {
            throw new IllegalArgumentException("No options in the MakePlayerChoose");
        }

        do {
            try {
                // sending this MakePlayerChoose to the client and wait for a response by him
                String option = player.getGameClientHandler().sendMessageGetResponse(this);
                chosen = (int) Float.parseFloat(option);
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

    /**
     * Method that has the logic to pay a production
     *
     * @param player       player that has to pay the production
     * @param requirements requirements to be payed
     */
    public static boolean payRequirements(HumanPlayer player, ResRequirements requirements) {
        requirements = new ResRequirements(requirements.getResourcesReq());
        if (requirements.isSatisfiable(player)) {
            for (Res_Enum res_enum : requirements.getResourcesReq()) {
                // letting choose the QUESTION resources among the possible resources
                if (res_enum == Res_Enum.QUESTION) {
                    res_enum = (new MakePlayerChoose<>(
                            "Choose the resource to spend",
                            Arrays.stream(Res_Enum.values()).filter(res -> player.getTotalResources().get(res) > 0).collect(Collectors.toList()))
                    ).choose(player);
                }

                // make the player choose among the available deposits
                int used;
                do {
                    used = (
                            new MakePlayerChoose<>("From which deposit you want to take a " + res_enum + "?",
                                    player.getDepositsWithResource(res_enum))
                    ).choose(player).useRes(res_enum, 1);
                } while (used == 0);
            }
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return "MakePlayerChoose{" +
                "toBeChosen=" + toBeChosen +
                ", message='" + message + '\'' +
                '}';
    }
}
