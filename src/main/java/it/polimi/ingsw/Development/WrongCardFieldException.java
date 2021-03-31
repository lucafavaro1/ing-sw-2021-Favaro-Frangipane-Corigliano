package it.polimi.ingsw.Development;

public class WrongCardFieldException extends Exception {
    public WrongCardFieldException(){
        super("cardToDiscard field must be a TypeDevCards_Enum value for DISCARD_TWO_CARDS effect");
    }
}
