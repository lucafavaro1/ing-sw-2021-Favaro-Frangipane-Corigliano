package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.common.viewEvents.PrintProductionsAddedEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

import java.util.ArrayList;
import java.util.List;

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
            player.getGameClientHandler().sendEvent(new FailEvent("Can't complete this action, it's not your turn!"));
            return;
        }

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Main action already completed in this turn!"));
            return;
        }

        if (player.getAvailableProductions().isEmpty()) {
            player.getGameClientHandler().sendEvent(new FailEvent("No more productions available!"));
            return;
        }

        List<Object> productions = new ArrayList<>(player.getAvailableProductions());
        productions.add("Go back");

        Object chosen = (new MakePlayerChoose<>(
                "Choose the production you want to use: ",
                productions)
        ).choose(player);

        // check if the player wants to go back
        if (chosen.equals("Go back")) {
            player.getGameClientHandler().sendEvent(new ActionDoneEvent(""));
            return;
        }

        Production production = (Production) chosen;

        if (!production.isSatisfiable(player)) {
            player.getGameClientHandler().sendEvent(new FailEvent("Production requirements not satisfiable!"));
            return;
        }

        // adding the production to the productions list
        if (!player.addProduction(production)) {
            player.getGameClientHandler().sendEvent(new FailEvent("Production not added!"));
            return;
        }

        player.getGameClientHandler().sendEvent(new PrintProductionsAddedEvent(player));
        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You added a new production!"));
    }
}
