package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.viewEvents.PrintEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * abstract singleton class that represents the interface to the user interface, weather it is the CLI or the GUI
 */
public abstract class UserInterface implements EventHandler {
    private static UserInterface instance;
    private final EventBroker eventBroker;

    // Cached Model instances
    /**
     * Map of all the players of the game, with key the nickname of the players
     */
    protected final Map<String, HumanPlayer> players = new HashMap<>();
    protected DcBoard dcBoard;
    protected MarketTray marketTray;
    protected String myNickname;

    public static void newInstance(boolean cli, EventBroker eventBroker) {
        if (instance == null)
            instance = (cli ? new CLIUserInterface(eventBroker) : new GUIUserInterface(eventBroker));
    }

    public static UserInterface getInstance() {
        return instance;
    }

    protected UserInterface(EventBroker eventBroker) {
        this.eventBroker = eventBroker;
        eventBroker.subscribe(
                this,
                EnumSet.of(Events_Enum.PRINT_MESSAGE, Events_Enum.FAIL, Events_Enum.FIRST_PLAYER, Events_Enum.RANKING));
    }

    /**
     * method that deals with showing to the user the different options the player could choose
     *
     * @param makePlayerChoose the makePlayerChoose received
     * @return the option chosen by the user (corresponding index from the list passed)
     */
    public abstract int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose);

    public synchronized void choose(int chosen){}

    /**
     * method that shows a message on the screen
     *
     * @param message message to be shown
     */
    public abstract void printMessage(Object message);

    public abstract void printMessage(String message);

    /**
     * method that deals with printing an error message
     *
     * @param message message of the problem occurred
     */
    public abstract void printFailMessage(String message);

    /**
     * method that updates the objects showed to the player
     *
     * @param event the particular view Event received
     */
    public void updateCachedModel(PrintEvent<?> event) {

        // TODO develop? (if we keep a version of the model in the client)
    }

    /**
     * method that notifies that this is the first player
     */

    public EventBroker getEventBroker() {
        return eventBroker;
    }

    public Map<String, HumanPlayer> getPlayers() {
        return players;
    }

    public DcBoard getDcBoard() {
        return dcBoard;
    }

    public MarketTray getMarketTray() {
        return marketTray;
    }

    public void setDcBoard(DcBoard dcBoard) {
        System.out.println("[UI] DcBoard updated");
        this.dcBoard = dcBoard;
    }

    public void setMarketTray(MarketTray marketTray) {
        System.out.println("[UI] market tray updated");
        this.marketTray = marketTray;
    }
    
    public void setMyNickname(String myNickname) {
        this.myNickname = myNickname;
    }

    public String getMyNickname() {
        return myNickname;
    }

}
