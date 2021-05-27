package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.common.viewEvents.PrintLeaderCardsEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.MoreProduction;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Event that signals the activation of the production
 */
public class ActivateLeaderEvent extends Event {
    private int numcard = -1;

    public ActivateLeaderEvent() {
        eventType = Events_Enum.ACTIVATE_LEADER;
    }

    public ActivateLeaderEvent(int num) {
        this();
        this.numcard = num;
    }

    @Override
    public void handle(Object playerObj) {
        boolean enabled;
        HumanPlayer player = (HumanPlayer) playerObj;
        LeaderCard leaderCard;

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Impossibile fare questa azione, non è il tuo turno!"));
            return;
        }

        List<Object> leaderCardList = player.getLeaderCards().stream().filter(leader -> !leader.isEnabled()).collect(Collectors.toList());
        if (leaderCardList.size() == 0) {
            player.getGameClientHandler().sendEvent(new FailEvent("Non ci sono carte leader da attivare"));
        }

        if (numcard != -1) {
            try {
                leaderCard = ((LeaderCard) leaderCardList.get(numcard));
            } catch (IndexOutOfBoundsException e) {
                player.getGameClientHandler().sendEvent(new FailEvent("Carta leader inesistente"));
                return;
            }
        } else {
            leaderCardList.add("Torna indietro");
            // making the player choose the leader card to activate
            Object chosen = (new MakePlayerChoose<>(
                    "Scegli la carta leader da attivare: ",
                    leaderCardList
            ).choose(player));

            // check if the player wants to go back
            if (chosen.equals("Torna indietro")) {
                player.getGameClientHandler().sendEvent(new ActionDoneEvent(""));
                return;
            }

            leaderCard = (LeaderCard) chosen;
        }

        enabled = leaderCard.enable(player);

        if (leaderCard.getCardAbility().getAbilityType() == Abil_Enum.PRODUCTION)
            ((MoreProduction) leaderCard.getCardAbility()).getProduction().setAvailable(true);

        if (enabled) {
            player.getGameClientHandler().sendEvent(new PrintLeaderCardsEvent(player));
            player.getGameClientHandler().sendEvent(new ActionDoneEvent("You activated the leader card!"));
        } else {
            player.getGameClientHandler().sendEvent(new FailEvent("Leader card can't be enabled"));
        }
    }
}
