package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import it.polimi.ingsw.server.model.Serializable;
import it.polimi.ingsw.server.model.SerializationType;

/**
 * Inner class that models the discard action as if it is a deposit as any other
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
        player.getGame().getEventBroker().postAllButMe(
                player.getFaithTrack(),
                new AddFaithEvent(1),
                false
        );
        return true;
    }

    @Override
    public String toString() {
        return "Scarta";
    }
}
