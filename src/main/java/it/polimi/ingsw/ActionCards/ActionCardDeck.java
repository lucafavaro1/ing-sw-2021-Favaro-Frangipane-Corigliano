package it.polimi.ingsw.ActionCards;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.BadFormatException;
import it.polimi.ingsw.DeckOfCards;
import it.polimi.ingsw.MockGame;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class (parametrized on the Game) that models the ActionCardDeck in a single player game
 */
public class ActionCardDeck extends DeckOfCards<ActionCard> {

    /**
     * Static map that memorizes all the instances of the eventBroker for every game played
     * key = Game, value = eventBroker associated to that Game
     * TODO: change MockGame to the real Game once it is available
     */
    static private final Map<MockGame, ActionCardDeck> instances = new HashMap<>();

    /**
     * Used for testing purposes
     * TODO: change MockGame to the real Game once it is available
     *
     * @return map that associates to every game its ActionCardDeck
     */
    static Map<MockGame, ActionCardDeck> getInstances() {
        return instances;
    }

    /**
     * Method used only for testing purposes, used to delete all the instances of EventBroker
     */
    static protected void resetInstances() {
        instances.clear();
    }

    /**
     * Method to get the single instance possible of the deck. if there isn't an instance,
     * a new one is created
     * TODO: change MockGame to the real Game once it is available
     *
     * @return the instance of the Deck
     */
    static public ActionCardDeck getInstance(MockGame game) {
        if (!instances.containsKey(game)) {
            try {
                instances.put(game, new ActionCardDeck());
            } catch (FileNotFoundException e) {
                throw new BadFormatException();
            }
        }

        return instances.get(game);
    }

    /**
     * Method used only for testing purposes, used to delete all the instances of EventBroker
     * TODO: change MockGame to the real Game once it is available
     */
    static protected void removeInstance(MockGame game) {
        instances.remove(game);
    }

    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     */
    public ActionCardDeck() throws FileNotFoundException {
        super("src/test/java/resources/ActionCards.json");
    }

    /**
     * overriding the method to parse a single json formatted card to a card object
     *
     * @param jsonCard takes a JsonElement to be parsed in a Card instance
     * @return the card object parsed from the json formatted card
     */
    @Override
    public ActionCard parseJsonCard(JsonElement jsonCard) {
        Gson gson = new Gson();

        // parsing the single jsonElement to an ActionCard class
        ActionCard actionCard = gson.fromJson(jsonCard, ActionCard.class);

        // checking if the card is well formatted
        if (actionCard.isAllowed())
            return actionCard;
        else
            throw new BadFormatException();
    }
}

