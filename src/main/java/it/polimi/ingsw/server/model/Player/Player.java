package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Game;

// TODO add javadoc
abstract public class Player {
    protected final Game game;
    protected final int IdPlayer;
    protected final FaithTrack faithTrack;
    protected boolean firstPlayer;

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

    // TODO: delete?
    public boolean checkVictory() {
        return faithTrack.isPopeSpace() && faithTrack.isVatican() && faithTrack.getTrackPos() == 24;
    }

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(boolean firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Game getGame() {
        return game;
    }

    public abstract boolean play();

}
