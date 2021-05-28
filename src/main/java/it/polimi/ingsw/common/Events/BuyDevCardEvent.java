package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.viewEvents.*;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.controller.MakePlayerPay;
import it.polimi.ingsw.server.model.Development.*;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.ResDiscount;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Event to signal that the player wants to buy a development card
 * TODO: Test
 */
public class BuyDevCardEvent extends Event {
    private final Tuple tuple;
    private List<ResDiscount> resDiscounts = List.of();

    /**
     * Constructor with the reference to the development card to buy
     *
     * @param tuple the tuple of the development card to buy from the board
     * @throws IllegalArgumentException if the tuple passed is invalid
     */
    public BuyDevCardEvent(Tuple tuple) throws IllegalArgumentException {
        this.tuple = tuple;
        eventType = Events_Enum.BUY_DEV_CARD;

        // checking if the parameters are legit
        if (tuple == null || tuple.getLevel() < 0 || tuple.getLevel() > 3) {
            throw new IllegalArgumentException();
        }
    }

    // TODO javadoc
    public BuyDevCardEvent(UserInterface userInterface) throws IllegalArgumentException {
        eventType = Events_Enum.BUY_DEV_CARD;
        userInterface.printMessage(userInterface.getPlayers().toString());
        userInterface.printMessage(userInterface.getDcBoard().toString());

        List<Object> types = new ArrayList<>(Arrays.asList(TypeDevCards_Enum.values()));
        types.add("Go back");

        // choosing the type of the card
        int chosenType = userInterface.makePlayerChoose(
                new MakePlayerChoose<>(
                        "Choose the type of the development card you want to buy: ",
                        types
                )
        );
        if (types.get(chosenType).equals("Go back"))
            throw new IllegalArgumentException();

        // choosing the level of the card
        int chosenLevel = userInterface.makePlayerChoose(new MakePlayerChoose<>(
                "Choose the level of the development card you want to buy: ",
                List.of(1, 2, 3, "Go back")
        ));
        
        if (types.get(chosenLevel).equals("Go back"))
            throw new IllegalArgumentException();

        this.tuple = new Tuple(TypeDevCards_Enum.values()[chosenType], List.of(1, 2, 3).get(chosenLevel));
    }

    /**
     * Constructor with the reference to the development card to buy and the Leader cards in order to have a discount
     *
     * @param tuple        the tuple of the development card to buy from the board
     * @param resDiscounts list of the leader cards that offer discounts for the purchase of development cards
     * @throws IllegalArgumentException if the tuple passed is invalid
     */
    public BuyDevCardEvent(Tuple tuple, List<ResDiscount> resDiscounts) throws IllegalArgumentException {
        this(tuple);
        if (resDiscounts != null)
            this.resDiscounts = resDiscounts;
    }

    // TODO add javadoc
    public BuyDevCardEvent(UserInterface userInterface, List<ResDiscount> resDiscounts) throws IllegalArgumentException {
        this(userInterface);
        if (resDiscounts != null)
            this.resDiscounts = resDiscounts;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;

        // returning a fail event if it's not the turn of the player
        if (!player.isPlaying()) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't do this action, it's not your turn!"));
            return;
        }

        if (player.isActionDone()) {
            player.getGameClientHandler().sendEvent(new FailEvent("You already did a main action in this round!"));
            return;
        }

        DcPersonalBoard dcPersonalBoard = player.getDevelopmentBoard();
        DevelopmentCard developmentCard;

        // getting the card that the player wants
        try {
            developmentCard = player.getGame().getDcBoard().getFirstCard(tuple);
        } catch (NoCardsInDeckException e) {
            player.getGameClientHandler().sendEvent(new FailEvent("Couldn't get the desired card!"));
            return;
        }

        // asking if the player wants the discounts
        if (resDiscounts == null || resDiscounts.isEmpty()) {
            for (ResDiscount discount : player.getEnabledLeaderCards(Abil_Enum.DISCOUNT).stream().map(leaderCard -> (ResDiscount) leaderCard.getCardAbility()).collect(Collectors.toList())) {
                if (
                        new MakePlayerChoose<>(
                                "Do you want to get a " + discount.getDiscountValue() + " discount on the " + discount.getResourceType() + "? ",
                                List.of("Yes", "No")
                        ).choose(player).equals("Yes")
                ) {
                    resDiscounts.add(discount);
                }
            }
        }

        // if the card isn't purchasable and placeable returns with a fail event
        if (!(dcPersonalBoard.isPlaceable(developmentCard) && developmentCard.getCardCost().isSatisfiable(player, resDiscounts))) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't buy this card!"));
            return;
        }

        // creating the map of all the resources that has to be payed
        Map<Res_Enum, Integer> resToPay = Res_Enum.getFrequencies(developmentCard.getCardCost().getResourcesReq());

        // if the cost for the resource type of the discount is greater than 0, applies the discount
        for (ResDiscount resDiscount : resDiscounts) {
            if (resToPay.get(resDiscount.getResourceType()) > 0)
                resToPay.merge(resDiscount.getResourceType(), resDiscount.getDiscountValue(), (a, b) -> a - b);
        }

        // Makes the player pay the final amount of resources. if this can't be done, a fail event is returned
        if (!MakePlayerPay.payRequirements(player, new ResRequirements(Res_Enum.getList(resToPay)))) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't pay the cost of the card!"));
            return;
        }

        boolean placed = false;
        do {
            try {
                // make the player choose the slot in which place the card purchased
                player.getDevelopmentBoard().addCard(
                        (new MakePlayerChoose<>("Choose the slot for the development card bought:", List.of(1, 2, 3))).choose(player) - 1,
                        developmentCard
                );
                placed = true;

                // removes the card from the board
                player.getGame().getDcBoard().removeFirstCard(tuple);

                // clearing productions added before this main action
                player.clearProductions();
            } catch (BadCardPositionException | BadSlotNumberException | NoCardsInDeckException e) {
                player.getGameClientHandler().sendEvent(new PrintEvent<>("Insert another slot, card can't be placed here!"));
            }
        } while (!placed);

        developmentCard.getProduction().setAvailable(true);

        // signals that the player has completed an action
        player.setActionDone();

        // updating the view
        player.getGame().getEventBroker().post(new PrintDcBoardEvent(player.getGame()), false);

        player.getGameClientHandler().sendEvent(new PrintPlayerEvent(player));
        player.getGameClientHandler().sendEvent(new PrintWarehouseEvent(player));
        player.getGameClientHandler().sendEvent(new PrintStrongboxEvent(player));
        player.getGameClientHandler().sendEvent(new PrintLeaderCardsEvent(player));
        player.getGameClientHandler().sendEvent(new PrintDevelopmentCardsEvent(player));

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You bought a new development card!"));
    }
}