package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.common.Events.EndTurnClientEvent;
import it.polimi.ingsw.common.Events.FirstPlayerEvent;
import it.polimi.ingsw.common.Events.StartTurnEvent;
import it.polimi.ingsw.server.GameClientHandler;
import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.MoreProduction;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.*;
import java.util.stream.Collectors;

// TODO: send view Leader cards, warehouse, DcPersonalBoard, StrongBoc
/**
 * Class that represents the human player
 */
public class HumanPlayer extends Player {
    private boolean actionDone;
    private GameClientHandler gameClientHandler;
    private final WarehouseDepots warehouseDepots;
    private final StrongBox strongBox;
    private final DcPersonalBoard developmentBoard;
    private final Production baseProduction = new Production(
            List.of(Res_Enum.QUESTION, Res_Enum.QUESTION),
            List.of(Res_Enum.QUESTION),
            0
    );
    private final List<LeaderCard> leaderCards = new ArrayList<>();
    private final List<Production> productionsAdded = new ArrayList<>();
    private boolean playing = false;

    /**
     * Constructor of a human player
     *
     * @param game     Game which the player belongs to
     * @param idPlayer the id of the player created
     */
    public HumanPlayer(Game game, int idPlayer) {
        super(game, idPlayer);

        strongBox = new StrongBox(this);
        warehouseDepots = new WarehouseDepots(this);
        developmentBoard = new DcPersonalBoard(this);
    }

    /**
     * Counts the victory points of the player
     *
     * @return the victory points gained by the player
     */
    public int countPoints() {
        int total = 0;
        int resources = 0;

        // development cards points
        total += List.of(0, 1, 2).stream().map(slot -> {
            try {
                return developmentBoard.getCardsFromSlot(slot).stream()
                        .map(DevelopmentCard::getCardVictoryPoints)
                        .reduce(0, Integer::sum);
            } catch (BadSlotNumberException e) {
                System.out.println(e.getMessage());
            }
            return 0;
        }).reduce(0, Integer::sum);

        // leader cards points
        total += leaderCards.stream()
                .filter(LeaderCard::isEnabled)
                .map(LeaderCard::getCardVictoryPoints)
                .reduce(0, Integer::sum);

        // faith track points
        total += this.getFaithTrack().getPosPoints();
        total += this.getFaithTrack().getBonusPoints();

        // getting resources from the strongbox
        resources += Res_Enum.getList(this.strongBox.getAllRes()).size();

        // getting resources from the warehouse shelves
        resources += List.of(1, 2, 3).stream()
                .map(dp -> warehouseDepots.get_dp(dp).size())
                .reduce(0, Integer::sum);

        // getting resources from the leader cards
        resources += leaderCards.stream()
                .filter(leaderCard -> leaderCard.getCardAbility()
                        .getAbilityType()
                        .equals(Abil_Enum.SLOT)
                ).map(leaderCard -> ((PlusSlot) leaderCard.getCardAbility()).getResource().size())
                .reduce(0, Integer::sum);

        return total + resources / 5;
    }

    /**
     * Calculates the total resources owned by the player
     *
     * @return a map of all the resources owned by the player
     */
    public Map<Res_Enum, Integer> getTotalResources() {
        // putting into allRes the resources of the strongBox
        Map<Res_Enum, Integer> allRes = new HashMap<>(strongBox.getAllRes());

        // summing the resources of the warehouse into the allRes
        for (int i = 1; i <= 3; i++) {
            Map<Res_Enum, Integer> dpRes = Res_Enum.getFrequencies(warehouseDepots.get_dp(i));

            Arrays.stream(Res_Enum.values()).forEach(res_enum ->
                    allRes.merge(res_enum, dpRes.get(res_enum), Integer::sum)
            );
        }

        // summing the resources of the leader cards
        leaderCards.stream().filter(leaderCard ->
                leaderCard.getCardAbility().getAbilityType() == Abil_Enum.SLOT
        ).map(leaderCard -> (PlusSlot) leaderCard.getCardAbility())
                .forEach(plusSlot ->
                        allRes.merge(plusSlot.getResType(), plusSlot.getResource().size(), Integer::sum)
                );

        return allRes;
    }

    /**
     * Used in order to get all the available resources excluding the one that has to be used for the
     * already added productions
     *
     * @return a map of the available resources
     */
    public Map<Res_Enum, Integer> getAvailableResources() {
        Map<Res_Enum, Integer> totalResources = getTotalResources();
        Map<Res_Enum, Integer> productionResources = new HashMap<>();

        // calculating all the resources that that the player is using for the production
        productionsAdded.forEach(production -> Res_Enum.getFrequencies(production.getResourcesReq())
                .forEach(
                        (res_enum, quantity) -> productionResources.merge(res_enum, quantity, Integer::sum)
                ));

        // subtracting to the total resources the resources blocked for the production
        for (Res_Enum res_enum : productionResources.keySet()) {
            totalResources.merge(res_enum, productionResources.get(res_enum), (a, b) -> a - b);
        }

        return totalResources;
    }

