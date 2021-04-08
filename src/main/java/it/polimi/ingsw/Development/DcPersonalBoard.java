package it.polimi.ingsw.Development;

import it.polimi.ingsw.Events.Events_Enum;
import it.polimi.ingsw.Game;

import java.util.*;

/**
 * Personal board on which the player puts the development cards he bought
 * TODO: test the class
 */
public class DcPersonalBoard {
    private final Game game;
    private final static int nSlots = 3;
    private final Map<Integer, TreeSet<DevelopmentCard>> slots = new HashMap<>();

    /**
     * constructor that creates the different slots of the board
     */
    public DcPersonalBoard(Game game) {
        this.game = game;

        for (int i = 0; i < nSlots; i++) {
            slots.put(i, new TreeSet<>());
        }
    }

    /**
     * Method in order to get the first card from a slot
     *
     * @param slot slot number of the card we want to take
     * @return the development card relative to the slot number passed
     * @throws BadSlotNumberException if the slot number doesn't correspond to a real slot
     */
    public DevelopmentCard getTopCard(int slot) throws BadSlotNumberException {
        checkSlotNumber(slot);
        return slots.get(slot).first();
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
     * adds a card to the slot
     *
     * @param slot slot number where we want to put the card
     * @param card the card to put in the slot
     * @throws BadCardPositionException The card can't be put in the slot passed
     * @throws BadSlotNumberException   the slot is non valid
     */
    public void addCardToSlot(int slot, DevelopmentCard card) throws BadCardPositionException, BadSlotNumberException {

        // adding the card anyway, even if there are no slots available
        // TODO: maybe we have to put this in the controller
        if(slots.keySet().stream().mapToInt(key -> slots.get(key).size()).sum() == 6){
            game.getEventBroker().post(Events_Enum.LAST_ROUND, false);
            slots.get(slot).add(card);
        }

        checkSlotNumber(slot);
        if (getTopCard(slot).getCardType().getLevel() == card.getCardType().getLevel() - 1)
            slots.get(slot).add(card);
        else
            throw new BadCardPositionException("Posizione non valida!");
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
