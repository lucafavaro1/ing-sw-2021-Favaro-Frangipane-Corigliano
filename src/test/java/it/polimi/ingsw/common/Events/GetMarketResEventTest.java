package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.WhiteMarble;
import it.polimi.ingsw.server.model.Market.Marble_Enum;
import it.polimi.ingsw.server.model.Market.MarketMarble;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.CardRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum.*;
import static org.junit.Assert.assertEquals;

public class GetMarketResEventTest {
    /**
     * testing if the event could be generated with valid parameters
     */
    @Test
    public void constructorNoExceptionsTest() {
        new GetMarketResEvent(true, 0);
        new GetMarketResEvent(false, 0);

        new GetMarketResEvent(true, 2);
        new GetMarketResEvent(false, 3);
    }

    /**
     * testing if the event throws an exception with negative line to get
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionsNegativeTest() {
        new GetMarketResEvent(true, -1);
    }

    /**
     * testing if the event throws an exception with above limit line to get
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionsOverLimitRowTest() {
        new GetMarketResEvent(true, 3);
    }

    /**
     * testing if the event throws an exception with above limit line to get
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructorExceptionsOverLimitColumnTest() {
        new GetMarketResEvent(false, 4);
    }

    /**
     * converting resources no leader and no faith
     */
    @Test
    public void convertMarblesSimple() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        GetMarketResEvent event = new GetMarketResEvent(true, 0);
        assertEquals(List.of(SHIELD, SERVANT, COIN), event.convertMarbles(player, List.of(
                new MarketMarble(Marble_Enum.BLUE),
                new MarketMarble(Marble_Enum.PURPLE),
                new MarketMarble(Marble_Enum.WHITE),
                new MarketMarble(Marble_Enum.YELLOW))));
    }

    /**
     * converting resources faith
     */
    @Test
    public void convertMarblesFaith() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        GetMarketResEvent event = new GetMarketResEvent(true, 0);
        assertEquals(List.of(SERVANT), event.convertMarbles(player, List.of(
                new MarketMarble(Marble_Enum.RED),
                new MarketMarble(Marble_Enum.PURPLE)
        )));
        assertEquals(1, player.getFaithTrack().getTrackPos());
    }

    /**
     * converting resources 1 white marble leader card
     */
    @Test
    public void convertMarbles1WhiteMarble() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        // creating and adding the leader card
        LeaderCard leaderCard = new LeaderCard(
                new WhiteMarble(SERVANT),
                new CardRequirements(List.of()),
                new ResRequirements(List.of()),
                0
        );

        player.addLeaderCard(leaderCard);
        leaderCard.enable(player);

        GetMarketResEvent event = new GetMarketResEvent(true, 0);

        assertEquals(List.of(SERVANT), event.convertMarbles(player, List.of(
                new MarketMarble(Marble_Enum.WHITE)
        )));
    }

    /* TODO fix once we can test makePlayerChoose
    /**
     * converting resources 2 white marble leader card
     *
    @Test
    public void convertMarbles2WhiteMarble() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        // creating and adding the leader cards
        LeaderCard leaderCardServant = new LeaderCard(
                new WhiteMarble(SERVANT),
                new CardRequirements(List.of()),
                new ResRequirements(List.of()),
                0
        );

        LeaderCard leaderCardCoin = new LeaderCard(
                new WhiteMarble(COIN),
                new CardRequirements(List.of()),
                new ResRequirements(List.of()),
                0
        );

        player.addLeaderCard(leaderCardServant);
        player.addLeaderCard(leaderCardCoin);
        leaderCardServant.enable(player);
        leaderCardCoin.enable(player);

        GetMarketResEvent event = new GetMarketResEvent(true, 0);

        // simulating the player choosing the coin
        provideInput("1\n");
        assertEquals(List.of(COIN), event.convertMarbles(player, List.of(
                new MarketMarble(Marble_Enum.WHITE))
        ));
    }
    */

    /* TODO fix once we can test makePlayerChoose
    /**
     * converting resources 2 white marble leader card
     *
    @Test
    public void convertMarblesAll() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        // creating and adding the leader cards
        LeaderCard leaderCardServant = new LeaderCard(
                new WhiteMarble(SERVANT),
                new CardRequirements(List.of()),
                new ResRequirements(List.of()),
                0
        );

        LeaderCard leaderCardCoin = new LeaderCard(
                new WhiteMarble(COIN),
                new CardRequirements(List.of()),
                new ResRequirements(List.of()),
                0
        );

        player.addLeaderCard(leaderCardServant);
        player.addLeaderCard(leaderCardCoin);
        leaderCardServant.enable(player);
        leaderCardCoin.enable(player);

        GetMarketResEvent event = new GetMarketResEvent(true, 0);

        // simulating the player choosing the coin
        provideInput("1\n");
        assertEquals(List.of(COIN, COIN), event.convertMarbles(player, List.of(
                new MarketMarble(Marble_Enum.RED),
                new MarketMarble(Marble_Enum.YELLOW),
                new MarketMarble(Marble_Enum.WHITE)
        )));
        assertEquals(1, player.getFaithTrack().getTrackPos());
    }
    */

    /* TODO fix once we can test makePlayerChoose
    /**
     * adding all resources
     *
    @Test
    public void processResourcesTest() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        GetMarketResEvent event = new GetMarketResEvent(true, 0);

        provideInput("1\n");
        event.processResources(player, COIN);
        provideInput("1\n");
        event.processResources(player, COIN);
        provideInput("1\n");
        event.processResources(player, COIN);
        provideInput("1\n");
        event.processResources(player, SERVANT);
        provideInput("0\n");
        event.processResources(player, COIN);
    }
    */

    // TODO finish tests
    @Test
    public void handleTest() {
    }

    /**
     * setting output in order not to print on terminal the messages
     */
    @Before
    public void setUpOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * restoring input and output to the standard
     */
    @After
    public void restoreSystemInputOutput() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * method that, provided some lines of text, passes this when a input is required
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}