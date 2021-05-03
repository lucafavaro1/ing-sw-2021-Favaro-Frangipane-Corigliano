package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.server.model.Market.Marble_Enum;
import it.polimi.ingsw.server.model.Market.MarketMarble;
import org.junit.Test;
import static org.junit.Assert.*;

public class TupleTest {
    /**
     * Method to test the overridden method isEqual for Tuple type
     * Case of different type but same level
     */
    @Test
    public void isEqualTest() {
        Tuple t1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Tuple t2 = new Tuple(TypeDevCards_Enum.GREEN, 1);
        assertFalse(t1.equals(t2));
    }

    /**
     * Method to test the overridden method isEqual for Tuple type
     * Case of same type and same level
     */
    @Test
    public void isEqualTest1() {
        Tuple t1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Tuple t2 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        assertTrue(t1.equals(t2));
    }

    /**
     * Method to test the overridden method isEqual for Tuple type
     * Case of one object not of Tuple type
     */
    @Test
    public void isEqualTest2() {
        Tuple t1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        MarketMarble m1 = new MarketMarble(Marble_Enum.RED);
        assertFalse(t1.equals(m1));
    }

    /**
     * Method to test the overridden method isEqual for Tuple type
     * Case of same object
     */
    @Test
    public void isEqualTest3() {
        Tuple t1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        assertTrue(t1.equals(t1));
    }

}