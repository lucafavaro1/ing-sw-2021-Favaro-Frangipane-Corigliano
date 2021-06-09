package it.polimi.ingsw.server.model.Market;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import static org.junit.Assert.*;
import org.junit.Test;

public class MarketMarbleTest {

    /**
     * Testing the conversion of a RED marble
     */
    @Test
    public void testConvertRed() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        MarketMarble m1 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.RED);

        assertNull(m1.convertRes(player));
        assertEquals(1, player.getFaithTrack().getTrackPos());
    }

    /**
     * Testing the conversion of a White marble
     */
    @Test
    public void testConvertWhite() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        MarketMarble m1 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.WHITE);

        assertNull(m1.convertRes(player));
        assertEquals(0, player.getFaithTrack().getTrackPos());
    }

    /**
     * Testing the conversion of a Blue marble
     */
    @Test
    public void testConvertBlue() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        MarketMarble m1 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.BLUE);

        assertEquals(Res_Enum.SHIELD, m1.convertRes(player));
        assertEquals(0, player.getFaithTrack().getTrackPos());
    }

    /**
     * Testing the conversion of a Yellow marble
     */
    @Test
    public void testConvertYellow() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        MarketMarble m1 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.YELLOW);

        assertEquals(Res_Enum.COIN, m1.convertRes(player));
        assertEquals(0, player.getFaithTrack().getTrackPos());
    }

    /**
     * Testing the conversion of a Purple marble
     */
    @Test
    public void testConvertPurple() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        MarketMarble m1 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.PURPLE);

        assertEquals(Res_Enum.SERVANT, m1.convertRes(player));
        assertEquals(0, player.getFaithTrack().getTrackPos());
    }

    /**
     * Testing the conversion of a Stone marble
     */
    @Test
    public void testConvertStone() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        MarketMarble m1 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.GREY);

        assertEquals(Res_Enum.STONE, m1.convertRes(player));
        assertEquals(0, player.getFaithTrack().getTrackPos());
    }

    /**
     * Tests that uses 5 marbles (1 per type) and converts them into resources
     */
    @Test
    public void testConvertRes() {
        Game g1 = new Game(4);
        HumanPlayer p1 = new HumanPlayer(g1);
        MarketMarble m1 = new MarketMarble();
        MarketMarble m2 = new MarketMarble();
        MarketMarble m3 = new MarketMarble();
        MarketMarble m4 = new MarketMarble();
        MarketMarble m5 = new MarketMarble();
        m1.setMarbleColor(Marble_Enum.RED);
        m2.setMarbleColor(Marble_Enum.WHITE);
        m3.setMarbleColor(Marble_Enum.BLUE);
        m4.setMarbleColor(Marble_Enum.YELLOW);
        m5.setMarbleColor(Marble_Enum.PURPLE);

        try {
            m1.convertRes(p1);
            m2.convertRes(p1);
            m3.convertRes(p1);
            m4.convertRes(p1);
            m5.convertRes(p1);
        } catch (Exception e) {
            fail();
        }
    }
}