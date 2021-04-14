package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Development.BadSlotNumberException;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.MoreProduction;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.*;

// TODO add javadoc, test
// TODO add method to assign leaderCards
public class HumanPlayer extends Player {
    private final WarehouseDepots warehouseDepots = new WarehouseDepots();
    private final StrongBox strongBox = new StrongBox();
    private final DcPersonalBoard developmentBoard;
    private final Production baseProduction = new Production(
            List.of(Res_Enum.QUESTION, Res_Enum.QUESTION),
            List.of(Res_Enum.QUESTION),
            0
    );

    private boolean actionDone;

    private final List<LeaderCard> leaderCards = new ArrayList<>();

    private final List<Production> productionsAdded = new ArrayList<>();

    // TODO: javadoc, test
    public HumanPlayer(Game game, int idPlayer) {
        super(game, idPlayer);

        developmentBoard = new DcPersonalBoard(game);
    }

    public int countPoints() {
        int total = 0;
        int resources;
        // punti delle carte sviluppo sulla plancia
        // punti delle carte leader
        total += this.getFaithTrack().getPosPoints();
        total += this.getFaithTrack().getBonusPoints();
        resources = this.strongBox.getRes(Res_Enum.COIN) + this.strongBox.getRes(Res_Enum.SERVANT) +
                this.strongBox.getRes(Res_Enum.SHIELD) + this.strongBox.getRes(Res_Enum.STONE);
        resources += this.getWarehouseDepots().get_dp(1).size() + this.getWarehouseDepots().get_dp(2).size() +
                this.getWarehouseDepots().get_dp(3).size();
        total += resources / 5;
        return total;
    }

    // TODO: javadoc, test
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

    // TODO: javadoc, test
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

    // TODO: javadoc, test
    public boolean addProduction(Production production) {
        if (production.isSatisfiable(this) && production.isAvailable()) {
            productionsAdded.add(production);
            production.setAvailable(false);
            return true;
        }

        return false;
    }

    // TODO: javadoc, test
    public boolean deleteProduction(Production production) {
        if (!production.isAvailable()) {
            productionsAdded.remove(production);
            production.setAvailable(true);
            return true;
        }

        return false;
    }

    // TODO: javadoc, test
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

    // TODO: javadoc, test
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
                        ((PlusSlot) leaderCard.getCardAbility()).removeRes(toRemove);
                    }
                }

                // remove resources from the strongbox
                try {
                    strongBox.useRes(res_enum, quantity);
                } catch (NotEnoughResourcesException e) {
                    System.out.println(e.getMessage());
                }
            });

            return true;
        } else
            return false;
    }

    // TODO: javadoc, test
    public Map<Res_Enum, Integer> getAvailableResources() {

        Map<Res_Enum, Integer> totalResources = getTotalResources();
        Map<Res_Enum, Integer> productionResources = new HashMap<>();

        // calculating all the resources that that the player is using for the production
        productionsAdded.forEach(production -> Res_Enum.getFrequencies(production.getResourcesReq())
                .forEach(
                        (res_enum, quantity) -> productionResources.merge(res_enum, quantity, Integer::sum)
                ));

        // subtracting to the total resources the resources blocked for the production
        Arrays.stream(Res_Enum.values()).forEach(res_enum -> totalResources.merge(res_enum, productionResources.get(res_enum), (a, b) -> a - b));

        return totalResources;
    }

    // TODO: javadoc, test
    public void addLeaderCard(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    @Override
    // TODO develop
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

    public void setActionDone() {
        this.actionDone = true;
    }
}
