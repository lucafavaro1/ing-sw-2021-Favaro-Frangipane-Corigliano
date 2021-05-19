package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.FirstPlayerEvent;
import it.polimi.ingsw.server.model.Game;

/**
 * Abstract class that models a player of the game
 */
public abstract class Player implements EventHandler {
    protected final Game game;
    protected final int IdPlayer;
    protected String nickname;
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

    public boolean isFirstPlayer() {
        return firstPlayer;
    }

    // TODO: call this once the game is created
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

    public abstract void play();

    public abstract int countPoints();
}
