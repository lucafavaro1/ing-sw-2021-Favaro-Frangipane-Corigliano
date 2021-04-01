package it.polimi.ingsw.SingletonTrial;

import it.polimi.ingsw.MockGame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

// TODO: check and add documentation
public abstract class SingletonClass {
    /**
     * Static map that memorizes all the instances of the eventBroker for every game played
     * key = Game, value = eventBroker associated to that Game
     * TODO: change MockGame to the real Game once it is available
     */
    static private final Map<Class, Map<MockGame, Object>> instances = new HashMap<>();

    /**
     * Used for testing purposes
     * TODO: change MockGame to the real Game once it is available
     *
     * @return map that associates to every game its ActionCardDeck
     */
    static protected <T> Map<MockGame, T> getInstances(Class c) {
        Map<MockGame, T> instancesCasted = new HashMap<>();

        if (instances.get(c) == null)
            instances.put(c, new HashMap<>());

        for (Map.Entry<MockGame, Object> entry : instances.get(c).entrySet()) {
            if (entry.getValue() != null) {
                instancesCasted.put(entry.getKey(), (T) entry.getValue());
            }
        }

        return instancesCasted;
    }

    /**
     * Method used only for testing purposes, used to delete all the instances of EventBroker
     */
    static protected void resetInstances(Class c) {

        if (instances.get(c) == null)
            instances.put(c, new HashMap<>());

        instances.get(c).clear();
    }

    /**
     * Method to get the single instance possible of the deck. if there isn't an instance,
     * a new one is created
     * TODO: change MockGame to the real Game once it is available
     *
     * @return the instance of the Deck
     */
    static protected <T> T getInstance(MockGame game, Class c) {
        Map<MockGame, T> classInstances = getInstances(c);

        if (!classInstances.containsKey(game)) {
            Constructor<?> constructor = null;
            try {
                constructor = c.getConstructor();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            try {
                classInstances.put(game, (T) constructor.newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        instances.put(c, (Map<MockGame, Object>) classInstances);

        return classInstances.get(game);
    }

    /**
     * Method used only for testing purposes, used to delete all the instances of EventBroker
     * TODO: change MockGame to the real Game once it is available
     */
    static protected void removeInstance(MockGame game, Class c) {
        instances.get(c).remove(game);
    }
}
