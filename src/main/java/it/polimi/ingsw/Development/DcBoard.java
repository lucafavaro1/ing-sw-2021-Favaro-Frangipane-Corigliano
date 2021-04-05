package it.polimi.ingsw.Development;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Class representing the Development Card Board that is in common for all players
 */
public class DcBoard {
    private HashMap<Tuple, ArrayList<DevelopmentCard>> allCards = new HashMap<>();

    /**
     * Method to remove (consequence of buying) a development card from the DcBoard
     * @param t unique identifier of the card you want to take
     */
    public void removeCard(Tuple t) {
        if (allCards.get(t).size() > 0)
            allCards.get(t).remove(1);
    }

    /**
     * Method to get a deck in the DCBoard
     * @param t tuple identifying the deck
     * @return the list of cards of that specific deck
     */
    public ArrayList<DevelopmentCard> takeCard(Tuple t) {
        return allCards.get(t);
    }

    /**
     * Method to shuffle the twelve decks of development cards divided by type and level
     */
    public void shuffle() {
        for (int i = 1; i < 4; i++) {
            Tuple x= new Tuple(TypeDevCards_Enum.BLUE,i);
            ArrayList<DevelopmentCard> prev_deck = allCards.get(x);
            do {
                Collections.shuffle(allCards.get(x));
            } while (!allCards.get(x).equals(prev_deck));
        }
        for (int i = 1; i < 4; i++) {
            Tuple x= new Tuple(TypeDevCards_Enum.GREEN,i);
            ArrayList<DevelopmentCard> prev_deck = allCards.get(x);
            do {
                Collections.shuffle(allCards.get(x));
            } while (!allCards.get(x).equals(prev_deck));
        }
        for (int i = 1; i < 4; i++) {
            Tuple x= new Tuple(TypeDevCards_Enum.PURPLE,i);
            ArrayList<DevelopmentCard> prev_deck = allCards.get(x);
            do {
                Collections.shuffle(allCards.get(x));
            } while (!allCards.get(x).equals(prev_deck));
        }
        for (int i = 1; i < 4; i++) {
            Tuple x= new Tuple(TypeDevCards_Enum.YELLOW,i);
            ArrayList<DevelopmentCard> prev_deck = allCards.get(x);
            do {
                Collections.shuffle(allCards.get(x));
            } while (!allCards.get(x).equals(prev_deck));
        }
    }

}
