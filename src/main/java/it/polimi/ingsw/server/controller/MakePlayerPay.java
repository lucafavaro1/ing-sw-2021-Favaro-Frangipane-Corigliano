package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MakePlayerPay {

    /**
     * TODO: where to put this method?
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
}
