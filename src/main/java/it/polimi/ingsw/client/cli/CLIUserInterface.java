package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CLIUserInterface extends UserInterface {
    public CLIUserInterface(EventBroker eventBroker) {
        super(eventBroker);
    }

    @Override
    public int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();

        // printing a nice message
        String message = makePlayerChoose.getMessage() + "\n";
        message += "Choose one of the following" + "\n";
        for (int i = 0; i < toBeChosen.size(); i++) {
            message += i + ")" + toBeChosen.get(i) + "\n";
        }
        System.out.print(message);

        do {
            System.out.println("Insert a number between 0 and " + (toBeChosen.size() - 1) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine());
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return chosen;
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }
}
