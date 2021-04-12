package it.polimi.ingsw.server.model.Development;

/**
 * thrown if the slot number doesn't correspond to a real slot number
 */
public class BadSlotNumberException extends Exception{
    public BadSlotNumberException(String message) {
        super(message);
    }
}
