package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.*;

public class ShuffleActionEventTest {

    /**
     * testing if the shuffle event really shuffles the action card deck
     */
    @Test
    public void shuffleHandleTest() {
        Game game = new Game(1);
        CPUPlayer cpu = (CPUPlayer) game.getPlayers().get(1);

        // taking the deck as it is at the beginning of the round
        List<ActionCard> oldDeck = List.copyOf(cpu.getActionCardDeck().getDeck());
        game.getEventBroker().post(new ShuffleActionEvent(), true);
        assertNotEquals(oldDeck, cpu.getActionCardDeck().getDeck());
    }
}