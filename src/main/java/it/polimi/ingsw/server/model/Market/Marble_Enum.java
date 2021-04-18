package it.polimi.ingsw.server.model.Market;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

/**
 * Enum for the types of marbles in the market
 */
public enum Marble_Enum {
    BLUE(Res_Enum.SHIELD),
    YELLOW(Res_Enum.COIN),
    GREY(Res_Enum.STONE),
    PURPLE(Res_Enum.SERVANT),
    WHITE(null),
    RED(null);

    public Res_Enum getEquivalentResource() {
        return equivalentResource;
    }

    private final Res_Enum equivalentResource;

    Marble_Enum(Res_Enum equivalentResource) {
        this.equivalentResource = equivalentResource;
    }
}
