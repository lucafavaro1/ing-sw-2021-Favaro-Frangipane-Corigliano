package it.polimi.ingsw.SingletonTrial;

import it.polimi.ingsw.MockGame;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class SingletonClassTest {

    /**
     * Testing if resetInstances() clears all the previous definitions
     */
    @Test
    public void resetInstancesTest() {
        ConcreteSingleton.resetInstances();

        // asserting that there shouldn't be any instance
        assertEquals(Set.of(), ConcreteSingleton.getInstances().keySet());

        MockGame game = new MockGame();
        ConcreteSingleton concreteSingleton1 = ConcreteSingleton.getInstance(game);

        // asserting that there is a key in the map
        assertEquals(Set.of(game), ConcreteSingleton.getInstances().keySet());

        // asserting that the key contains a non null value
        assertNotNull(ConcreteSingleton.getInstances().get(game));

        ConcreteSingleton.resetInstances();
        assertTrue(ConcreteSingleton.getInstances().keySet().isEmpty());
    }

    /**
     * Testing if removeInstance() deletes the right instance
     */
    @Test
    public void removeInstanceTest() {
        ConcreteSingleton.resetInstances();
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();

        ConcreteSingleton concreteSingleton1 = ConcreteSingleton.getInstance(game1);
        ConcreteSingleton concreteSingleton2 = ConcreteSingleton.getInstance(game2);

        // asserting that there have to be two instances
        assertEquals(2, ConcreteSingleton.getInstances().keySet().size());

        // removing a valid instance
        ConcreteSingleton.removeInstance(game1);

        // asserting that the only key present is the one not removed
        assertEquals(Set.of(game2), ConcreteSingleton.getInstances().keySet());
    }

    /**
     * Testing if a concrete Singleton returns the instance memorized
     */
    @Test
    public void getInstanceTestOnce() {
        ConcreteSingleton.resetInstances();
        MockGame game = new MockGame();
        ConcreteSingleton concreteSingleton = ConcreteSingleton.getInstance(game);
        assertSame(ConcreteSingleton.getInstances().get(game), concreteSingleton);
    }

    /**
     * Testing if a concrete Singleton is returning always the same instance
     */
    @Test
    public void getInstanceTestTwice() {
        ConcreteSingleton.resetInstances();
        MockGame game = new MockGame();
        ConcreteSingleton concreteSingleton = ConcreteSingleton.getInstance(game);
        ConcreteSingleton concreteSingletonBis = ConcreteSingleton.getInstance(game);
        assertSame(concreteSingletonBis, concreteSingleton);
    }

    /**
     * Testing if a concrete Singleton is returning different instances for different Games
     */
    @Test
    public void getInstanceTestDifferents() {
        ConcreteSingleton.resetInstances();
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();
        ConcreteSingleton concreteSingleton1 = ConcreteSingleton.getInstance(game1);
        ConcreteSingleton concreteSingleton2 = ConcreteSingleton.getInstance(game2);

        // asserts that there have to be two different instantiations of the a concrete Singleton
        assertSame(2, ConcreteSingleton.getInstances().keySet().size());

        // asserts that the wto instantiations must be different
        assertNotSame(concreteSingleton1, concreteSingleton2);
    }

    /**
     * Testing if a concrete Singleton is returning different instances for different Games
     */
    @Test
    public void getInstanceTestDifferentTwice() {
        ConcreteSingleton.resetInstances();
        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();

        ConcreteSingleton concreteSingleton1 = ConcreteSingleton.getInstance(game1);
        ConcreteSingleton concreteSingleton2 = ConcreteSingleton.getInstance(game2);

        // repeats the getInstance in order to get again the two instances
        ConcreteSingleton concreteSingleton1Bis = ConcreteSingleton.getInstance(game1);
        ConcreteSingleton concreteSingleton2Bis = ConcreteSingleton.getInstance(game2);

        // asserts that there have to be two different instantiations of the concrete Singleton
        assertSame(2, ConcreteSingleton.getInstances().keySet().size());

        // asserts that the two instantiations must be returned at the second getInstance() call
        assertSame(concreteSingleton1, concreteSingleton1Bis);
        assertSame(concreteSingleton2, concreteSingleton2Bis);

        // asserts that the two instantiations must be different
        assertNotSame(concreteSingleton1Bis, concreteSingleton2Bis);
    }

    /**
     * Testing if different concrete Singletons could be instantiated for same game
     */
    @Test
    public void getInstanceDifferentSingletons() {
        ConcreteSingleton.resetInstances();
        OtherConcreteSingleton.resetInstances();

        MockGame game1 = new MockGame();

        ConcreteSingleton concreteSingleton1 = ConcreteSingleton.getInstance(game1);
        OtherConcreteSingleton otherConcreteSingleton = OtherConcreteSingleton.getInstance(game1);

        // asserts that there have to be one instantiation of the concrete Singleton
        assertSame(1, ConcreteSingleton.getInstances().size());
        assertSame(1, OtherConcreteSingleton.getInstances().size());

        // asserts that the two instantiations must be different
        assertNotEquals(OtherConcreteSingleton.getInstances(), ConcreteSingleton.getInstances());
    }

    /**
     * Testing if different concrete Singletons could be instantiated for same game
     */
    @Test
    public void getDifferentInstancesDifferentSingletons() {
        ConcreteSingleton.resetInstances();
        OtherConcreteSingleton.resetInstances();

        MockGame game1 = new MockGame();
        MockGame game2 = new MockGame();

        ConcreteSingleton concreteSingleton1 = ConcreteSingleton.getInstance(game1);
        ConcreteSingleton concreteSingleton2 = ConcreteSingleton.getInstance(game2);
        OtherConcreteSingleton otherConcreteSingleton1 = OtherConcreteSingleton.getInstance(game1);
        OtherConcreteSingleton otherConcreteSingleton2 = OtherConcreteSingleton.getInstance(game2);

        // asserts that there have to be two instantiation of each concrete Singletons
        assertEquals(Set.of(game1, game2), ConcreteSingleton.getInstances().keySet());
        assertEquals(Set.of(game1, game2), OtherConcreteSingleton.getInstances().keySet());

        // asserting that the concrete singletons obtained are the one stored
        assertEquals(concreteSingleton1, ConcreteSingleton.getInstances().get(game1));
        assertEquals(concreteSingleton2, ConcreteSingleton.getInstances().get(game2));
        assertEquals(otherConcreteSingleton1, OtherConcreteSingleton.getInstances().get(game1));
        assertEquals(otherConcreteSingleton2, OtherConcreteSingleton.getInstances().get(game2));
    }

    // TODO add more tests (more about the possible failures of uploading different objects)
    // TODO add tests for deleteGame

}

