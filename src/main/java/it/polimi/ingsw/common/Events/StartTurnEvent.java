package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.controller.MakePlayerPay;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.List;

/**
 * Event that signals the starting of the turn of a player
 */
public class StartTurnEvent extends Event {

    public StartTurnEvent() {
        eventType = Events_Enum.START_TURN;
    }

    @Override
    public void handle(Object object) {
        // TODO develop once client is developed: notify to the client that can start sending events to the server
    }
}
