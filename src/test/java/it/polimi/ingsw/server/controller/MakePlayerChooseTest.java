package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Player.HumanPlayer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MakePlayerChooseTest {

    /**
     * setting output in order not to print on terminal the messages
     */
    @Before
    public void setUpOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * restoring input and output to the standard
     */
    @After
    public void restoreSystemInputOutput() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * method that, provided some lines of text, passes this when a input is required
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * test in order to check if the object chosen is returned
     */
    @Test
    public void choose() {
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        provideInput("0\n");

        assertEquals(
                object1,
                (new MakePlayerChoose<>(List.of(object1, object2, object3))).choose((HumanPlayer) null)
        );
    }

    /**
     * test in order to check if a wrong number
     */
    @Test
    public void chooseWrong() {
        Object object1 = new Object();
        Object object2 = new Object();
        Object object3 = new Object();

        provideInput("as\n3\n0\n");

        assertEquals(
                object1,
                (new MakePlayerChoose<>(List.of(object1, object2, object3))).choose((HumanPlayer) null)
        );
    }
}