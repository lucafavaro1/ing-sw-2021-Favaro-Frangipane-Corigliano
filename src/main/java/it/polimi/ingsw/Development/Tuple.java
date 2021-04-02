package it.polimi.ingsw.Development;

import java.util.Objects;

/**
 * Object that contains the information about the type and the level of a development card
 */
public class Tuple {
    TypeDevCards_Enum type;
    int level;

    public TypeDevCards_Enum getType() {
        return type;
    }

    public int getLevel() {
        return level;
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
        return type + "development card of level" + level;
    }
}
