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

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREY = "\u001B[37m";
    public static final String ANSI_RED = "\u001B[91m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[94m";
    public static final String ANSI_PURPLE = "\u001B[95m";
    public static final String ANSI_WHITE = "\u001B[97m";

    public Res_Enum getEquivalentResource() {
        return equivalentResource;
    }

    private final Res_Enum equivalentResource;

    Marble_Enum(Res_Enum equivalentResource) {
        this.equivalentResource = equivalentResource;
    }

    @Override
    public String toString(){
        if(this.equals(Marble_Enum.GREY)){
            return ANSI_GREY +"GREY" + ANSI_RESET;
        }
        else if(this.equals(Marble_Enum.YELLOW)){
            return ANSI_YELLOW +  "YELLOW" + ANSI_RESET;
        }
        else if(this.equals(Marble_Enum.RED)){
            return ANSI_RED + "RED" + ANSI_RESET;
        }
        else if(this.equals(Marble_Enum.PURPLE)){
            return ANSI_PURPLE + "PURPLE" + ANSI_RESET;
        }
        else if(this.equals(Marble_Enum.BLUE)){
            return ANSI_BLUE +  "BLUE" + ANSI_RESET;
        }
        else {
            return ANSI_WHITE + "WHITE" + ANSI_RESET;
        }
    }



}
