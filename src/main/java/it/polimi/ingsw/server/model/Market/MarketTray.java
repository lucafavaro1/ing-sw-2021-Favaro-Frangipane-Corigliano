package it.polimi.ingsw.server.model.Market;

import java.util.*;

/**
 * This class implements the market tray and its methods
 */
public class MarketTray {
    private MarketMarble[][] matrix = new MarketMarble[3][4];
    private MarketMarble freeball;

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
        return "freeball: " + getFreeball() + "\n" +
                "\t   1\t  2\t    3\t   4" +
                " \n 1: " + "[" + getRow(0).get(0) + "]" + "[" + getRow(0).get(1) + "]" +
                "[" + getRow(0).get(2) + "]" + "[" + getRow(0).get(3) + "]" +
                " \n 2: " + "[" + getRow(1).get(0) + "]" + "[" + getRow(1).get(1) + "]" +
                "[" + getRow(1).get(2) + "]" + "[" + getRow(1).get(3) + "]" +
                " \n 3: " + "[" + getRow(2).get(0) + "]" + "[" + getRow(2).get(1) + "]" +
                "[" + getRow(2).get(2) + "]" + "[" + getRow(2).get(3) + "]";
    }
}

