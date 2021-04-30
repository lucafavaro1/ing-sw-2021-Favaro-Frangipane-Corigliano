package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * TODO: modify when are ready the interfaces to the console game and graphic game
 * TODO: test MakePlayerChoose
 * Class that permits the player to choose one between different objects
 *
 * @param <T> the type of objects to use
 */
public class MakePlayerChoose<T> {
    private final List<T> toBeChosen;

    public MakePlayerChoose(List<T> toBeChosen) {
        this.toBeChosen = toBeChosen;
    }

    /**
     * method that makes the player choose which one of the elements in the list chooses
     * TODO test
     *
     * @param player player that has to choose the element
     * @return the element chosen by the player
     */
    public T choose(HumanPlayer player) {
        // TODO: replace with the internet communication (maybe using an event)
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;

        System.out.println("Choose one of the following elements:\n");
        for (int i = 0; i < toBeChosen.size(); i++) {
            System.out.println(i + ")" + toBeChosen.get(i));
        }

        do {
            System.out.println("Insert a number between 0 and " + (toBeChosen.size() - 1) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine());
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return toBeChosen.get(chosen);
    }
}
