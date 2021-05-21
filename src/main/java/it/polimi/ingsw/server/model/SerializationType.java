package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Events.Discard;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;

public enum SerializationType {
    DC_BOARD(DcBoard.class),
    DC_PERSONAL_BOARD(DcPersonalBoard.class),
    DEVELOPMENT_CARD(DevelopmentCard.class),
    LEADER_CARD(LeaderCard.class),
    STRONG_BOX(StrongBox.class),
    WAREHOUSE(WarehouseDepots.class),
    DISCARD(Discard.class);

    private final Class<?> type;

    SerializationType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

}
