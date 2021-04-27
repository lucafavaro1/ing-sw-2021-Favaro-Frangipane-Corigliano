package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PlusFaithCardEventTest {

    @Test
    public void plusFaithActionCardTest() {
        Game game = new Game(1);
        CPUPlayer cpu = (CPUPlayer) game.getPlayers().get(1);

        // checking if the starting point of the cpu is 0
        assertEquals(0, cpu.getFaithTrack().getTrackPos());
        game.getEventBroker().post(new PlusFaithCardEvent(4), true);

        // checking if the faith has been increased
        assertEquals(4, cpu.getFaithTrack().getTrackPos());
    }
}