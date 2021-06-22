package it.polimi.ingsw.server.model.Player;

/**
 * Exception if you try to add a type of resource in a shelf that is already in another shelf
 */
public class SameResInTwoShelvesException extends Exception{
    public SameResInTwoShelvesException (String message) {
        super(message);
    }
}
