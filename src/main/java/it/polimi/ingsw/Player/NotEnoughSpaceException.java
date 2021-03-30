package it.polimi.ingsw.Player;

public class NotEnoughSpaceException extends Exception{
    public NotEnoughSpaceException(String message) {
        super(message);
    }
}
