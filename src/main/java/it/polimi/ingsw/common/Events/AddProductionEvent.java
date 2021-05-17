package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

/**
 * Event that signals that the player wants to add a production to the productions to do
 */
public class AddProductionEvent extends Event {

    public AddProductionEvent() {
        eventType = Events_Enum.ADD_PRODUCTION;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = ((HumanPlayer) playerObj);

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't do this action, it's not your turn!"));
            return;
        }

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You already did a main action in this round!"));
            return;
        }

        if (player.getAvailableProductions().isEmpty()) {
            player.getGameClientHandler().sendEvent(new FailEvent("no more productions available!"));
            return;
        }

        Production production = (new MakePlayerChoose<>(
                "Choose the production you want to do: ",
                player.getAvailableProductions())
        ).choose(player);

        if (!production.isSatisfiable(player)) {
            player.getGameClientHandler().sendEvent(new FailEvent("Production requirements not satisfiable!"));
            return;
        }

        // adding the production to the productions list
        if (!player.addProduction(production)) {
            player.getGameClientHandler().sendEvent(new FailEvent("Production not added!"));
            return;
        }

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You added a new production!"));
    }
}
