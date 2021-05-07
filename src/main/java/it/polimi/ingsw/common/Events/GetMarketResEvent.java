package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Deposit;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.Leader.WhiteMarble;
import it.polimi.ingsw.server.model.Market.Marble_Enum;
import it.polimi.ingsw.server.model.Market.MarketMarble;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Event used to signal the action of taking resources from the market
 */
public class GetMarketResEvent extends Event {
    private final boolean horizontal;
    private final int toGet;

    /**
     * Constructor of the event that identifies the row or column that the player wants to get from the market
     *
     * @param horizontal if the line to get is horizontal or vertical (aka row or column)
     * @param toGet      the number of the row or column to get
     * @throws IllegalArgumentException if the line selected doesn't exist
     */
    public GetMarketResEvent(boolean horizontal, int toGet) throws IllegalArgumentException {
        eventType = Events_Enum.GET_MARKET_RES;

        // checking if the parameters are legit
        if (toGet < 0 || (horizontal && toGet > 2) || (!horizontal && toGet > 3)) {
            throw new IllegalArgumentException();
        }

        this.horizontal = horizontal;
        this.toGet = toGet;
    }

    // TODO add javadoc
    public GetMarketResEvent(UserInterface userInterface) throws IllegalArgumentException {
        eventType = Events_Enum.GET_MARKET_RES;

        horizontal = (userInterface.makePlayerChoose(
                new MakePlayerChoose<>(
                        "Choose if you want to take a row or a column from the market tray:",
                        List.of("Row", "Column")
                )
        ) == 1);

        toGet = (horizontal ?
                userInterface.makePlayerChoose(
                        new MakePlayerChoose<>(
                                "Choose what row you want to take: ",
                                List.of(1, 2, 3)
                        )
                ) :
                userInterface.makePlayerChoose(
                        new MakePlayerChoose<>(
                                "Choose what column you want to take: ",
                                List.of(1, 2, 3, 4)
                        )
                ));

        eventType = Events_Enum.GET_MARKET_RES;

        // checking if the parameters are legit
        if (toGet < 0 || (horizontal && toGet > 2) || (!horizontal && toGet > 3)) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Method that converts the marbles taken from the market to the relative resources (asks the player if he has more WhiteMarble leader cards)
     *
     * @param player       the player that is converting the marbles
     * @param marblesTaken the list of marbles to convert
     * @return a list of resources
     */
    protected List<Res_Enum> convertMarbles(HumanPlayer player, List<MarketMarble> marblesTaken) {
        List<Res_Enum> resources = new ArrayList<>();

        // takes the list of cards with the White Marble Ability
        List<WhiteMarble> whiteMarbleCards = player.getEnabledLeaderCards(Abil_Enum.WHITE_MARBLE).stream()
                .map(leaderCard -> (WhiteMarble) leaderCard.getCardAbility())
                .collect(Collectors.toList());

        // for each market marble taken we convert the resource to the relative ResEnum
        for (MarketMarble marble : marblesTaken) {
            if (marble.getMarbleColor().equals(Marble_Enum.WHITE) && !whiteMarbleCards.isEmpty()) {
                // if the color of the marble is white and the player has some WhiteMarble leader card
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
                // converts the resources and if there is a resource returned adds it to the resources list
                Optional.ofNullable(marble.convertRes(player))
                        .ifPresent(resources::add);
            }
        }

        return resources;
    }

    /**
     * Method that makes the player choose whether to discard the resource or to put in an available deposit
     *
     * @param player   the player that is sorting the resources
     * @param res_enum the resource that the player has to sort in his deposits
     */
    protected void processResources(HumanPlayer player, Res_Enum res_enum) {
        Discard discard = new Discard(player);

        List<PlusSlot> plusSlots = player.getEnabledLeaderCards(Abil_Enum.SLOT).stream()
                .map(leaderCard -> (PlusSlot) leaderCard.getCardAbility())
                .filter(plusSlot -> plusSlot.getResType() == res_enum && plusSlot.getResource().size() < 2)
                .collect(Collectors.toList());

        List<Deposit> deposits = new ArrayList<>();
        deposits.add(discard);
        deposits.add(player.getWarehouseDepots());
        deposits.addAll(plusSlots);

        // makes the player choose in which deposit add the resources obtained or if discard them
        Deposit chosen;
        do {
            chosen = (new MakePlayerChoose<>("where to put " + res_enum + "? \n", deposits)).choose(player);
            deposits.remove(chosen);
        } while (!chosen.tryAdding(res_enum));
    }

    // TODO test
    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;

        // clearing productions added before this main action
        player.clearProductions();

        // if the player can't do another action, we return without doing anything
        if (player.isActionDone())
            return;

        MarketTray marketTray = player.getGame().getMarketTray();
        WarehouseDepots warehouseDepots = player.getWarehouseDepots();
        List<Res_Enum> resources;

        // takes and converts the resources taken from the market
        if (horizontal) {
            resources = convertMarbles(player, marketTray.getRow(toGet));
            marketTray.shiftRowLeft(toGet);
        } else {
            resources = convertMarbles(player, marketTray.getColumn(toGet));
            marketTray.shiftColUp(toGet);
        }

        // for each resource taken from the market the player chooses where to put it or if he wants to discard it
        for (Res_Enum res_enum : resources) {
            processResources(player, res_enum);
        }
        // notifying that an action is done
        player.setActionDone();
    }

    @Override
    public String toString() {
        return "Get resources from the market";
    }
}

/**
 * Inner class that models the discard action as if it is a deposit as any other
 */
class Discard implements Deposit {
    private final HumanPlayer player;

    public Discard(HumanPlayer player) {
        this.player = player;
    }

    @Override
    public int useRes(Res_Enum res, int quantity) {
        return 0;
    }

    @Override
    public boolean tryAdding(Res_Enum res) {
        player.getGame().getEventBroker().postAllButMe(
                player.getFaithTrack(),
                new AddFaithEvent(1),
                false
        );
        return true;
    }

    @Override
    public String toString() {
        return "Discard";
    }
}