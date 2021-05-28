package it.polimi.ingsw.server.model.Development;

/**
 * Enumeration that models the different types/colours of development card
 */
public enum TypeDevCards_Enum {
    BLUE,
    YELLOW,
    PURPLE,
    GREEN;
    public String ANSI_RESET = "\u001B[0m";
    public String ANSI_GREY = "\u001B[37m";
    public String ANSI_GREEN = "\u001B[32m";
    public String ANSI_RED = "\u001B[91m";
    public String ANSI_YELLOW = "\u001B[93m";
    public String ANSI_BLUE = "\u001B[94m";
    public String ANSI_PURPLE = "\u001B[95m";
    public String ANSI_WHITE = "\u001B[97m";





    public String toColoredString(){
        if(this.equals(TypeDevCards_Enum.BLUE)){
            return ANSI_BLUE + "BLUE" + ANSI_RESET;
        }
        else if(this.equals(TypeDevCards_Enum.YELLOW)){
            return ANSI_YELLOW + "YELLOW" + ANSI_RESET;
        }
        else if(this.equals(TypeDevCards_Enum.GREEN)){
            return ANSI_GREEN + "GREEN" + ANSI_RESET;
        }
        else{
            return ANSI_PURPLE + "PURPLE" + ANSI_RESET;
        }
    }
}

