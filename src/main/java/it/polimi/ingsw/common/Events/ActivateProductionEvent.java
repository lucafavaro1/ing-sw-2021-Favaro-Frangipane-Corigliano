package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.*;
import it.polimi.ingsw.server.controller.MakePlayerPay;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.Collections;
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

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't do this action, it's not your turn!"));
            return;
        }

        List<Production> productionsAdded = player.getProductionsAdded();

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You already did a main action in this round!"));
            return;
        }

        if (productionsAdded.isEmpty()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You haven't added a production, can't start the production!"));
            return;
        }

        // we always do the base production before all the others
        if(productionsAdded.contains(player.getBaseProduction()))
            Collections.swap(productionsAdded,0, productionsAdded.indexOf(player.getBaseProduction()));

        List<Res_Enum> resourcesObtained = new ArrayList<>();

        // for each production the player chooses from which deposit take the resource to pay
        for (Production production : productionsAdded) {
            // make the player pay the production
            if (!MakePlayerPay.payRequirements(player, production)) {
                player.getGameClientHandler().sendEvent(new FailEvent("Can't pay for the production requirements!"));
                return;
            }

            // the player receives the resources from the production
            resourcesObtained.addAll(production.getProductionResources());

            // the player receives the faith points from the productions
            player.getFaithTrack().increasePos(production.getCardFaith());
        }

        // adding all the resources obtained to the strong box
        resourcesObtained.forEach(res_enum -> player.getStrongBox().tryAdding(res_enum.chooseResource(player)));

        player.clearProductions();
        player.setActionDone();
        
        // updating view
        player.getGameClientHandler().sendEvent(new PrintPlayerEvent(player));
        player.getGameClientHandler().sendEvent(new PrintWarehouseEvent(player));
        player.getGameClientHandler().sendEvent(new PrintStrongboxEvent(player));
        player.getGameClientHandler().sendEvent(new PrintLeaderCardsEvent(player));
        player.getGameClientHandler().sendEvent(new PrintFaithtrackEvent(player));

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You completed the production action!"));
    }
}
