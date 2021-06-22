package it.polimi.ingsw.server.model.Player;

/**
 * Exception if there is not enough space in the warehousedepots
 */
public class NotEnoughSpaceException extends Exception{
    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
