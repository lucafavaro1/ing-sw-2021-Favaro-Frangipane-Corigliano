package it.polimi.ingsw.Player;

import java.util.*;

/**
 * Class representing the faith track object.
 */
public class FaithTrack {
    private int trackPos;
    private boolean PopeSpace;
    private boolean Vatican;
    private int VaticanSection;
    private int posPoints;
    private int bonusPoints;
    private boolean[] SecAsFirst;

    /**
     * Faith track constructor.
     */
    public FaithTrack() {
        trackPos = 0;   // le posizioni vanno da 0 a 24
        PopeSpace = false;
        Vatican = false;
        VaticanSection = 0; // 0 se deve ancora raggiungere la prima section , poi 1,2,3 in base all sezione
        posPoints = 0;
        bonusPoints = 0;
        SecAsFirst = new boolean[3];
        Arrays.fill(SecAsFirst, Boolean.FALSE);
    }

    /**
     * Moving forward the faith marker.
     *
     * @param n number of steps forward to do on the faithtrack
     */
    public void increasePos(int n) {
        // incremento la posizione
        if (trackPos + n < 25)
            trackPos += n;
        else
            trackPos = 24;

        // faccio i controlli se sono in una sezione vaticano e quale
        if (trackPos > 4 && trackPos < 9) {
            Vatican = true;
            VaticanSection = 1;
        } else if (trackPos > 11 && trackPos < 17) {
            Vatican = true;
            VaticanSection = 2;
        } else if (trackPos > 18) {
            Vatican = true;
            VaticanSection = 1;
        } else
            Vatican = false;

        // setto il PopeSpace
        if (trackPos == 8 || trackPos == 16 || trackPos == 24)
            PopeSpace = true;
        else
            PopeSpace = false;

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
        //if (PopeSpace && Vatican && SecAsFirst[VaticanSection + 1])
        // qui bisogna invocare la chiamata per tutti i player! ????????????????????????????????????????
    }

    /**
     * After receiving a Vatican Report Call, check your faith marker position to see if you can obtain bonus points
     *
     * @param section provides the number of the section in the faith track (1-2-3) on which the report was called
     */
    // un altro player ha chiamato il "rapporto al vaticano" perchè ha raggiunto lo spazio "section"
    public void VaticanReport(int section) {
        if (VaticanSection == section && Vatican)
            bonusPoints += (section + 1);
        SecAsFirst[section - 1] = false;
    }

    public int getTrackPos() {
        return trackPos;
    }

    public boolean isPopeSpace() {
        return PopeSpace;
    }

    public boolean isVatican() {
        return Vatican;
    }

    public int getVaticanSection() {
        return VaticanSection;
    }

    public int getPosPoints() {
        return posPoints;
    }

    public int getBonusPoints() {
        return bonusPoints;
    }
}
