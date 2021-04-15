package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.IncorrectResourceException;
import it.polimi.ingsw.server.model.Leader.PlusSlot;
import it.polimi.ingsw.server.model.Leader.SlotIsFullException;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.WarehouseDepots;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Event used to signal the action of taking resources from the market
 * TODO: tests
 */
public class GetMarketResEvent extends Event {
    private final boolean horizontal;
    private final int toGet;

    /**
     * constructor of the event that identifies the row or column that the player wants to get from the market
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

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;
        MarketTray marketTray = player.getGame().getMarketTray();
        WarehouseDepots warehouseDepots = player.getWarehouseDepots();

        if (player.isActionDone())
            return;

        // takes the resources from the market tray
        List<Res_Enum> resources = marketTray.marketAction(player, horizontal, toGet);

        // add resources in the leader cards
        List<PlusSlot> plusSlotCards = player.getLeaderCards().stream().filter(leaderCard ->
                leaderCard.isEnabled() && leaderCard.getCardAbility().getAbilityType().equals(Abil_Enum.SLOT)
        ).map(leaderCard -> (PlusSlot) leaderCard.getCardAbility()).collect(Collectors.toList());

        // for each plus slot leader card activated adds the resources that fit
        plusSlotCards.stream().filter(plusSlot -> Collections.frequency(resources, plusSlot.getResType()) > 0)
                .forEach(plusSlot -> {
                    for (int i = 0; i < 2 && Collections.frequency(resources, plusSlot.getResType()) > 0; i++) {
                        try {
                            plusSlot.putRes(plusSlot.getResType());
                            resources.remove(plusSlot.getResType());
                        } catch (SlotIsFullException | IncorrectResourceException ignored) {
                        }
                    }
                });

        // for each resource left, we ask the player if wants to discard or try adding the resource to the depot
        for (Res_Enum res_enum : resources) {
            boolean added = false;
            if ((new MakePlayerChoose<>(List.of("discard", "add"))).choose(player).equals("add")) {
                added = warehouseDepots.tryAdding(res_enum);
            }

            if (!added) {
                // to be discarded, adds to all the other players to add one faith
                player.getGame().getEventBroker().postAllButMe(
                        player.getFaithTrack(),
                        new AddFaithEvent(1),
                        false
                );
            }
        }

        // notifying that an action is done
        player.setActionDone();
    }
}
