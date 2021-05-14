package it.polimi.ingsw.client.cli;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Class representing the cli user interface, extends the abstract userinterface (see that for methods javadoc)
 */
public class CLIUserInterface extends UserInterface {
    public CLIUserInterface(EventBroker eventBroker) {
        super(eventBroker);
    }

    @Override
    public synchronized int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose) {
        BufferedReader myObj = new BufferedReader(new InputStreamReader(System.in));
        int chosen = -1;
        List<?> toBeChosen = makePlayerChoose.getToBeChosen();

        // printing a nice message
        StringBuilder message = new StringBuilder(makePlayerChoose.getMessage() + "\n");
        message.append("Choose one of the following" + "\n");
        for (int i = 0; i < toBeChosen.size(); i++) {
            message.append(i + 1).append(")").append(toBeChosen.get(i)).append("\n");
        }
        System.out.print(message);

        do {
            System.out.println("Insert a number between 1 and " + (toBeChosen.size()) + ": ");
            try {
                chosen = Integer.parseInt(myObj.readLine()) - 1;
            } catch (NumberFormatException | IOException ignored) {
            }
        } while (chosen < 0 || chosen > (toBeChosen.size() - 1));

        return chosen;
    }

    @Override
    public void printMessage(Object message) {
        String toPrint;
        // TODO check for another method instead of instanceof (maybe overloading with parameter List<?>)
        if (message instanceof List)
            toPrint = ((List<?>) message).stream().map(Object::toString).reduce((s, s2) -> s+"\n\n"+s2).orElse("");
        else
            toPrint = message.toString();

        System.out.println(toPrint);
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printFailMessage(String message) {
        System.err.println(message);
    }
}
