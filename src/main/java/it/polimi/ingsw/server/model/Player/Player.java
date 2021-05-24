package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.server.model.Game;

/**
 * Abstract class that models a player of the game
 */
public abstract class Player implements EventHandler {
    protected final Game game;
    protected final int IdPlayer;
    protected String nickname;
    protected FaithTrack faithTrack;
    protected boolean firstPlayer;

    public Player(Game game, int idPlayer) {
        this.game = game;
        IdPlayer = idPlayer;
        faithTrack = new FaithTrack(game);
    }

    public FaithTrack getFaithTrack() {
        return faithTrack;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setFaithTrack(FaithTrack faithTrack) {
        this.faithTrack = faithTrack;
    }

    public abstract void play();

    public abstract int countPoints();
}
