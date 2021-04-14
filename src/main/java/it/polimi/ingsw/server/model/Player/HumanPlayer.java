package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.*;

// TODO add javadoc, test
// TODO add method to assign leaderCards
public class HumanPlayer extends Player {

    private final WarehouseDepots warehouseDepots = new WarehouseDepots(this);
    private final StrongBox strongBox = new StrongBox(this);
    private final DcPersonalBoard developmentBoard;
    private final List<LeaderCard> leaderCards = new ArrayList<>();

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

    // TODO: test the method
    // TODO: add javadoc
    public Map<Res_Enum, Integer> totalResources() {
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

    //public QuestionResource BaseProduction(Res_Enum res1, Res_Enum res2) {
    //}

    // TODO: to be developed
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

    public void addLeaderCard(LeaderCard leaderCard) {
        leaderCards.add(leaderCard);
    }

    @Override
    // TODO play() to be developed
    public boolean play() {
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
}
