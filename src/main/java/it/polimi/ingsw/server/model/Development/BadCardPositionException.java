package it.polimi.ingsw.server.model.Development;

/**
 * thrown when a card is being put in a bad position
 */
public class BadCardPositionException extends Exception {
    public BadCardPositionException (String message) {
        super(message);
    }
}
