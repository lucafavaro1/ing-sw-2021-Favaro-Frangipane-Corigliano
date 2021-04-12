package it.polimi.ingsw.server.model.Development;

import java.util.Objects;

/**
 * Object that contains the information about the type and the level of a development card
 */
public class Tuple {
    private final TypeDevCards_Enum type;
    private final int level;

    private static final int minLevel = 1;
    private static final int maxLevel = 3;

    /**
     * Constructor of a Tuple for a development card
     *
     * @param type type of the development card
     * @param level level of the development card
     */
    public Tuple(TypeDevCards_Enum type, int level) {
        this.type = type;
        this.level = level;
    }

    public TypeDevCards_Enum getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public static int getMinLevel() {
        return minLevel;
    }

    public static int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple tuple = (Tuple) o;
        return level == tuple.level &&
                type == tuple.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, level);
    }

    @Override
    public String toString() {
        return type + " level " + level;
    }
}
