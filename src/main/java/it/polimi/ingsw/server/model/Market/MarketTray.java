package it.polimi.ingsw.server.model.Market;

import java.util.*;

/**
 * This class implements the market tray and its methods
 */
public class MarketTray {
    private MarketMarble[][] matrix = new MarketMarble[3][4];
    private MarketMarble freeball;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREY = "\u001B[37m";
    public static final String ANSI_RED = "\u001B[91m";
    public static final String ANSI_YELLOW = "\u001B[93m";
    public static final String ANSI_BLUE = "\u001B[94m";
    public static final String ANSI_PURPLE = "\u001B[95m";
    public static final String ANSI_WHITE = "\u001B[97m";

    public MarketTray() {
        generateTray();
    }

    /**
     * Method used to get a row of marbles from the market
     *
     * @param x used to specify the row to return
     * @return row of marbles
     */
    public ArrayList<MarketMarble> getRow(int x) {
        if (x < 0 || x > 2)
            throw new IllegalArgumentException("row number is invalid");

        ArrayList<MarketMarble> row = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            row.add(matrix[x][i]);
        }
        return row;
    }

    /**
     * Method used to get a column of marbles from the market
     *
     * @param y used to specify the column to return
     * @return column of marbles
     */
    public ArrayList<MarketMarble> getColumn(int y) {
        if (y < 0 || y > 3)
            throw new IllegalArgumentException("column number is invalid");

        ArrayList<MarketMarble> column = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            column.add(matrix[i][y]);
        }
        return column;

    }

    /**
     * Method used to shift a specified row of the market when the player decides to get resources from the market
     *
     * @param x specifies the row to shift
     */
    public void shiftRowLeft(int x) {
        MarketMarble newFreeBall = getMatrix()[x][0];
        System.arraycopy(matrix[x], 1, matrix[x], 0, 3);
        matrix[x][3] = this.getFreeball();
        this.setFreeball(newFreeBall);
    }

    /**
     * Method used to shift a specified column of the market when the player decides to get resources from the market
     *
     * @param y specifies the column to shift
     */
    public void shiftColUp(int y) {
        MarketMarble newFreeBall = matrix[0][y];
        for (int i = 0; i <= 1; i++) {
            matrix[i][y] = matrix[i + 1][y];
        }
        matrix[2][y] = this.getFreeball();
        setFreeball(newFreeBall);
    }

    /**
     * This method is helpful while generating the initial market tray
     *
     * @return a random marble
     */
    public MarketMarble randomMarble() {
        MarketMarble marble = new MarketMarble();

        // selects a random marble color from the enum
        marble.setMarbleColor(
                Arrays.asList(Marble_Enum.values())
                        .get((new Random()).nextInt(Marble_Enum.values().length))
        );

        return marble;
    }

    /**
     * This method is used to generate the tray at the beginning of the match
     */
    public void generateTray() {
        // initial list of the marbles to insert in the market
        List<Marble_Enum> marblesToPlace = new ArrayList<>(List.of(
                Marble_Enum.WHITE, Marble_Enum.WHITE, Marble_Enum.WHITE, Marble_Enum.WHITE,
                Marble_Enum.YELLOW, Marble_Enum.YELLOW,
                Marble_Enum.BLUE, Marble_Enum.BLUE,
                Marble_Enum.GREY, Marble_Enum.GREY,
                Marble_Enum.PURPLE, Marble_Enum.PURPLE,
                Marble_Enum.RED
        ));

        // shuffles the list of marbles to place
        Collections.shuffle(marblesToPlace);

        this.matrix = new MarketMarble[3][4];

        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 3; y++) {
                this.matrix[x][y] = new MarketMarble(marblesToPlace.remove(0));
            }
        }

        this.setFreeball(new MarketMarble(marblesToPlace.remove(0)));
    }

    /**
     * This method let you copy the matrix of a given tray. Mostly used for testing (see MarketTraTest class)
     * to compare a tray with its previous state after a shift.
     *
     * @param tray specifies the tray you want to copy the matrix from
     */
    public void copyMatrix(MarketTray tray) {
        for (int x = 0; x <= 2; x++) {
            for (int y = 0; y <= 3; y++) {
                this.matrix[x][y] = tray.getMatrix()[x][y];
            }
        }
        this.setFreeball(tray.getFreeball());
    }

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

    @Override
    public String toString() {
        return "\nFreeball: " + getFreeballColor(getFreeball())+ getFreeball() + ANSI_RESET+ "\n" +
                "       1       2       3       4"+

                " \n 1: " + "[" +
                getColor(0,0) + getRow(0).get(0) + ANSI_RESET + getSpaces(0,0) +  "]" + "[" +
                getColor(0,1) + getRow(0).get(1) + ANSI_RESET + getSpaces(0,1) + "]" + "[" +
                getColor(0,2) + getRow(0).get(2) + ANSI_RESET + getSpaces(0,2) + "]" + "[" +
                getColor(0,3) + getRow(0).get(3) + ANSI_RESET + getSpaces(0,3) + "]" + " \n 2: " + "[" +
                getColor(1,0) + getRow(1).get(0) + ANSI_RESET + getSpaces(1,0) + "]" + "[" +
                getColor(1,1) + getRow(1).get(1) + ANSI_RESET + getSpaces(1,1) + "]" + "[" +
                getColor(1,2) + getRow(1).get(2) + ANSI_RESET + getSpaces(1,2) + "]" + "[" +
                getColor(1,3) + getRow(1).get(3) + ANSI_RESET + getSpaces(1,3) + "]" + " \n 3: " + "[" +
                getColor(2,0) + getRow(2).get(0) + ANSI_RESET + getSpaces(2,0) + "]" + "[" +
                getColor(2,1) + getRow(2).get(1) + ANSI_RESET + getSpaces(2,1) + "]" + "[" +
                getColor(2,2) + getRow(2).get(2) + ANSI_RESET + getSpaces(2,2) + "]" + "[" +
                getColor(2,3) + getRow(2).get(3) + ANSI_RESET + getSpaces(2,3) + "]";
    }

    public String getColor(int row, int index){
        if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.WHITE)){
            return ANSI_WHITE;
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.BLUE)){
            return ANSI_BLUE;
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.RED)){
            return ANSI_RED;
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.YELLOW)){
            return ANSI_YELLOW;
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.PURPLE)){
            return ANSI_PURPLE;
        }
        else {
            return ANSI_GREY;
        }

    }
    public String getFreeballColor(MarketMarble marble){
        if(marble.getMarbleColor().equals(Marble_Enum.WHITE)){
            return ANSI_WHITE;
        }
        else if(marble.getMarbleColor().equals(Marble_Enum.BLUE)){
            return ANSI_BLUE;
        }
        else if(marble.getMarbleColor().equals(Marble_Enum.RED)){
            return ANSI_RED;
        }
        else if(marble.getMarbleColor().equals(Marble_Enum.YELLOW)){
            return ANSI_YELLOW;
        }
        else if(marble.getMarbleColor().equals(Marble_Enum.PURPLE)){
            return ANSI_PURPLE;
        }
        else {
            return ANSI_GREY;
        }

    }

    public String getSpaces(int row, int index){
        if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.WHITE)){
            return " ";
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.BLUE)){
            return "  ";
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.RED)){
            return "   ";
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.YELLOW)){
            return "";
        }
        else if(getRow(row).get(index).getMarbleColor().equals(Marble_Enum.PURPLE)){
            return "";
        }
        else {
            return "  ";
        }

    }
}

