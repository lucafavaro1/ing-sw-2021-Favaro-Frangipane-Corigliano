package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.common.Events.GameEndedEvent;
import it.polimi.ingsw.common.Events.NotifyRankingEvent;
import it.polimi.ingsw.common.Events.PreparationEndedEvent;
import it.polimi.ingsw.common.networkCommunication.Pingable;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * GameHandler class handles a single match instantiating a game and a controller.
 */
public class GameHandler extends Thread {
    private final List<GameClientHandler> clientHandlers = new ArrayList<>();
    private final Map<HumanPlayer, Runnable> preparations = new HashMap<>();
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
    public synchronized void prepareGame() {
        int resToChoose = 0;
        int faithToAdd = 0;

        // choosing random the order of the players
        if (clientHandlers.size() > 1)
            Collections.shuffle(game.getPlayers());

        // setting the first player
        game.getPlayers().get(0).setFirstPlayer(true);

        // waiting for the players to print the common board before sending the request for the resources and cards to choose
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // preparation parallelized for all the players
        for (int i = 0; i < clientHandlers.size(); i++) {
            HumanPlayer player = (HumanPlayer) game.getPlayers().get(i);

            // give the initial resources, the initial faith and the leader cards to the players
            preparations.put(player, new Preparation(
                    this,
                    player,
                    resToChoose,
                    faithToAdd,
                    new ArrayList<>(game.getLeaderCardDeck().removeCardsFromDeck(4))
            ));

            (new Thread(preparations.get(player))).start();

            // law of increasing of the initial faith and initial resources
            if (i % 2 != 0)
                faithToAdd++;
            else
                resToChoose++;
        }

        // waiting for all the players to finish their preparation
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the player and relative preparation thread. if the game isn't running and all the players connected have finished the preparation, starts the game
     *
     * @param player The player that finished the preparation
     */
    public synchronized void deletePreparation(HumanPlayer player) {
        preparations.remove(player);

        // if all players have done the preparation or if all the remaining players are disconnected
        if (clientHandlers.size() == maxPlayers && (preparations.isEmpty() ||
                preparations.keySet().stream().map(HumanPlayer::getGameClientHandler).noneMatch(GameClientHandler::isConnected))
        ) {
            if (running) {
                // sending the preparation event ended only to the player reconnected if the game is already running
                game.getEventBroker().post(player.getGameClientHandler(), new PreparationEndedEvent(game), false);
            } else {
                // starting the game if it's not running
                game.getEventBroker().post(new PreparationEndedEvent(game), false);
                notifyAll();
                start();
            }
            System.out.println("Preparation sent");
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

        // sending starting situation of the players to the view (in the right order as they will play)
        game.getPlayers().forEach(player -> {
            game.getEventBroker().post(new PrintPlayerEvent(player), true);
            try {
                sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // alternating of the rounds among players
        while (!game.isLastRound()) {
            game.getPlayers().forEach(Player::play);
        }

        running = false;

        // calculate the ranking
        List<Player> ranking = new ArrayList<>(game.getPlayers());
        ranking.sort(Comparator.comparingInt(Player::countPoints));
        Collections.reverse(ranking);

        // sending the ranking at the end of the game
        game.getEventBroker().post(
                new NotifyRankingEvent(
                        ranking.stream().map(Player::countPoints).collect(Collectors.toList()),
                        ranking.stream().map(Player::getNickname).collect(Collectors.toList())
                ),
                true
        );

        // deletes the game from the gameHandlers
        GameServer.getGameHandlers()
                .entrySet()
                .removeIf(
                        entry -> (this.equals(entry.getValue()))
                );

        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        clientHandlers.forEach(Pingable::stopCheckConnection);
        // send event of ending game
        game.getEventBroker().post(new GameEndedEvent(), false);
    }

    /**
     * Game is running?
     * @return true if it is running, false otherwise
     */
    public boolean isRunning() {
        return running;
    }

    public List<GameClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public Game getGame() {
        return game;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Runnable getPreparation(HumanPlayer player) {
        return preparations.get(player);
    }
}

/**
 * Inner class used to keep track of the preparation phase of each player.
 * Used in order to make the player do the preparation phase even if the game has already begun
 */
class Preparation extends Thread {
    private final GameHandler gameHandler;
    private final HumanPlayer player;
    private final int nResources;
    private int nFaith;
    private final List<LeaderCard> leaders;

    Preparation(GameHandler gameHandler, HumanPlayer player, int nResources, int nFaith, List<LeaderCard> leaders) {
        this.gameHandler = gameHandler;
        this.player = player;
        this.nResources = nResources;
        this.nFaith = nFaith;
        this.leaders = leaders;
    }

    @Override
    public void run() {
        player.getLeaderCards().clear();
        player.getWarehouseDepots().clear();

        List<LeaderCard> leaderCardsToChoose = new ArrayList<>(leaders);

        player.getFaithTrack().increasePos(nFaith);
        nFaith = 0;

        // makes the player choose the resources he wants
        for (int j = 0; j < nResources; j++) {
            player.getWarehouseDepots().tryAdding(Res_Enum.QUESTION.chooseResource(player));
        }

        // make the player choose the two cards
        for (int j = 0; j < 2; j++) {
            LeaderCard leaderCardChosen = new MakePlayerChoose<>(
                    "Choose the leader cards you want",
                    leaderCardsToChoose
            ).choose(player);

            leaderCardsToChoose.remove(leaderCardChosen);
            player.addLeaderCard(leaderCardChosen);
        }

        player.setPreparation();
        gameHandler.deletePreparation(player);
        gameHandler.getGame().getEventBroker().post(new PrintPlayerEvent(player), false);
        System.out.println("Preparation: ended for " + player.getNickname());
    }
}