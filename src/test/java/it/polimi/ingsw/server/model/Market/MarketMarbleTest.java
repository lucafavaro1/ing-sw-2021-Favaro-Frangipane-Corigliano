package it.polimi.ingsw.server.model.Market;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import junit.framework.TestCase;
import org.junit.Test;

public class MarketMarbleTest extends TestCase {

    /**
     * Tests that uses 5 marbles (1 per type) and converts them into resources
     */
    @Test
    public void testConvertRes() {
        Game g1=new Game(4);
        HumanPlayer p1 = new HumanPlayer(g1,1);
        MarketMarble m1= new MarketMarble();
        MarketMarble m2= new MarketMarble();
        MarketMarble m3= new MarketMarble();
        MarketMarble m4= new MarketMarble();
        MarketMarble m5= new MarketMarble();
        m1.setMarbleColor(Marble_Enum.RED);
        m2.setMarbleColor(Marble_Enum.WHITE);
        m3.setMarbleColor(Marble_Enum.BLUE);
        m4.setMarbleColor(Marble_Enum.YELLOW);
        m5.setMarbleColor(Marble_Enum.PURPLE);
        try{
            m1.convertRes(p1);
            m2.convertRes(p1);
            m3.convertRes(p1);
            m4.convertRes(p1);
            m5.convertRes(p1);

        }
        catch (Exception e){
            fail();
        }
    }
}