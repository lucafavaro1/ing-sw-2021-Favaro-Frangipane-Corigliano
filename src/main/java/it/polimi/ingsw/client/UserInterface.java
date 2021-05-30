package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLIUserInterface;
import it.polimi.ingsw.client.gui.GUIUserInterface;
import it.polimi.ingsw.common.Events.EventBroker;
import it.polimi.ingsw.common.Events.EventHandler;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.ActionCards.ActionCard;
import it.polimi.ingsw.server.model.Development.DcBoard;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract singleton class that represents the interface to the user interface, weather it is the CLI or the GUI
 */
public abstract class UserInterface implements EventHandler {
    private static UserInterface instance;
    private final EventBroker eventBroker;

    // Cached Model instances
    /**
     * Map of all the players of the game, with as key the nickname of the players
     */
    protected final Map<String, Player> players = new HashMap<>();
    protected ActionCard lastActionCard;
    protected DcBoard dcBoard;
    protected MarketTray marketTray;
    protected String myNickname;

    /**
     * Method to create a new instance of the UserInterface
     * @param cli 1 if you choose cli, 0 otherwise
     * @param eventBroker the eventbroker of the client
     */
    public static void newInstance(boolean cli, EventBroker eventBroker) {
        if (instance == null)
            instance = (cli ? new CLIUserInterface(eventBroker) : new GUIUserInterface(eventBroker));
    }

    public static UserInterface getInstance() {
        return instance;
    }

    /**
     * Basic constructor of the userinterface
     * @param eventBroker
     */
    protected UserInterface(EventBroker eventBroker) {
        this.eventBroker = eventBroker;
        eventBroker.subscribe(
                this,
                EnumSet.of(Events_Enum.PRINT_MESSAGE, Events_Enum.FAIL, Events_Enum.FIRST_PLAYER, Events_Enum.RANKING));
    }

    /**
     * Method that deals with showing to the user the different options the player could choose
     *
     * @param makePlayerChoose the makePlayerChoose list received
     * @return the option chosen by the user (corresponding index from the list passed)
     */
    public abstract int makePlayerChoose(MakePlayerChoose<?> makePlayerChoose);

    public synchronized void choose(int chosen) {
    }

    /**
     * method that shows a message on the screen
     *
     * @param message message to be shown
     */
    public abstract void printMessage(Object message);

    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * method that deals with printing an error message
     *
     * @param message message of the problem occurred
     */
    public abstract void printFailMessage(String message);

    /**
     * method that notifies that this is the first player
     */

    public EventBroker getEventBroker() {
        return eventBroker;
    }

    public Map<String, Player> getPlayers() {
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

    public ActionCard getLastActionCard() {
        return lastActionCard;
    }

    public void setLastActionCard(ActionCard lastActionCard) {
        // printing the action card taken by the CPU
        printMessage(lastActionCard.toString());
        this.lastActionCard = lastActionCard;
    }

    public HumanPlayer getMyPlayer() {
        System.out.println("[UI] myPlayer " + myNickname + "is null?" + (players.get(myNickname) == null));
        return (HumanPlayer) players.get(myNickname);
    }
}
