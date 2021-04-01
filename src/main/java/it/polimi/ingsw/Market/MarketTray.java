package it.polimi.ingsw.Market;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class implements the market tray and its methods
 */
public class MarketTray {
    private MarketMarble[][] matrix;
    private MarketMarble freeball;

    public MarketMarble[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(MarketMarble[][] matrix) {
        this.matrix = matrix;
    }

    public MarketMarble getFreeball() {
        return freeball;
    }

    public void setFreeball(MarketMarble freeball) {
        this.freeball = freeball;
    }


    /**
     * Method use to get a row of marbles from the market
     * @param x used to specify the row to return
     * @return row of marbles
     */
    public ArrayList<MarketMarble> getRow(int x){
        ArrayList<MarketMarble> row = new ArrayList<MarketMarble>();
        for(int i=0; i<=2;i++){
            row.add(matrix[x][i]);
        }
        return row;

    }

    /**
     * Method use to get a column of marbles from the market
     * @param y used to specify the column to return
     * @return column of marbles
     */
    public ArrayList<MarketMarble> getColumn(int y){
        ArrayList<MarketMarble> row = new ArrayList<MarketMarble>();
        for(int i=0; i<=3;i++){
            row.add(matrix[i][y]);
        }
        return row;

    }

    /**
     * Method used to shift a specified row of the market when the player decides to get resources from the market
     * @param x specifies the row to shift
     */
    public void shiftRowLeft(int x){
        MarketMarble newFreeBall = getMatrix()[x][0];
        for(int i=0; i<=2; i++){
            matrix[x][i]=matrix[x][i+1];
        }
        matrix[x][3]=this.getFreeball();
        this.setFreeball(newFreeBall);

    }

    /**
     * Method used to shift a specified column of the market when the player decides to get resources from the market
     * @param y specifies the column to shift
     */
    public void shiftColUp(int y){
        MarketMarble newFreeBall = matrix[0][y];
        for(int i=0; i<=1; i++){
            matrix[i][y]=matrix[i+1][y];
        }
        matrix[2][y]=this.getFreeball();
        setFreeball(newFreeBall);
   }

    /**
     * This method is helpful while generating the initial market tray
     * @return a random marble generated through a simple RNG
     */
    public MarketMarble randomMarble(){
        Random rand = new Random();
        int upperbound = 6;
        //generate random values from 0-5
        //0=WHITE, 1=RED, 2=BLUE, 3=YELLOW, 4=GREY, 5=PURPLE
        int int_random = rand.nextInt(upperbound);
       MarketMarble marble= new MarketMarble();
        if( int_random==0){
            marble.setMarbleColor(Marble_Enum.WHITE);
        }
        else if(int_random==1){
            marble.setMarbleColor(Marble_Enum.RED);
        }
        else if(int_random==2){
            marble.setMarbleColor(Marble_Enum.BLUE);
        }
        else if(int_random==3){
            marble.setMarbleColor(Marble_Enum.YELLOW);
        }
        else if(int_random==4){
            marble.setMarbleColor(Marble_Enum.GREY);
        }
        else if(int_random==5){
            marble.setMarbleColor(Marble_Enum.PURPLE);
        }

        return marble;
    }

    /**
     * This method is used to generate the tray at the beginning of the match
     */
    public void generateTray(){
        this.matrix=new MarketMarble[3][4];
        for(int x=0; x<=2; x++){
            for(int y=0; y<=3; y++){
                this.matrix[x][y]=randomMarble();
            }
        }
        this.setFreeball(randomMarble());
    }


    /**
     * This method lets you copy the matrix of a given tray. Mostly used for testing (see MarketTraTest class)
     * to compare a tray with its previous state after a shift.
     * @param tray specifies the tray you want to copy the matrix from
     */
    public void copyMatrix(MarketTray tray){
        for(int x=0; x<=2; x++){
            for(int y=0; y<=3; y++){
                this.matrix[x][y]=tray.getMatrix()[x][y];
            }
        }
        this.setFreeball(tray.getFreeball());

    }
}
