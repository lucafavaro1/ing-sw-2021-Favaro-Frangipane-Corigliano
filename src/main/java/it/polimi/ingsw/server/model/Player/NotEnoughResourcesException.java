package it.polimi.ingsw.server.model.Player;

public class NotEnoughResourcesException extends Exception {

    public NotEnoughResourcesException(String message) {
        super(message);
    }
}
