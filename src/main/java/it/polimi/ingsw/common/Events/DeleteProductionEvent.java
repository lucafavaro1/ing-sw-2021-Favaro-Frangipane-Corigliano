package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

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

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You already did a main action in this round!"));
            return;
        }

        player.deleteProduction(
                new MakePlayerChoose<>(
                        "Choose the production you want to delete",
                        player.getProductionsAdded()
                ).choose(player)
        );
        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You deleted a production!"));
    }
}
