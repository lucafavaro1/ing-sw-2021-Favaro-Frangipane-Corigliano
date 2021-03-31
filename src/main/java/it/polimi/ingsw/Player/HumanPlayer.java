package it.polimi.ingsw.Player;

import it.polimi.ingsw.Leader.LeaderCard;

public class HumanPlayer extends Player{

    private boolean firstPlayer;
    private WarehouseDepots warehouseDepots;
    private StrongBox strongBox;
    private LeaderCard[] leaderCards;
    //private DcBoard developmentBoard;             // da sbloccare quando implementato DcBoard
    private Res_Enum[] extraSlots;



    public HumanPlayer(int idPlayer) {
        super(idPlayer);
    }

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
