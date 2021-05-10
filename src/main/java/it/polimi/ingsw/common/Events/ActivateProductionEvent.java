package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintResourcesEvent;
import it.polimi.ingsw.server.controller.MakePlayerPay;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.List;

/**
 * Event that signals the activation of the production
 */
public class ActivateProductionEvent extends Event {

    public ActivateProductionEvent() {
        eventType = Events_Enum.ACTIVATE_PRODUCTION;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;
        List<Production> productionsAdded = player.getProductionsAdded();

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You already did a main action in this round!"));
            return;
        }

        if (productionsAdded.isEmpty()){
            player.getGameClientHandler().sendEvent(new FailEvent("You haven't added a production, can't start the production!"));
            return;
        }

        // for each production the player chooses from which deposit take the resource to pay
        for (Production production : productionsAdded) {
            // make the player pay the production
            MakePlayerPay.payRequirements(player, production);

            // the player receives the resources from the production
            Res_Enum.getFrequencies(production.getProductionResources())
                    .forEach(player.getStrongBox()::putRes);

            // the player receives the faith points from the productions
            player.getFaithTrack()
                    .increasePos(production.getCardFaith());
        }

        player.clearProductions();
        player.setActionDone();
        player.getGameClientHandler().sendEvent(new PrintResourcesEvent(player));
        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You completed the production action!"));
    }
}