// TODO add documentation
class ConcreteSingleton extends SingletonClass {
    public ConcreteSingleton(){

    }

    static Map<MockGame, ConcreteSingleton> getInstances() {
        return SingletonClass.getInstances(ConcreteSingleton.class);
    }

    static void resetInstances() {
        SingletonClass.resetInstances(ConcreteSingleton.class);
    }

    static public ConcreteSingleton getInstance(MockGame game) {
        return SingletonClass.getInstance(game, ConcreteSingleton.class);
    }

    static protected void removeInstance(MockGame game){
        SingletonClass.removeInstance(game, ConcreteSingleton.class);
    }
}

class OtherConcreteSingleton extends SingletonClass{
    public OtherConcreteSingleton(){

    }
    static Map<MockGame, OtherConcreteSingleton> getInstances() {
        return SingletonClass.getInstances(OtherConcreteSingleton.class);
    }

    static void resetInstances() {
        SingletonClass.resetInstances(OtherConcreteSingleton.class);
    }

    static public OtherConcreteSingleton getInstance(MockGame game) {
        return SingletonClass.getInstance(game, OtherConcreteSingleton.class);
    }

    static protected void removeInstance(MockGame game){
        SingletonClass.removeInstance(game, OtherConcreteSingleton.class);
    }
}