package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.common.Events.LastRoundEvent;
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
     * Constructor that creates the different slots of the board and links the board to the game
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
     * Method to add a card to the slot
     *
     * @param slot slot number where we want to put the card (between 0 and nSlots)
     * @param card the card to put in the slot
     * @throws BadCardPositionException the card can't be put in the slot passed
     * @throws BadSlotNumberException   the slot number isn't valid
     */
    public void addCard(int slot, DevelopmentCard card) throws BadCardPositionException, BadSlotNumberException {
        checkSlotNumber(slot);

        if (card == null)
            return;

        /*
         * if the slot is empty and the card has the minimum level or the card is a level above the top card of the slot
         * adds the card to the slot; otherwise, throws BadCardPositionException.
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
     * @param slot slot number of the card we want to take (between 0 and nSlots)
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
     * Takes all the cards (list) from a slot number
     *
     * @param slot slot number (between 0 and nSlots)
     * @return a list of all the cards present in the slot
     * @throws BadSlotNumberException if the slot number doesn't correspond to a real slot
     */
    public List<DevelopmentCard> getCardsFromSlot(int slot) throws BadSlotNumberException {
        checkSlotNumber(slot);
        return new ArrayList<>(slots.get(slot));
    }

    /**
     * Checks if the development card passed is placeable on the personal board checking
     * if there is already a card with a level inferior of the one to insert
     *
     * @param developmentCard the development card to check if is placeable
     * @return true if it can be placed on the board, false otherwise
     */
    public boolean isPlaceable(DevelopmentCard developmentCard) {
        for (int nSlot = 0; nSlot < nSlots; nSlot++) {
            try {
                if (developmentCard.isSuccessorOf(getTopCard(nSlot)))
                    return true;
            } catch (BadSlotNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
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

    @Override
    public String toString() {
        String toPrint = "";

        try {
            for (DevelopmentCard developmentCard : getCardsFromSlot(0)) {
                toPrint = toPrint.concat(developmentCard.toString() + "\n");
            }
            for (DevelopmentCard developmentCard : getCardsFromSlot(1)) {
                toPrint = toPrint.concat(developmentCard.toString() + "\n");
            }
            for (DevelopmentCard developmentCard : getCardsFromSlot(2)) {
                toPrint = toPrint.concat(developmentCard.toString() + "\n");
            }
        } catch (BadSlotNumberException e) {
            e.printStackTrace();
        }
        return toPrint;
    }
}
