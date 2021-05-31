package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event sent by the server to the client (specified in the constructor) in order to update the view
 * In particular this event sends the leader card situation of the player
 */
public class PrintLeaderCardsEvent extends PrintEvent<List<LeaderCard>> {
    public PrintLeaderCardsEvent(HumanPlayer player) {
        super(player.getNickname(), player.getLeaderCards());
        printType = PrintObjects_Enum.LEADER_CARDS;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        // updating the view only if is of the client's player
        if (nickname.equals(userInterface.getMyNickname()))
            userInterface.printMessage(toPrint);

        // deleting the view of the leader cards not yet enabled
        if (!nickname.equals(userInterface.getMyNickname()) && !nickname.equals("Lorenzo (CPU)"))
            toPrint = toPrint.stream().map(leaderCard -> {
                if (!leaderCard.isEnabled()) {
                    return new LeaderCard(null, null, null, 0);
                }
                return leaderCard;
            }).collect(Collectors.toList());

        ((HumanPlayer) userInterface.getPlayers().get(nickname)).setLeaderCards(toPrint);
    }
}
