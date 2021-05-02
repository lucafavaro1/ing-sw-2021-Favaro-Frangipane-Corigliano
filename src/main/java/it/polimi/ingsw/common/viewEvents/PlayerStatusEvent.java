package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

/**
 * Event sent by the server to the client to update ALL THE VIEW
 */

public class PlayerStatusEvent extends Event {
    PrintFaithtrackEvent faithtrackEvent;
    PrintLeaderCardsEvent leaderCardsEvent;
    PrintResourcesEvent resourcesEvent;
    PrintDevelopmentCardsEvent developmentCardsEvent;

    public PlayerStatusEvent(HumanPlayer nickname) throws BadSlotNumberException {
        faithtrackEvent = new PrintFaithtrackEvent(nickname);
        leaderCardsEvent = new PrintLeaderCardsEvent(nickname);
        resourcesEvent = new PrintResourcesEvent(nickname);
        developmentCardsEvent = new PrintDevelopmentCardsEvent(nickname);
    }

    @Override
    public void handle(Object player) {
        faithtrackEvent.handle(this);
        leaderCardsEvent.handle(this);
        resourcesEvent.handle(this);
        developmentCardsEvent.handle(this);
    }
}
