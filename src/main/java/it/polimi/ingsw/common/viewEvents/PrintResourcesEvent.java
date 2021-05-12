package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;


/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the resource situation of the player
 */
public class PrintResourcesEvent extends PrintEvent<List<Deposit>> {
    public PrintResourcesEvent(HumanPlayer player) {/*
        printType = PrintObjects_Enum.DEPOSITS;
        // TODO not working
        toPrint = List.of(
                player.getStrongBox(),
                player.getWarehouseDepots()
        );

        player.getEnabledLeaderCards(Abil_Enum.SLOT).forEach(
                leaderCard -> toPrint.add((PlusSlot) leaderCard.getCardAbility())
        );
        /*
        toPrint = nickname.getWarehouseDepots().toString() + "\n\n" +
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
                toPrint = toPrint.concat(((PlusSlot) leaderCard.getCardAbility()).getResource().toString());
            }
        }
        if (count == 0) {
            toPrint = toPrint.concat("there's nothing inside the leadercard slots \n");
        }*/
    }

    @Override
    public String toString() {
        return "View your deposit situation";
    }
}
