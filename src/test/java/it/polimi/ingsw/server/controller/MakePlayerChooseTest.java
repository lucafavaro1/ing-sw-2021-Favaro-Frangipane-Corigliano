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

}