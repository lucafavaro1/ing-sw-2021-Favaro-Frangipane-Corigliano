package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Player.FaithTrack;

/**
 * Event to call the vatican report for all players of the match
 */
public class VaticanReportEvent extends Event {
    private final int section;

    public VaticanReportEvent(int section) {
        eventType = Events_Enum.VATICAN_REPORT;
        this.section = section;
    }

    @Override
    public void handle(Object faithTrack) {
        ((FaithTrack) faithTrack).vaticanReport(section);
    }
}