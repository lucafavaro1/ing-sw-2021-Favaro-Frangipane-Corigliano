package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VaticanReportEventTest {

    /**
     * testing if the vatican report event works
     */
    @Test
    public void vaticanReportEvent1() {
        Game game = new Game(1);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        player1.getFaithTrack().increasePos(7);
        player2.getFaithTrack().increasePos(7);

        assertEquals(0, player1.getFaithTrack().getBonusPoints());
        assertEquals(0, player2.getFaithTrack().getBonusPoints());

        game.getEventBroker().post(new VaticanReportEvent(1), true);

        assertEquals(2, player1.getFaithTrack().getBonusPoints());
        assertEquals(2, player2.getFaithTrack().getBonusPoints());
    }

    @Test
    public void vaticanReportEvent2() {
        Game game = new Game(1);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        player1.getFaithTrack().increasePos(7);
        player2.getFaithTrack().increasePos(7);

        assertEquals(0, player1.getFaithTrack().getBonusPoints());
        assertEquals(0, player2.getFaithTrack().getBonusPoints());

        game.getEventBroker().post(new VaticanReportEvent(2), true);

        assertEquals(0, player1.getFaithTrack().getBonusPoints());
        assertEquals(0, player2.getFaithTrack().getBonusPoints());
    }
}