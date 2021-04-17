package it.polimi.ingsw.server.model.Market;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * The tests below are supposed to verify the main movements of the market tray
 * <p>
 * The first test
 * <p>
 * The same happens with the shiftColUp test, but now every position is inverted due to shifting columns, not rows
 */
public class MarketTrayTest {
    /**
     * testing if the generated market tray is valid
     */
    @Test
    public void generateTrayTest() {
        MarketTray marketTray = new MarketTray();

        marketTray.generateTray();

        List<Marble_Enum> marbles = new ArrayList<>();

        for (MarketMarble[] marketMarbles : marketTray.getMatrix()) {
            marbles.addAll(
                    Arrays.stream(marketMarbles)
                            .map(MarketMarble::getMarbleColor)
                            .collect(Collectors.toList())
            );
        }

        marbles.add(marketTray.getFreeball().getMarbleColor());

        assertEquals(4, Collections.frequency(marbles, Marble_Enum.WHITE));
        assertEquals(2, Collections.frequency(marbles, Marble_Enum.BLUE));
        assertEquals(2, Collections.frequency(marbles, Marble_Enum.GREY));
        assertEquals(2, Collections.frequency(marbles, Marble_Enum.PURPLE));
        assertEquals(2, Collections.frequency(marbles, Marble_Enum.YELLOW));
        assertEquals(1, Collections.frequency(marbles, Marble_Enum.RED));
    }

    /**
     * verifies that every possible movements of the rows is possible ( shifts of every row and shift of a row
     * that was already shifted before );
     * it works comparing the result of the shift with a copy of the tray before the shift itself, verifying that
     * every marble in the position[x][i] is what was in the position [x][i+1] before the movement and that the
     * new freeBall is now the marble that was in position [x][0] before.
     */
    @Test
    public void shiftRowLeft() {
        MarketTray newTray = new MarketTray();
        newTray.generateTray();
        MarketTray copyTray = new MarketTray();
        copyTray.generateTray();
        copyTray.copyMatrix(newTray);

        newTray.shiftRowLeft(1);
        try {
            for (int i = 0; i <= 2; i++) {
                assertEquals(newTray.getMatrix()[1][i].getMarbleColor(), copyTray.getMatrix()[1][i + 1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[1][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception e) {
            fail();
        }
        copyTray.shiftRowLeft(1);

        newTray.shiftRowLeft(2);
        try {
            for (int i = 0; i <= 2; i++) {
                assertEquals(newTray.getMatrix()[2][i].getMarbleColor(), copyTray.getMatrix()[2][i + 1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception e) {
            fail();
        }
        copyTray.shiftRowLeft(2);

        newTray.shiftRowLeft(1);
        try {
            for (int i = 0; i <= 2; i++) {
                assertEquals(newTray.getMatrix()[1][i].getMarbleColor(), copyTray.getMatrix()[1][i + 1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[1][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception e) {
            fail();
        }
        copyTray.shiftRowLeft(1);

        newTray.shiftRowLeft(0);
        try {
            for (int i = 0; i <= 2; i++) {
                assertEquals(newTray.getMatrix()[0][i].getMarbleColor(), copyTray.getMatrix()[0][i + 1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[0][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception e) {
            fail();
        }
        copyTray.shiftRowLeft(0);
    }

    /**
     * verifies that every possible movements of the column is possible ( shifts of every column and shift of a column
     * that was already shifted before );
     * it works comparing the result of the shift with a copy of the tray before the shift itself, verifying that
     * every marble in the position[i][y] is what was in the position [i+1][y] before the movement and that the
     * new freeBall is now the marble that was in position [0][y] before.
     */
    @Test
    public void shiftColUp() {
        MarketTray newTray = new MarketTray();
        newTray.generateTray();
        MarketTray copyTray = new MarketTray();
        copyTray.generateTray();
        copyTray.copyMatrix(newTray);
        newTray.shiftColUp(1);
        try {
            for (int i = 0; i <= 1; i++) {
                assertEquals(newTray.getMatrix()[i][1].getMarbleColor(), copyTray.getMatrix()[i + 1][1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][1].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception ignored) {

        }
        copyTray.shiftColUp(1);

        newTray.shiftColUp(3);
        try {
            for (int i = 0; i <= 1; i++) {
                assertEquals(newTray.getMatrix()[i][3].getMarbleColor(), copyTray.getMatrix()[i + 1][3].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception ignored) {

        }
        copyTray.shiftColUp(3);

        newTray.shiftColUp(1);
        try {
            for (int i = 0; i <= 1; i++) {
                assertEquals(newTray.getMatrix()[i][1].getMarbleColor(), copyTray.getMatrix()[i + 1][1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][1].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception ignored) {

        }
        copyTray.shiftColUp(1);

        newTray.shiftColUp(2);
        try {
            for (int i = 0; i <= 1; i++) {
                assertEquals(newTray.getMatrix()[i][2].getMarbleColor(), copyTray.getMatrix()[i + 1][2].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][2].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception ignored) {

        }
        copyTray.shiftColUp(2);

        newTray.shiftColUp(0);
        try {
            for (int i = 0; i <= 1; i++) {
                assertEquals(newTray.getMatrix()[i][0].getMarbleColor(), copyTray.getMatrix()[i + 1][0].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][0].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        } catch (Exception ignored) {

        }
        copyTray.shiftColUp(0);

    }

    /**
     * testing if the row returned contains the same resources of the real one
     */
    @Test
    public void getRowTest() {
        MarketTray newTray = new MarketTray();

        newTray.generateTray();
        for (int i = 0; i < 3; i++) {
            assertEquals(Arrays.asList(newTray.getMatrix()[i]), newTray.getRow(i));
        }
    }

    /**
     * testing if the column returned contains the same resources of the real one
     */
    @Test
    public void getColumnTest() {
        MarketTray newTray = new MarketTray();

        newTray.generateTray();
        for (int i = 0; i < 4; i++) {
            List<MarketMarble> marbles = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                marbles.add(newTray.getMatrix()[j][i]);
            }
            assertEquals(marbles, newTray.getColumn(i));
        }
    }
}