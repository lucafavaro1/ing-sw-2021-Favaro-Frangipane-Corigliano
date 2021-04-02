package it.polimi.ingsw.ActionCards;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import it.polimi.ingsw.*;
import it.polimi.ingsw.SingletonTrial.DeckOfCardsSingleton;
import it.polimi.ingsw.SingletonTrial.SingletonClass;

import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Singleton class (parametrized on the Game) that models the ActionCardDeck in a single player game
 */
public class ActionCardDeckSingleton extends DeckOfCardsSingleton<ActionCard> {

    static Map<MockGame, ActionCardDeckSingleton> getInstances() {
        return SingletonClass.getInstances(ActionCardDeckSingleton.class);
    }

    static public void resetInstances() {
        SingletonClass.resetInstances(ActionCardDeckSingleton.class);
    }

    static public ActionCardDeckSingleton getInstance(MockGame game) {
        return SingletonClass.getInstance(game, ActionCardDeckSingleton.class);
    }

    static public void removeInstance(MockGame game){
        SingletonClass.removeInstance(game, ActionCardDeckSingleton.class);
    }

    /**
     * Constructor that loads the deck from a JSON file passed as parameter
     */
    public ActionCardDeckSingleton() throws FileNotFoundException {
        super("src/main/java/resources/ActionCards.json");
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

