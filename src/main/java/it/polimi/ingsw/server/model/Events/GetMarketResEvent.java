package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.MakePlayerChoose;
import it.polimi.ingsw.server.model.Leader.*;
import it.polimi.ingsw.server.model.Market.Marble_Enum;
import it.polimi.ingsw.server.model.Market.MarketMarble;
import it.polimi.ingsw.server.model.Market.MarketTray;
import it.polimi.ingsw.server.model.Player.*;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.*;
import java.util.stream.Collectors;

// TODO: javadoc, tests
public class GetMarketResEvent extends Event {
    private final boolean horizontal;
    private final int toGet;

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
        List<MarketMarble> marblesTaken;
        List<Res_Enum> resources = new ArrayList<>();

        // takes the marbles chosen
        if (horizontal)
            marblesTaken = marketTray.getRow(toGet);
        else
            marblesTaken = marketTray.getColumn(toGet);

        // takes the list of cards with the White Marble Ability
        List<WhiteMarble> whiteMarbleCards = player.getLeaderCards().stream().filter(
                leaderCard -> leaderCard.isEnabled() && leaderCard.getCardAbility().getAbilityType().equals(Abil_Enum.WHITE_MARBLE)
        ).map(leaderCard -> (WhiteMarble) leaderCard.getCardAbility()).collect(Collectors.toList());

        // for each market marble taken we convert the resource to the relative ResEnum
        for (MarketMarble marble : marblesTaken) {
            if (marble.getMarbleColor().equals(Marble_Enum.WHITE) && !whiteMarbleCards.isEmpty()) {
                // if the color of the marble is white and the player has some Leader Card for the market
                if (whiteMarbleCards.size() == 1) {
                    // takes automatically the resource
                    resources.add(whiteMarbleCards.get(0).getResourceType());
                } else {
                    // makes the player choose the resource leader card to use
                    resources.add(
                            (new MakePlayerChoose<>(whiteMarbleCards)).choose(player).getResourceType()
                    );
                }
            } else {
                Optional.ofNullable(marble.convertRes(player))
                        .ifPresent(resources::add);
            }
        }

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

        // TODO: portarlo in warehouseDepot? test
        for (Res_Enum res_enum : resources) {
            boolean added = false;
            if ((new MakePlayerChoose<>(List.of("discard", "add"))).choose(player).equals("add")) {
                added = warehouseDepots.tryAdding(res_enum);
            }

            if(!added){
                // to be discarded
                player.getGame().getEventBroker().postAllButMe(
                        player.getFaithTrack(),
                        new AddFaithEvent(1),
                        false
                );
            }
        }
        player.setActionDone();
    }
}
