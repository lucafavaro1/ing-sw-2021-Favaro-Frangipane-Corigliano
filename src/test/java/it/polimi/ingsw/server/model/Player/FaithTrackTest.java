package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Game;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class FaithTrackTest {
    /**
     * Test of the position of the faith marker
     */

    @Test
    public void testGeneralPos() {
        Game game = new Game(2);
        FaithTrack faith = game.getPlayers().get(0).getFaithTrack();
        faith.increasePos(6);
        assertEquals(faith.getTrackPos(), 6);
        assertTrue(faith.isVatican());
        assertEquals(faith.getVaticanSection(), 1);
        assertFalse(faith.isPopeSpace());
        assertEquals(faith.getPosPoints(), 2);
        faith.increasePos(2);
        assertTrue(faith.isVatican());
        assertEquals(faith.getVaticanSection(), 1);
        assertTrue(faith.isPopeSpace());
    }

    /**
     * Testing the vatican report method in vatican section
     */

    @Test
    public void testVaticanReport() {
        Game game = new Game(2);
        FaithTrack faith = game.getPlayers().get(0).getFaithTrack();
        faith.increasePos(5);
        assertTrue(faith.isVatican());
        faith.vaticanReport(1);
        assertEquals(faith.getBonusPoints()[0], 2);
        faith.increasePos(5);
        faith.vaticanReport(2);
        assertEquals(faith.getBonusPoints()[0], 2);
    }

    /**
     * Test calling the first vatican report for a player
     */
    @Test
    public void testVaticanReport1Event() {
        Game game = new Game(2);
        FaithTrack faithTrack1 = game.getPlayers().get(0).getFaithTrack();
        FaithTrack faithTrack2 = game.getPlayers().get(1).getFaithTrack();

        // triggers the first vatican report
        faithTrack1.increasePos(8);

        // putting a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(2, faithTrack1.getBonusPoints()[0]);
        assertEquals(0, faithTrack2.getBonusPoints()[0]);
    }

    /**
     * Test entering in the vatican section for more than a player
     */
    @Test
    public void testVaticanReportMorePlayersEvent() {
        Game game = new Game(2);
        FaithTrack faithTrack1 = game.getPlayers().get(0).getFaithTrack();
        FaithTrack faithTrack2 = game.getPlayers().get(1).getFaithTrack();

        // entering the first vatican section
        faithTrack1.increasePos(7);
        // triggers the first vatican report
        faithTrack2.increasePos(8);

        // putting a sleep in order to let the dispatcher notify the subscribers
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(2, faithTrack1.getBonusPoints()[0]);
        assertEquals(2, faithTrack2.getBonusPoints()[0]);
    }

}