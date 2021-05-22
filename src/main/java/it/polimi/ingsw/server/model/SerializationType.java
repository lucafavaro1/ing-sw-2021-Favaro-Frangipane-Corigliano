package it.polimi.ingsw.server.model;

import it.polimi.ingsw.common.Events.Discard;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Development.DcPersonalBoard;
import it.polimi.ingsw.server.model.Development.DevelopmentCard;
import it.polimi.ingsw.server.model.Leader.LeaderAbility;
import it.polimi.ingsw.server.model.Leader.LeaderCard;
import it.polimi.ingsw.server.model.Player.StrongBox;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import it.polimi.ingsw.server.model.RequirementsAndProductions.CardRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;

public enum SerializationType {
    DC_BOARD(DcBoard.class),
    DC_PERSONAL_BOARD(DcPersonalBoard.class),
    DEVELOPMENT_CARD(DevelopmentCard.class),
    LEADER_CARD(LeaderCard.class),
    LEADER_ABILITY(LeaderAbility.class),
    STRONG_BOX(StrongBox.class),
    WAREHOUSE(WarehouseDepots.class),
    DISCARD(Discard.class),
    RES_REQUIREMENTS(ResRequirements.class),
    CARD_REQUIREMENTS(CardRequirements.class),
    PRODUCTION(Production.class);

    private final Class<?> type;

    SerializationType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }

}
