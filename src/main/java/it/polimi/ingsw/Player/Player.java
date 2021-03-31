package it.polimi.ingsw.Player;

abstract class Player {
    private int IdPlayer;
    private FaithTrack faithTrack;


    public Player(int idPlayer) {
        IdPlayer = idPlayer;
        faithTrack = new FaithTrack();
    }

    public int getIdPlayer() {
        return IdPlayer;
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
    }

    public boolean checkVictory() {
        return faithTrack.isPopeSpace() && faithTrack.isVatican() && faithTrack.getTrackPos() == 24;
    }

    /*
    public boolean play() {
     }
     */

}
