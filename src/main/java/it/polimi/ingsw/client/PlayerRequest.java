package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.Event;

/**
 * Interface of the player request to the server
 */
public interface PlayerRequest {
    Event getRelativeEvent(UserInterface userInterface);
}
