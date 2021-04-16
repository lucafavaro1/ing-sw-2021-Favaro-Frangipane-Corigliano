package it.polimi.ingsw.server.model.Player;

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
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.*;

// TODO add method to assign leaderCards, choose leader cards (first round?)
public class HumanPlayer extends Player {
    private boolean actionDone;
    private final WarehouseDepots warehouseDepots = new WarehouseDepots();
    private final StrongBox strongBox = new StrongBox();
    private final DcPersonalBoard developmentBoard;
    private final Production baseProduction = new Production(
            List.of(Res_Enum.QUESTION, Res_Enum.QUESTION),
            List.of(Res_Enum.QUESTION),
            0
    );

    private final List<LeaderCard> leaderCards = new ArrayList<>();
    private final List<Production> productionsAdded = new ArrayList<>();

    public HumanPlayer(Game game, int idPlayer) {
        super(game, idPlayer);

        developmentBoard = new DcPersonalBoard(game);
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
     * Switches all the possible productions to available and clears the list of productions
     * TODO: test, add call to this method in other events of other actions of the player
     */
    public void clearProductions() {
        // clearing base production
        baseProduction.setAvailable(true);

        // clearing development cards production
        Arrays.stream(new int[]{1, 2, 3})
                .forEach(slot -> {
                    try {
                        developmentBoard.getTopCard(slot).getProduction().setAvailable(true);
                    } catch (BadSlotNumberException e) {
                        e.printStackTrace();
                    }
                });

        // clearing leader cards production
        leaderCards.stream()
                .filter(leaderCard -> leaderCard.getCardAbility().getAbilityType() == Abil_Enum.PRODUCTION)
                .forEach(leaderCard ->
                        ((MoreProduction) leaderCard.getCardAbility()).getProduction().setAvailable(true)
                );

        productionsAdded.clear();
    }

    /**
     * adds a specific production to the list of productions to do if the resources available are enough
     * TODO test
     *
     * @param production production to add
     * @return true if the production is both available and the player has still resources available
     */
    public boolean addProduction(Production production) {
        if (production != null && production.isSatisfiable(this) && production.isAvailable()) {
            productionsAdded.add(production);
            production.setAvailable(false);
            return true;
        }

        return false;
    }

    /**
     * Deletes a already added production from the list of productions to do
     * TODO test
     *
     * @param production production to delete
     * @return true if the production has been removed, false otherwise
     */
    public boolean deleteProduction(Production production) {
        if (!production.isAvailable()) {
            productionsAdded.remove(production);
            production.setAvailable(true);
            return true;
        }

        return false;
    }

    /**
     * Activates the action of production
     * TODO test
     */
    public void activateProduction() {
        if (productionsAdded.isEmpty())
            return;

        for (Production production : productionsAdded) {
            // the player pays the production
            pay(Res_Enum.getFrequencies(production.getResourcesReq()));

            // the player receives the resources from the production
            Res_Enum.getFrequencies(production.getProductionResources()).forEach(strongBox::putRes);

            // the player receives the faith points from the productions
            getFaithTrack().increasePos(production.getCardFaith());

            clearProductions();
        }
        actionDone = true;
    }

    /**
     * Uses the amount of resources passed as parameter; takes the resources from the player firstly from the warehouse,
     * then from the Plus Slot leader cards, then from the strong box
     * TODO test
     * TODO: check if limitates the choosing of the player (maybe delete)
     *
     * @param resToPay resources to be payed
     * @return true if all the amount of resources has been removed, false otherwise
     */
    public boolean pay(Map<Res_Enum, Integer> resToPay) {
        if ((new ResRequirements(Res_Enum.getList(resToPay))).isSatisfiable(this)) {
            resToPay.forEach((res_enum, quantity) -> {
                // remove resources from the deposits
                quantity -= warehouseDepots.useRes(res_enum, quantity);

                // remove resources from the leader cards
                for (LeaderCard leaderCard : leaderCards) {
                    if (leaderCard.getCardAbility().getAbilityType() == Abil_Enum.SLOT &&
                            ((PlusSlot) leaderCard.getCardAbility()).getResType() == res_enum) {
                        int toRemove = Math.min(((PlusSlot) leaderCard.getCardAbility()).getResource().size(), quantity);
                        quantity -= toRemove;
                        ((PlusSlot) leaderCard.getCardAbility()).useRes(res_enum, toRemove);
                    }
                }

                // remove resources from the strongbox
                strongBox.useRes(res_enum, quantity);
            });

            return true;
        } else
            return false;
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

    /**
     * Used to get all the available deposits that contains the passed resource
     * TODO: test
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
     * adds a leader card to the list of leader cards assigned to the player
     *
     * @param leaderCard leader card to add
     */
    public void addLeaderCard(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    @Override
    // TODO develop, javadoc, test
    public boolean play() {
        actionDone = false;
        return false;
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
}