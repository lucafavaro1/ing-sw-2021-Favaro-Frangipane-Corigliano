package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.Events.LastRoundEvent;
import it.polimi.ingsw.common.Events.VaticanReportEvent;
import it.polimi.ingsw.server.model.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;

/**
 * Class representing the faith track object.
 */
public class FaithTrack implements EventHandler {
    private final Game game;

    private int trackPos;
    private boolean popeSpace;
    private boolean vatican;
    private int vaticanSection;
    private int posPoints;

    private int bonusPoints[];
    private final boolean[] secAsFirst;

    /**
     * Faith track constructor.
     */
    public FaithTrack(Game game) {
        this.game = game;

        trackPos = 0;   // le posizioni vanno da 0 a 24
        popeSpace = false;
        vatican = false;
        vaticanSection = 0; // 0 se deve ancora raggiungere la prima section , poi 1,2,3 in base all sezione
        posPoints = 0;
        bonusPoints = new int[3];
        Arrays.fill(bonusPoints,0);
        secAsFirst = new boolean[3];
        Arrays.fill(secAsFirst, Boolean.TRUE);

        // registering to the event broker on the events we can handle
        game.getEventBroker().subscribe(this,
                EnumSet.of(Events_Enum.VATICAN_REPORT, Events_Enum.ADD_FAITH)
        );
    }

    /**
     * Moving forward the faith marker.
     *
     * @param n number of steps forward to do on the faithtrack
     */
    public void increasePos(int n) {
        if (n > 1) {
            for (int i = 0; i < n; i++)
                increasePos(1);
        } else {
            // incremento la posizione
            if (trackPos + n < 24)
                trackPos += n;
            else {
                trackPos = 24;
                game.getEventBroker().post(new LastRoundEvent(), true);
            }

            // faccio i controlli se sono in una sezione vaticano e quale
            if (trackPos > 4 && trackPos < 9) {
                vatican = true;
                vaticanSection = 1;
            } else if (trackPos > 11 && trackPos < 17) {
                vatican = true;
                vaticanSection = 2;
            } else if (trackPos > 18) {
                vatican = true;
                vaticanSection = 3;
            } else
                vatican = false;

            // setto il PopeSpace
            popeSpace = trackPos == 8 || trackPos == 16 || trackPos == 24;

            // controllo punti extra
            if (trackPos <= 8)
                posPoints = trackPos / 3;
            else if (trackPos == 24)
                posPoints = 20;
            else if (trackPos > 20)
                posPoints = 16;
            else if (trackPos > 17)
                posPoints = 12;
            else if (trackPos > 14)
                posPoints = 9;
            else if (trackPos > 11)
                posPoints = 6;
            else
                posPoints = 4;

            // controllo se devo fare rapporto al vaticano
            if (popeSpace && secAsFirst[vaticanSection - 1]) {
                game.getEventBroker().post(new VaticanReportEvent(vaticanSection), true);
            }
        }
    }

    /**
     * After receiving a Vatican Report Call, check your faith marker position to see if you can obtain bonus points
     *
     * @param section provides the number of the section in the faith track (1-2-3) on which the report was called
     */
    public void vaticanReport(int section) {
        if (vaticanSection == section && vatican)
            bonusPoints[section-1] = (section + 1);

        secAsFirst[section - 1] = false;
    }

    public int getTrackPos() {
        return trackPos;
    }

    public boolean isPopeSpace() {
        return popeSpace;
    }

    public boolean isVatican() {
        return vatican;
    }

    public int getVaticanSection() {
        return vaticanSection;
    }

    public int getPosPoints() {
        return posPoints;
    }

    public int[] getBonusPoints() {
        return bonusPoints;
    }

    public boolean[] getSecAsFirst() {
        return secAsFirst;
    }

    @Override
    public String toString() {
        return "FaithTrack{" + "\n" +
                "\tcurrent track position =" + trackPos + "\n" +
                "\tat popeSpace ? =" + popeSpace + "\n" +
                "\tin vatican? =" + vatican + "\n" +
                "\tcurrent vaticanSection =" + vaticanSection + "\n" +
                "\tposition points =" + posPoints + "\n" +
                "\tbonusPoints =" + bonusPoints[0]+bonusPoints[1]+bonusPoints[2] + "\n" +
                "\tvatican section as first [1 = still not called, 0 = someone called] =" + Arrays.toString(secAsFirst) + "\n" +
                '}';
    }
}
