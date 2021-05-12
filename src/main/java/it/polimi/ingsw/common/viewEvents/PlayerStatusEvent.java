package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client to update ALL THE VIEW
 */
/*
// TODO javadoc, check development
public class PlayerStatusEvent extends PrintEvent {
    public PlayerStatusEvent(HumanPlayer nickname) throws BadSlotNumberException {
        eventType = Events_Enum.PRINT_MESSAGE;

        // print faithtrack
        toPrint = "Faithtrack : " + nickname.getFaithTrack().toString();

        // print leader cards
        toPrint = toPrint.concat("\nLeader cards: \n");
        for (LeaderCard leaderCard : nickname.getLeaderCards()) {
            toPrint = toPrint.concat(leaderCard.toString() + "\n");
        }

        // print resources
        toPrint = toPrint.concat(nickname.getWarehouseDepots().toString() +
                nickname.getStrongBox().toString() + "\nLeaderSlots: ");

        int count = 0;
        for (LeaderCard leaderCard : nickname.getLeaderCards()) {
            if (
                    leaderCard.isEnabled() &&
                            leaderCard.getCardAbility().getAbilityType() == Abil_Enum.SLOT &&
                            ((PlusSlot) leaderCard.getCardAbility()).getResource().size() != 0
            ) {
                count++;
                toPrint = toPrint.concat(((PlusSlot) leaderCard.getCardAbility()).getResource().toString());
            }
        }
        if (count == 0) {
            toPrint = toPrint.concat("there's nothing inside the leadercard slots \n");
        }

        // print development cards
        toPrint = toPrint.concat("Development cards: \n");
        for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(0)) {
            toPrint = toPrint.concat(developmentCard.toString() + "\n");
        }
        for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(1)) {
            toPrint = toPrint.concat(developmentCard.toString() + "\n");
        }
        for (DevelopmentCard developmentCard : nickname.getDevelopmentBoard().getCardsFromSlot(2)) {
            toPrint = toPrint.concat(developmentCard.toString() + "\n");
        }
    }
}*/
