package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.*;
import it.polimi.ingsw.server.controller.MakePlayerPay;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

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

        // for each production the player chooses from which deposit take the resource to pay
        for (Production production : productionsAdded) {
            // make the player pay the production
            if (!MakePlayerPay.payRequirements(player, production)) {
                player.getGameClientHandler().sendEvent(new FailEvent("Can't pay for the production requirements!"));
                return;
            }

            // the player receives the resources from the production
            production.getProductionResources().forEach(res_enum -> player.getStrongBox().tryAdding(res_enum.chooseResource(player)));

            // the player receives the faith points from the productions
            player.getFaithTrack()
                    .increasePos(production.getCardFaith());
        }

        player.clearProductions();
        player.setActionDone();
        
        // updating view
        player.getGame().getEventBroker().post(new PrintPlayerEvent(player), false);
        player.getGameClientHandler().sendEvent(new PrintWarehouseEvent(player));
        player.getGameClientHandler().sendEvent(new PrintStrongboxEvent(player));
        player.getGameClientHandler().sendEvent(new PrintLeaderCardsEvent(player));
        player.getGameClientHandler().sendEvent(new PrintFaithtrackEvent(player));

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You completed the production action!"));
    }
}
