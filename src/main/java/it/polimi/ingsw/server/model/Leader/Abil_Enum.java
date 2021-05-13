package it.polimi.ingsw.server.model.Leader;

/**
 * Enum that describes the possible types of leader abilities
 */
public enum Abil_Enum {
    DISCOUNT(ResDiscount.class),
    SLOT(PlusSlot.class),
    WHITE_MARBLE(WhiteMarble.class),
    PRODUCTION(MoreProduction.class);

    public Class<?> getEventClass() {
        return equivalentClass;
    }

    private final Class<?> equivalentClass;

    Abil_Enum(Class<?> equivalentClass) {
        this.equivalentClass = equivalentClass;
    }
}
