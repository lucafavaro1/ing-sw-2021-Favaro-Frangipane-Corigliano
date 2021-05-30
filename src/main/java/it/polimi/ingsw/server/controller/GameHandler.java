package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.Events.GameEndedEvent;
import it.polimi.ingsw.common.Events.GameStartedEvent;
import it.polimi.ingsw.common.Events.NotifyRankingEvent;
import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintMarketTrayEvent;
import it.polimi.ingsw.common.viewEvents.PrintPlayerEvent;
import it.polimi.ingsw.server.GameClientHandler;
import it.polimi.ingsw.server.GameServer;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GameHandler class handles a single match instantiating a game and a controller.
 */
public class GameHandler extends Thread {
    private final List<GameClientHandler> clientHandlers = new ArrayList<>();
    private final Controller controller;
    private final Game game;
    private final int maxPlayers;
    private boolean running = false;

    /**
     * Constructor of the game handler
     *
     * @param num number of players for the game
     */
    public GameHandler(int num) {
        this.game = new Game(num);
        this.maxPlayers = num;
        this.controller = new Controller(game, this);
    }

    /**
     * adds a client handler to the list
     *
     * @param clientHandler client handler to add to the game
     * @return true if the number of players for the game is reached, false otherwise
     */
    public boolean addGameClientHandler(GameClientHandler clientHandler) {
        clientHandlers.add(clientHandler);

        return clientHandlers.size() == maxPlayers;
    }

    /**
     * For each human player in the game makes him choose the resources he can receive
     */
    public void prepareGame() {
        int resToChoose = 0;
        int faithToAdd = 0;
        HumanPlayer player;

        // choosing random the order of the players
        if (clientHandlers.size() > 1)
            Collections.shuffle(game.getPlayers());

        // choosing the first player
        game.getPlayers().get(0).setFirstPlayer(true);

        // TODO: parallelize the preparation
        for (int i = 0; i < clientHandlers.size(); i++) {
            // if we are in multiplayer give the initial resources or the initial faith
            player = (HumanPlayer) game.getPlayers().get(i);
            // increases the resources of the initial amount
            player.getFaithTrack().increasePos(faithToAdd);
            // makes the player choose the resources he wants
            for (int j = 0; j < resToChoose; j++) {
                player.getWarehouseDepots().tryAdding(Res_Enum.QUESTION.chooseResource(player));
            }

            // law of increasing of the initial faith and initial resources
            if (i != 0 && i % 2 == 0)
                faithToAdd++;
            else
                resToChoose++;

            // take 4 cards among the ones the player has to choose the cards to take
            List<LeaderCard> leaderCardsToChoose = new ArrayList<>(game.getLeaderCardDeck().removeCardsFromDeck(4));
            // make the player choose the two cards
            for (int j = 0; j < 2; j++) {
                LeaderCard leaderCardChosen = new MakePlayerChoose<>(leaderCardsToChoose).choose(player);
                leaderCardsToChoose.remove(leaderCardChosen);
                player.addLeaderCard(leaderCardChosen);
            }
        }
    }

    /**
     * Method to start the game for each player of the match
     */
    @Override
    public void run() {
        running = true;

        System.out.println("[SERVER] Sending first views");

        // sending the starting situation of the common boards to the view
        game.getEventBroker().post(new PrintDcBoardEvent(game), false);
        game.getEventBroker().post(new PrintMarketTrayEvent(game), false);

        // sending starting situation of the players to the view
        game.getPlayers().forEach(player -> game.getEventBroker().post(new PrintPlayerEvent(player), false));

        /*try {
            // TODO: change in something else?
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        // TODO: change to PrintPlayerEvent?
        /*gameClientHandler.sendEvent(new PrintDevelopmentCardsEvent(gameClientHandler.getPlayer()));
        gameClientHandler.sendEvent(new PrintWarehouseEvent(gameClientHandler.getPlayer()));
        gameClientHandler.sendEvent(new PrintFaithtrackEvent(gameClientHandler.getPlayer()));
        gameClientHandler.sendEvent(new PrintStrongboxEvent(gameClientHandler.getPlayer()));
        gameClientHandler.sendEvent(new PrintLeaderCardsEvent(gameClientHandler.getPlayer()));*/


        // notifying the players that the game just started
        game.getEventBroker().post(new GameStartedEvent(), false);

        try {
            sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!game.isLastRound()) {
            game.getPlayers().forEach(Player::play);
        }

        running = false;

        // calculate the ranking
        List<Player> ranking = new ArrayList<>(game.getPlayers());
        ranking.sort(Comparator.comparingInt(Player::countPoints));
        Collections.reverse(ranking);

        game.getEventBroker().post(new NotifyRankingEvent(ranking.stream().map(Player::countPoints).collect(Collectors.toList()), ranking.stream().map(Player::getNickname).collect(Collectors.toList())), true);

        // deletes the game from the gameHandlers
        GameServer.getGameHandlers().entrySet()
                .removeIf(
                        entry -> (this.equals(entry.getValue()))
                );

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // send event of ending game
        game.getEventBroker().post(new GameEndedEvent(), false);
    }

    public boolean isRunning() {
        return running;
    }

    public List<GameClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public Controller getController() {
        return controller;
    }

    public Game getGame() {
        return game;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }
}
