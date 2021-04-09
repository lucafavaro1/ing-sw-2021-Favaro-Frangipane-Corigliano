package it.polimi.ingsw.Player;

import it.polimi.ingsw.Game;

abstract public class Player {
    private final Game game;
    private boolean firstPlayer;
    private final int IdPlayer;
    private final FaithTrack faithTrack;

    public Player(Game game, int idPlayer) {
        this.game = game;
        IdPlayer = idPlayer;
        faithTrack = new FaithTrack(game);
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

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public abstract boolean play();


}
