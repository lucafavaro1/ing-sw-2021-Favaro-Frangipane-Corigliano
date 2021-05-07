package it.polimi.ingsw.client;

import it.polimi.ingsw.common.Events.Event;

public interface PlayerRequest {
    Event getRelativeEvent(UserInterface userInterface);
}
