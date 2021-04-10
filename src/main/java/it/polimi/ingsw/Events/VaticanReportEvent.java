package it.polimi.ingsw.Events;

import it.polimi.ingsw.Player.FaithTrack;

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