    //TODO add javadoc, test
    public List<Production> getAvailableProductions() {
        List<Production> productionsAvailable = new ArrayList<>();

        // checking for the base production
        if (baseProduction.isAvailable())
            productionsAvailable.add(baseProduction);

        // checking for the productions from the development cards
        for (int i = 0; i < 3; i++) {
            try {
                if (developmentBoard.getTopCard(i) != null && developmentBoard.getTopCard(i).getProduction().isAvailable())
                    productionsAvailable.add(developmentBoard.getTopCard(i).getProduction());
            } catch (BadSlotNumberException e) {
                e.printStackTrace();
            }
        }

        // checking for the productions from the leader cards
        getEnabledLeaderCards(Abil_Enum.PRODUCTION).forEach(leaderCard ->
                productionsAvailable.add(((MoreProduction) leaderCard.getCardAbility()).getProduction())
        );

        return productionsAvailable;
    }

    /**
     * Switches all the possible productions to "available" and clears the list of productions
     */
    public void clearProductions() {
        productionsAdded.forEach(production -> production.setAvailable(true));
        productionsAdded.clear();
    }

    /**
     * Adds a specific production to the list of productions if the resources available are enough
     *
     * @param production production to add
     * @return true if the production is both available and the player has still resources available
     */
    public boolean addProduction(Production production) {
        if (production != null && production.isAvailable() && production.isSatisfiable(this)) {
            productionsAdded.add(production);
            production.setAvailable(false);
            return true;
        }

        return false;
    }

    /**
     * Deletes an already added production from the list of productions to do
     *
     * @param production production to delete
     * @return true if the production has been removed, false otherwise
     */
    public boolean deleteProduction(Production production) {
        if (production != null && !production.isAvailable()) {
            productionsAdded.remove(production);
            production.setAvailable(true);
            return true;
        }

        return false;
    }

    /**
     * Used to get all the available deposits that contains the passed resource
     *
     * @param res_enum type of resource to search for
     * @return the list of deposits that contains that resource
     */
    public List<Deposit> getDepositsWithResource(Res_Enum res_enum) {
        List<Deposit> deposits = new ArrayList<>();

        // if the warehouse contains that resource, adds it to the list of deposit available
        if (warehouseDepots.contains(res_enum))
            deposits.add(warehouseDepots);

        // checks for each enabled leader card if there is any resource of the type wanted
        for (LeaderCard leaderCard : leaderCards) {
            if (
                    leaderCard.isEnabled() &&
                            leaderCard.getCardAbility().getAbilityType() == Abil_Enum.SLOT &&
                            ((PlusSlot) leaderCard.getCardAbility()).getResource().contains(res_enum)
            ) {
                deposits.add((PlusSlot) leaderCard.getCardAbility());
            }
        }

        // checks if in the strongbox there is the resource wanted
        if (strongBox.getRes(res_enum) > 0)
            deposits.add(strongBox);

        return deposits;
    }

    /**
     * Adds a leader card to the list of leader cards assigned to the player
     *
     * @param leaderCard leader card to add
     */
    public void addLeaderCard(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    /**
     * Method that returns the list of enabled leader cards owned by the player with the leader ability passed
     *
     * @param abil_enum ability type of the card wanted
     * @return a list of leader cards with the passed leader ability
     */
    public List<LeaderCard> getEnabledLeaderCards(Abil_Enum abil_enum) {
        return leaderCards.stream()
                .filter(leaderCard -> leaderCard.isEnabled() &&
                        leaderCard.getCardAbility().getAbilityType().equals(abil_enum)
                ).collect(Collectors.toList());
    }

    // TODO add javadoc
    public synchronized void endTurn() {
        playing = false;
        gameClientHandler.sendEvent(new EndTurnClientEvent());
        clearProductions();
        notifyAll();
    }

    public WarehouseDepots getWarehouseDepots() {
        return warehouseDepots;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }

    public List<LeaderCard> getLeaderCards() {
        return leaderCards;
    }

    public DcPersonalBoard getDevelopmentBoard() {
        return developmentBoard;
    }

    public boolean isActionDone() {
        return actionDone;
    }

    public void setActionDone() {
        this.actionDone = true;
    }

    public List<Production> getProductionsAdded() {
        return productionsAdded;
    }

    public void setGameClientHandler(GameClientHandler gameClientHandler) {
        this.gameClientHandler = gameClientHandler;
    }

    public GameClientHandler getGameClientHandler() {
        return gameClientHandler;
    }

    public Production getBaseProduction() {
        return baseProduction;
    }

    public boolean isPlaying() {
        return playing;
    }


    @Override
    public void setFirstPlayer(boolean firstPlayer) {
        super.setFirstPlayer(firstPlayer);

        // notifying the right player that he's the first player
        game.getEventBroker().post(gameClientHandler, new FirstPlayerEvent(), false);
    }

    @Override
    // TODO javadoc
    public synchronized void play() {
        playing = true;
        actionDone = false;
        gameClientHandler.sendEvent(new StartTurnEvent());

        while (playing) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}