package it.polimi.ingsw.server.model.Player;

public class NotEnoughSpaceException extends Exception{
    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
