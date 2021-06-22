package it.polimi.ingsw.server.model.Player;

/**
 * Exception if you try to buy something you can't afford
 */
public class NotEnoughResourcesException extends Exception {

    public NotEnoughResourcesException(String message) {
        super(message);
    }
}
