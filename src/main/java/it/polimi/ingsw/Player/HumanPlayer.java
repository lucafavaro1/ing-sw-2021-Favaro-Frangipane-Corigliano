package it.polimi.ingsw.Player;

import it.polimi.ingsw.Development.DcPersonalBoard;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.Leader.LeaderCard;
import it.polimi.ingsw.RequirementsAndProductions.Res_Enum;

import java.util.HashMap;

public class HumanPlayer extends Player {

    private final WarehouseDepots warehouseDepots = new WarehouseDepots(this);
    private final StrongBox strongBox = new StrongBox(this);
    private final HashMap<Res_Enum,Integer> totalResources = new HashMap<>();
    private final DcPersonalBoard developmentBoard;
    private LeaderCard[] leaderCards;
    private Res_Enum[] extraSlots;

    public HumanPlayer(Game game, int idPlayer) {
        super(game, idPlayer);
        totalResources.put(Res_Enum.SHIELD,0);
        totalResources.put(Res_Enum.STONE,0);
        totalResources.put(Res_Enum.SERVANT,0);
        totalResources.put(Res_Enum.COIN,0);
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

    //public QuestionResource BaseProduction(Res_Enum res1, Res_Enum res2) {
    //}

    //public DevelopmentCard buy(Tuple tuple) {
    //}

    // TODO play() to be developed
    @Override
    public boolean play() {
        return false;
    }

    public WarehouseDepots getWarehouseDepots() {
        return warehouseDepots;
    }

    public StrongBox getStrongBox() {
        return strongBox;
    }

    public LeaderCard[] getLeaderCards() {
        return leaderCards;
    }

    public DcPersonalBoard getDevelopmentBoard() {
        return developmentBoard;
    }

    public HashMap<Res_Enum, Integer> getTotalResources() {
        return totalResources;
    }
}
