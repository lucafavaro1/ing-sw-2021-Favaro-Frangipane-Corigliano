package it.polimi.ingsw.SingletonTrial;

import it.polimi.ingsw.MockGame;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base class that permits to implement a singleton parametrized on the game for every class that extends this class
 */
public abstract class SingletonClass {
    /**
     * Static map that memorizes all the instances of all the classes
     * key = Class that needs a singleton, value = [map with key = game associated to the singleton; value = singleton of that Class]
     * TODO: change MockGame to the real Game once it is available
     */
    static private final Map<Class, Map<MockGame, Object>> instances = new ConcurrentHashMap<>();

    /**
     * getter for all the instances of a type of class
     * Used for testing purposes
     * TODO: change MockGame to the real Game once it is available
     *
     * @return map that associates to every game its singleton
     */
    static protected <T> Map<MockGame, T> getInstances(Class c) {
        Map<MockGame, T> instancesCasted = new ConcurrentHashMap<>();

        if (instances.get(c) == null)
            instances.put(c, new ConcurrentHashMap<>());

        for (Map.Entry<MockGame, Object> entry : instances.get(c).entrySet()) {
            if (entry.getValue() != null) {
                instancesCasted.put(entry.getKey(), (T) entry.getValue());
            }
        }

        return instancesCasted;
    }

    /**
     * Method used only for testing purposes, used to delete all the instances of a class
     */
    static protected void resetInstances(Class c) {

        if (instances.get(c) == null)
            instances.put(c, new ConcurrentHashMap<>());

        instances.get(c).clear();
    }

    /**
     * Method used when a game is finished
     * TODO: change MockGame to the real Game once it is available
     */
    static public void deleteGame(MockGame game) {
        instances.keySet().forEach(c -> removeInstance(game, c));
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
            System.out.println(constructor);
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
     * Method used only for testing purposes, used to delete the instance of a particular class for a particular game
     * TODO: change MockGame to the real Game once it is available
     */
    static protected void removeInstance(MockGame game, Class c) {
        instances.get(c).remove(game);
    }
}