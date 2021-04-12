package it.polimi.ingsw.server.model.Leader;

/**
 * Exception thrown when the player is trying to add material to the leader card slots when they are full
 */
public class SlotIsFullException extends Exception {

    public SlotIsFullException(String message) {
        super(message);
    }

}
