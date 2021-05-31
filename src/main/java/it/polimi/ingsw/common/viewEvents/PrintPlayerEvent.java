package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.util.stream.Collectors;

/**
 * Event sent by the server to the client in order to update the view
 * In particular this event sends the DcBoard situation of a game
 */
public class PrintPlayerEvent extends PrintEvent<Player> {
    public PrintPlayerEvent(Player player) {
        super(player.getNickname(), player);
        printType = PrintObjects_Enum.PLAYER;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        // deleting the view of the leader cards not yet enabled
        if (!nickname.equals(userInterface.getMyNickname()) && !nickname.equals("Lorenzo (CPU)"))
            ((HumanPlayer) toPrint).setLeaderCards(((HumanPlayer) toPrint).getLeaderCards().stream().map(leaderCard -> {
                if (!leaderCard.isEnabled()) {
                    return new LeaderCard(null, null, null, 0);
                }
                return leaderCard;
            }).collect(Collectors.toList()));

        // updating the model in the client
        userInterface.getPlayers().put(nickname, toPrint);

        // updating the view only if is of the client's player
        if (nickname.equals(userInterface.getMyNickname())) {
            HumanPlayer player = (HumanPlayer) toPrint;
            System.out.println("[UPDATING GUI] PERSONAL");
            userInterface.printMessage(player.getWarehouseDepots());
            userInterface.printMessage(player.getStrongBox());
            userInterface.printMessage(player.getFaithTrack());
            userInterface.printMessage(player.getLeaderCards());
            userInterface.printMessage(player.getDevelopmentBoard());
            userInterface.printMessage(player.getProductionsAdded());
        }
    }

    @Override
    public String toString() {
        return "View Common Development Board";
    }
}
