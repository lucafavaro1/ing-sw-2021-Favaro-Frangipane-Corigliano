package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.List;

public class MakePlayerPay {

    /**
     * method that has the logic to pay a production
     *
     * @param player       player that has to pay the production
     * @param requirements requirements to be payed
     */
    public static void payRequirements(HumanPlayer player, ResRequirements requirements) {
        List<Res_Enum> resToPay = requirements.getResourcesReq();

        if ((new ResRequirements(resToPay)).isSatisfiable(player)) {
            for (Res_Enum res_enum : resToPay) {
                // make the player choose among the available deposits
                int used;
                do {
                    used = (new MakePlayerChoose<>(player.getDepositsWithResource(res_enum)))
                            .choose(player)
                            .useRes(res_enum, 1);
                } while (used == 0);
            }
        }
    }
}
