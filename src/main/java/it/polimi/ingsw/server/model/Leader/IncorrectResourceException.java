package it.polimi.ingsw.server.model.Leader;

/**
 * Exception thrown when the player tries to put into the leader card slots a material of a different type than the one
 * indicated by the leader card
 */
public class IncorrectResourceException extends Exception {
    public IncorrectResourceException(String message){
        super(message);
    }
}
