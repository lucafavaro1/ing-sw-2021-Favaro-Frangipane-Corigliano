package it.polimi.ingsw.server.model.Market;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The tests below are supposed to verify the two main movements of the market tray: rows and columns shifts.
 *
 * The first test verifies that every possible movements of the rows is possible ( shifts of every row and shift of
 * a row that was already shifted before ); it works comparing the result of the shift with a copy of the tray before the
 * shift itself, verifying that every marble in the position[x][i] is what was in the position [x][i+1] before the movement
 * and that the new freeBall is now the marble that was in position [x][0] before.
 *
 * The same happens with the shiftColUp test, but now every position is inverted due to shifting columns, not rows
 *
 */



public class MarketTrayTest {

    @Test
    public void shiftRowLeft() {
        MarketTray newTray= new MarketTray();
        newTray.generateTray();
        MarketTray copyTray = new MarketTray();
        copyTray.generateTray();
        copyTray.copyMatrix(newTray);
        newTray.shiftRowLeft(1);
        try{
            for(int i=0; i<=2;i++){
                assertEquals(newTray.getMatrix()[1][i].getMarbleColor(), copyTray.getMatrix()[1][i+1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[1][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){
            fail();
        }
        copyTray.shiftRowLeft(1);


        newTray.shiftRowLeft(2);
        try{
            for(int i=0; i<=2;i++){
                assertEquals(newTray.getMatrix()[2][i].getMarbleColor(), copyTray.getMatrix()[2][i+1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){
            fail();
        }
        copyTray.shiftRowLeft(2);

        newTray.shiftRowLeft(1);
        try{
            for(int i=0; i<=2;i++){
                assertEquals(newTray.getMatrix()[1][i].getMarbleColor(), copyTray.getMatrix()[1][i+1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[1][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){
            fail();
        }
        copyTray.shiftRowLeft(1);

        newTray.shiftRowLeft(0);
        try{
            for(int i=0; i<=2;i++){
                assertEquals(newTray.getMatrix()[0][i].getMarbleColor(), copyTray.getMatrix()[0][i+1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[0][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){
            fail();
        }
        copyTray.shiftRowLeft(0);











    }

    @Test
    public void shiftColUp() {
        MarketTray newTray= new MarketTray();
        newTray.generateTray();
        MarketTray copyTray = new MarketTray();
        copyTray.generateTray();
        copyTray.copyMatrix(newTray);
        newTray.shiftColUp(1);
        try{
            for(int i=0; i<=1;i++){
                assertEquals(newTray.getMatrix()[i][1].getMarbleColor(), copyTray.getMatrix()[i+1][1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][1].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){

        }
        copyTray.shiftColUp(1);

        newTray.shiftColUp(3);
        try{
            for(int i=0; i<=1;i++){
                assertEquals(newTray.getMatrix()[i][3].getMarbleColor(), copyTray.getMatrix()[i+1][3].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][3].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){

        }
        copyTray.shiftColUp(3);

        newTray.shiftColUp(1);
        try{
            for(int i=0; i<=1;i++){
                assertEquals(newTray.getMatrix()[i][1].getMarbleColor(), copyTray.getMatrix()[i+1][1].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][1].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){

        }
        copyTray.shiftColUp(1);

        newTray.shiftColUp(2);
        try{
            for(int i=0; i<=1;i++){
                assertEquals(newTray.getMatrix()[i][2].getMarbleColor(), copyTray.getMatrix()[i+1][2].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][2].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){

        }
        copyTray.shiftColUp(2);

        newTray.shiftColUp(0);
        try{
            for(int i=0; i<=1;i++){
                assertEquals(newTray.getMatrix()[i][0].getMarbleColor(), copyTray.getMatrix()[i+1][0].getMarbleColor());
            }
            assertEquals(newTray.getMatrix()[2][0].getMarbleColor(), copyTray.getFreeball().getMarbleColor());
        }
        catch (Exception e){

        }
        copyTray.shiftColUp(0);






    }
}