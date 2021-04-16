package it.polimi.ingsw.server.model.Market;

import it.polimi.ingsw.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.WhiteMarble;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class implements the market tray and its methods
 */
public class MarketTray {
    private MarketMarble[][] matrix;
    private MarketMarble freeball;

    /**
     * Executes the market action, taking the resources from a line, converting it and returning the resources converted
     * TODO: test
     *
     * @param player     player that takes the marbles from the market
     * @param horizontal if the line to get is horizontal or vertical (aka row or column)
     * @param toGet      the number of the row or column to get
     * @return the list of resources converted from the line taken
     */
    public List<Res_Enum> marketAction(HumanPlayer player, boolean horizontal, int toGet) {
        List<MarketMarble> marblesTaken;
        List<Res_Enum> resources = new ArrayList<>();

        // takes the marbles chosen
        if (horizontal) {
            marblesTaken = getRow(toGet);
            shiftRowLeft(toGet);
        } else {
            marblesTaken = getColumn(toGet);
            shiftColUp(toGet);
        }

        // takes the list of cards with the White Marble Ability
        List<WhiteMarble> whiteMarbleCards = player.getLeaderCards().stream().filter(leaderCard ->
                leaderCard.isEnabled() && leaderCard.getCardAbility().getAbilityType().equals(Abil_Enum.WHITE_MARBLE)
        ).map(leaderCard -> (WhiteMarble) leaderCard.getCardAbility()).collect(Collectors.toList());

        // for each market marble taken we convert the resource to the relative ResEnum
        for (MarketMarble marble : marblesTaken) {
            if (marble.getMarbleColor().equals(Marble_Enum.WHITE) && !whiteMarbleCards.isEmpty()) {
                // if the color of the marble is white and the player has some Leader Card for the market
                if (whiteMarbleCards.size() == 1) {
                    // takes automatically the resource
                    resources.add(whiteMarbleCards.get(0).getResourceType());
                } else {
                    // makes the player choose the leader card to use
                    resources.add(
                            (new MakePlayerChoose<>(whiteMarbleCards)).choose(player).getResourceType()
                    );
                }
            } else {
                Optional.ofNullable(marble.convertRes(player))
                        .ifPresent(resources::add);
            }
        }

        return resources;
    }

    /**
     * Method use to get a row of marbles from the market
     * TODO test
     *
     * @param x used to specify the row to return
     * @return row of marbles
     */
    public ArrayList<MarketMarble> getRow(int x) {
        ArrayList<MarketMarble> row = new ArrayList<>();
        for (int i = 0; i <= 2; i++) {
            row.add(matrix[x][i]);
        }
        return row;
    }

    /**
     * Method use to get a column of marbles from the market
     * TODO test
     *
     * @param y used to specify the column to return
     * @return column of marbles
     */
    public ArrayList<MarketMarble> getColumn(int y) {
        ArrayList<MarketMarble> row = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            row.add(matrix[i][y]);
        }
        return row;

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
     * TODO test
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
}
