package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.Player.HumanPlayer;


/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the resource situation of the player
 */
public class PrintResourcesEvent extends PrintEvent {
    public PrintResourcesEvent(HumanPlayer nickname) {
        textMessage = nickname.getWarehouseDepots().toString() + "\n\n" +
                nickname.getStrongBox().toString() + "\n\n" +
                "LeaderSlots: ";

        int count = 0;
        for (LeaderCard leaderCard : nickname.getLeaderCards()) {
            if (
                    leaderCard.isEnabled() &&
                            leaderCard.getCardAbility().getAbilityType() == Abil_Enum.SLOT &&
                            ((PlusSlot) leaderCard.getCardAbility()).getResource().size() != 0
            ) {
                count++;
                textMessage = textMessage.concat(((PlusSlot) leaderCard.getCardAbility()).getResource().toString());
            }
        }
        if (count == 0) {
            textMessage = textMessage.concat("there's nothing inside the leadercard slots \n");
        }
    }

    @Override
    public String toString() {
        return "View your deposit situation";
    }
}
