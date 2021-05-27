package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintProductionsAddedEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;

import java.util.ArrayList;
import java.util.List;

/**
 * Event that deletes a production added in precedence
 * TODO: test
 */
public class DeleteProductionEvent extends Event {

    public DeleteProductionEvent() {
        eventType = Events_Enum.DELETE_PRODUCTION;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = ((HumanPlayer) playerObj);

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Impossibile fare questa azione, non è il tuo turno!"));
            return;
        }

        // returning a fail event if the player already did a main action
        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Hai già fatto un'azione principale in questo turno!"));
            return;
        }

        // returning a fail event if there are no productions added
        if (!player.getProductionsAdded().isEmpty()) {
            List<Object> productions = new ArrayList<>(player.getAvailableProductions());
            productions.add("Torna indietro");

            Object chosen = (new MakePlayerChoose<>(
                    "Scegli la produzione che non vuoi più fare: ",
                    productions)
            ).choose(player);

            // check if the player wants to go back
            if (chosen.equals("Torna indietro")) {
                player.getGameClientHandler().sendEvent(new ActionDoneEvent(""));
                return;
            }

            Production production = (Production) chosen;


            player.deleteProduction(production);

            player.getGameClientHandler().sendEvent(new PrintProductionsAddedEvent(player));
            player.getGameClientHandler().sendEvent(new ActionDoneEvent("Hai eliminato una produzione!"));
        } else {
            player.getGameClientHandler().sendEvent(new FailEvent("Nessuna produzione da eliminare!"));
        }
    }
}
