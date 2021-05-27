package it.polimi.ingsw.common.Events;

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
            player.getGameClientHandler().sendEvent(new FailEvent("Impossibile fare questa azione, non è il tuo turno!"));
            return;
        }

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Hai già fatto un'azione principale in questo turno!"));
            return;
        }

        if (player.getAvailableProductions().isEmpty()) {
            player.getGameClientHandler().sendEvent(new FailEvent("non hai altre produzioni disponibili!"));
            return;
        }

        List<Object> productions = new ArrayList<>(player.getAvailableProductions());
        productions.add("Torna indietro");

        Object chosen = (new MakePlayerChoose<>(
                "Scegli la produzione che vuoi fare: ",
                productions)
        ).choose(player);

        // check if the player wants to go back
        if (chosen.equals("Torna indietro")) {
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
