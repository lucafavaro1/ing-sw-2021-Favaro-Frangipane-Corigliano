package it.polimi.ingsw.Player;

import it.polimi.ingsw.Events.EventHandler;
import it.polimi.ingsw.Events.Events_Enum;
import it.polimi.ingsw.Game;

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
    private int bonusPoints;
    private boolean[] secAsFirst;

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
        bonusPoints = 0;
        secAsFirst = new boolean[3];
        Arrays.fill(secAsFirst, Boolean.TRUE);

        // registering to the event broker on the events we can handle
        game.getEventBroker().subscribe(this,
                EnumSet.of(Events_Enum.VATICAN_REPORT_1,
                        Events_Enum.VATICAN_REPORT_2,
                        Events_Enum.VATICAN_REPORT_3)
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
            if (trackPos + n < 25)
                trackPos += n;
            else
                trackPos = 24;

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
            if (trackPos == 8 || trackPos == 16 || trackPos == 24)
                popeSpace = true;
            else
                popeSpace = false;

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
            //TODO: Test vatican report
            if (popeSpace && secAsFirst[vaticanSection - 1]) {
                switch (vaticanSection) {
                    case 1:
                        game.getEventBroker().post(Events_Enum.VATICAN_REPORT_1, false);
                        break;
                    case 2:
                        game.getEventBroker().post(Events_Enum.VATICAN_REPORT_2, false);
                        break;
                    case 3:
                        game.getEventBroker().post(Events_Enum.VATICAN_REPORT_3, false);
                        break;
                }
            }
        }
    }

    /**
     * After receiving a Vatican Report Call, check your faith marker position to see if you can obtain bonus points
     *
     * @param section provides the number of the section in the faith track (1-2-3) on which the report was called
     */
    // un altro player ha chiamato il "rapporto al vaticano" perchè ha raggiunto lo spazio "section"
    public void vaticanReport(int section) {
        if (vaticanSection == section && vatican)
            bonusPoints += (section + 1);

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

    public int getBonusPoints() {
        return bonusPoints;
    }

    @Override
    public void handleEvent(Events_Enum event) {
        switch (event) {
            case VATICAN_REPORT_1:
                vaticanReport(1);
                break;
            case VATICAN_REPORT_2:
                vaticanReport(2);
                break;
            case VATICAN_REPORT_3:
                vaticanReport(3);
                break;
            case PLUS_ONE_FAITH:
                increasePos(1);
                break;
            case PLUS_TWO_FAITH:
                increasePos(2);
                break;
        }
    }
}
