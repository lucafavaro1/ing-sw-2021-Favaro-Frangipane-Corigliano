package it.polimi.ingsw.server.model.Player;

/**
 * Exception if you try to mix different types of resources into the same shield of the warehousedepots
 */
public class MixedResourcesException extends Exception {

    public MixedResourcesException(String message) {
        super(message);
    }

}
