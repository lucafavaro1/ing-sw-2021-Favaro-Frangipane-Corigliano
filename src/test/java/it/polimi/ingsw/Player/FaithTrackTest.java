package it.polimi.ingsw.Player;

import org.junit.Test;
import static org.junit.Assert.*;

public class FaithTrackTest {
    @Test
    public void testGeneralPos() {
        FaithTrack faith = new FaithTrack();
        faith.increasePos(6);
        assertEquals(faith.getTrackPos(),6);
        assertTrue(faith.isVatican());
        assertEquals(faith.getVaticanSection(),1);
        assertFalse(faith.isPopeSpace());
        assertEquals(faith.getPosPoints(),2);
        faith.increasePos(2);
        assertTrue(faith.isVatican());
        assertEquals(faith.getVaticanSection(),1);
        assertTrue(faith.isPopeSpace());
    }

    @Test
    public void testVaticanReport() {
        FaithTrack faith = new FaithTrack();
        faith.increasePos(5);
        assertTrue(faith.isVatican());
        faith.VaticanReport(1);
        assertEquals(faith.getBonusPoints(),2);
        faith.increasePos(5);
        faith.VaticanReport(2);
        assertEquals(faith.getBonusPoints(),2);
    }

    // mancano i test per spedire la chiamata agli altri player quando raggiungo le condizioni per VATICAN REPORT

}