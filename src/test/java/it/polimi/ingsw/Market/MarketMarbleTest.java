package it.polimi.ingsw.Market;

import it.polimi.ingsw.RequirementsAndProductions.Res_Enum;
import junit.framework.TestCase;
import org.junit.Test;

public class MarketMarbleTest extends TestCase {

    /**
     * Tests that uses 5 marbles (1 per type) and converts them into resources
     */
    @Test
    public void testConvertRes() {
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
            m1.convertRes(0);
            m2.convertRes(0);
            m3.convertRes(0);
            m4.convertRes(0);
            m5.convertRes(0);

        }
        catch (Exception e){
            fail();
        }
    }
}