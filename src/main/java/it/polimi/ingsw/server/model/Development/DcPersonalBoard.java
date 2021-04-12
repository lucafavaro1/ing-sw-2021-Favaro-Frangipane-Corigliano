package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.server.model.Events.LastRoundEvent;
import it.polimi.ingsw.server.model.Game;

import java.util.*;

/**
 * Personal board on which the player puts the development cards he bought
 */
public class DcPersonalBoard {
    private final static int nSlots = 3;

    private final Game game;
    private final Map<Integer, TreeSet<DevelopmentCard>> slots = new HashMap<>();

    /**
     * constructor that creates the different slots of the board
     *
     * @param game game to which the board belongs to
     */
    public DcPersonalBoard(Game game) {
        this.game = game;

        for (int i = 0; i < nSlots; i++) {
            slots.put(i, new TreeSet<>());
        }
    }

    /**
     * adds a card to the slot
     *
     * @param slot slot number where we want to put the card
     * @param card the card to put in the slot
     * @throws BadCardPositionException The card can't be put in the slot passed
     * @throws BadSlotNumberException   the slot isn't valid
     */
    public void addCard(int slot, DevelopmentCard card) throws BadCardPositionException, BadSlotNumberException {

        checkSlotNumber(slot);

        /*
         * if the slot is empty and the card has the minimum level or the card is a level above the top card of the slot
         * adds the card to the slot;
         * otherwise, throws BadCardPositionException
         */
        if (card.isSuccessorOf(getTopCard(slot)))
            slots.get(slot).add(card);
        else
            throw new BadCardPositionException("Posizione non valida!");

        // if the player has 7 cards in his board, post the event LAST_ROUND
        if (slots.keySet().stream().mapToInt(key -> slots.get(key).size()).sum() == 7) {
            game.getEventBroker().post(new LastRoundEvent(), true);
        }
    }

    /**
     * Method that returns the first card from a slot without removing it
     *
     * @param slot slot number of the card we want to take
     * @return the development card relative to the slot number passed, null if the slot is empty
     * @throws BadSlotNumberException if the slot number doesn't correspond to a real slot
     */
    public DevelopmentCard getTopCard(int slot) throws BadSlotNumberException {
        checkSlotNumber(slot);

        try {
            return slots.get(slot).first();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * takes all the cards from a slot number
     *
     * @param slot slot number
     * @return a list of all the cards present in the slot
     * @throws BadSlotNumberException if the slot number doesn't correspond to a real slot
     */
    public List<DevelopmentCard> getCardsFromSlot(int slot) throws BadSlotNumberException {
        checkSlotNumber(slot);
        return new ArrayList<>(slots.get(slot));
    }

    /**
     * Checks if the slot number passed as parameter is valid
     *
     * @param slot slot to be checked
     * @throws BadSlotNumberException thrown if the slot doesn't exist
     */
    private void checkSlotNumber(int slot) throws BadSlotNumberException {
        if (slot < 0 || slot >= nSlots)
            throw new BadSlotNumberException("Slot non valido!");
    }
}
