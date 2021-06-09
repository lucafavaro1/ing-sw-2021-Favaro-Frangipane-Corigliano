package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.server.model.Game;

/**
 * Abstract class that models a player of the game
 */
public abstract class Player implements EventHandler {
    protected final Game game;
    protected String nickname;
    protected boolean firstPlayer;
    protected FaithTrack faithTrack;

    public Player(Game game) {
        this.game = game;
        faithTrack = new FaithTrack(this);
    }

    /**
     * method that makes the player do its turn and returns only when he ends its turn
     */
    public abstract void play();

    /**
     * Counts the points gained by the player
     *
     * @return an integer representing the vicotory points of the player
     */
    public abstract int countPoints();

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
}
