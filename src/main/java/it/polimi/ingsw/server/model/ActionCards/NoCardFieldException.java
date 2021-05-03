package it.polimi.ingsw.server.model.ActionCards;

/**
 * Thrown if the value wanted is absent
 */
public class NoCardFieldException extends Exception {
    public NoCardFieldException(String message) {
        super(message);
    }
}
