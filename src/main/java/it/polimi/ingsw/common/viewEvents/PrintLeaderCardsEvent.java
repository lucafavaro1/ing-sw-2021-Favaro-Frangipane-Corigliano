package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.client.gui.controllers.Controller;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

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
        if (userInterface.getClass() == GUIUserInterface.class && nickname.equals(userInterface.getMyNickname())) {
            if (toPrint.size() == 0) {
                ImageView l1 = (ImageView) Controller.getLeadercards().lookup("#leadercard1");
                ProgressBar b1 = (ProgressBar) Controller.getLeadercards().lookup("#leader1activate");
                ImageView l2 = (ImageView) Controller.getLeadercards().lookup("#leadercard2");
                ProgressBar b2 = (ProgressBar) Controller.getLeadercards().lookup("#leader2activate");
                l1.setImage(null);
                l2.setImage(null);
                b1.setProgress(0);
                b2.setProgress(0);
            }
            userInterface.printMessage(toPrint);
        }

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
