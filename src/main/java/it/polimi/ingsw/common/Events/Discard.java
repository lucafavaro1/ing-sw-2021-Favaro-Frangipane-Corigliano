package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintPlayerEvent;
import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

/**
 * Class that models the discard action as if it is a deposit as any other
 */
public class Discard extends Serializable implements Deposit {
    private final HumanPlayer player;

    public Discard(HumanPlayer player) {
        serializationType = SerializationType.DISCARD;
        this.player = player;
    }

    @Override
    public int useRes(Res_Enum res, int quantity) {
        return 0;
    }

    @Override
    public boolean tryAdding(Res_Enum res) {
        // increasing the faith by 1 to all the players except the player who discarded the resource
        player.getGame().getEventBroker().postAllButMe(
                player.getFaithTrack(),
                new AddFaithEvent(1),
                false
        );

        // notifying the increment of faith to all the players
        player.getGame().getPlayers().forEach(player ->
                player.getGame().getEventBroker().post(new PrintPlayerEvent(player), false)
        );
        return true;
    }

    @Override
    public String toString() {
        return "Discard";
    }
}
