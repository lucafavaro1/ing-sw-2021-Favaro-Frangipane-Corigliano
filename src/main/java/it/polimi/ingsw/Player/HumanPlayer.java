package it.polimi.ingsw.Player;

import it.polimi.ingsw.Leader.LeaderCard;

public class HumanPlayer extends Player{

    private boolean firstPlayer;
    private WarehouseDepots warehouseDepots = new WarehouseDepots();
    private StrongBox strongBox = new StrongBox();
    private LeaderCard[] leaderCards;
    //private DcPersonalBoard developmentBoard;             // da sbloccare quando implementato DcBoard
    private Res_Enum[] extraSlots;

    public HumanPlayer(int idPlayer) {
        super(idPlayer);
    }

    public int countPoints() {
        int total = 0;
        int resources;
        // punti delle carte sviluppo sulla plancia
        // punti delle carte leader
        total+=this.getFaithTrack().getPosPoints();
        total+=this.getFaithTrack().getBonusPoints();
        resources = this.strongBox.getRes(Res_Enum.COIN) + this.strongBox.getRes(Res_Enum.SERVANT) +
                this.strongBox.getRes(Res_Enum.SHIELD) + this.strongBox.getRes(Res_Enum.STONE);
        resources += this.getWarehouseDepots().get_dp(1).size() + this.getWarehouseDepots().get_dp(2).size() +
                this.getWarehouseDepots().get_dp(3).size();
        total+= resources/5;
        return total;
    }

    //public QuestionResource BaseProduction(Res_Enum res1, Res_Enum res2) {
    //}

    //public DevelopmentCard buy(Tuple tuple) {
    //}

    public boolean isFirstPlayer() {
        return firstPlayer;
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
}
