package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.viewEvents.PrintDcBoardEvent;
import it.polimi.ingsw.common.viewEvents.PrintEvent;
import it.polimi.ingsw.common.viewEvents.PrintPlayerEvent;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
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
    private final List<ResDiscount> resDiscounts = new ArrayList<>();

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

    /**
     * Constructor with the reference to the interface
     *
     * @param userInterface is a reference to the interface in used to print messages and make the player choose what to do
     * @throws IllegalArgumentException is thrown when player changes idea and wants to go back
     */
    public BuyDevCardEvent(UserInterface userInterface) throws IllegalArgumentException {
        eventType = Events_Enum.BUY_DEV_CARD;
        // printing the view to the player
        userInterface.printMessage("Common development card board: ");
        userInterface.printMessage(userInterface.getDcBoard().toString() + "\n\n");

        userInterface.printMessage("Personal development card board: ");
        userInterface.printMessage(userInterface.getMyPlayer().getDevelopmentBoard().toString() + "\n\n");

        userInterface.printMessage("Leader cards: ");
        userInterface.getMyPlayer().getLeaderCards().forEach(leaderCard -> userInterface.printMessage(leaderCard.toString() + "\n\n"));

        userInterface.printMessage("Total resources: ");
        userInterface.printMessage(userInterface.getMyPlayer().getTotalResources().remove(Res_Enum.QUESTION).toString() + "\n\n");

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
        if (resDiscounts.isEmpty()) {
            // taking the list of resource discounts with the discountType resource present in the list of resources required to buy the development card
            List<ResDiscount> discounts = player.getEnabledLeaderCards(Abil_Enum.DISCOUNT)
                    .stream()
                    .map(leaderCard -> (ResDiscount) leaderCard.getCardAbility())
                    .filter(resDiscount -> developmentCard.getCardCost().getResourcesReq().contains(resDiscount.getResourceType()))
                    .collect(Collectors.toList());

            for (ResDiscount discount : discounts) {
                // if the player wants to use a leader discount, adds it to the list of discount to use later in the purchase
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

        // if the card isn't purchasable or placeable returns with a fail event
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
        if (!MakePlayerChoose.payRequirements(player, new ResRequirements(Res_Enum.getList(resToPay)))) {
            player.getGameClientHandler().sendEvent(new FailEvent("Can't pay the cost of the card!"));
            return;
        }

        // placing the purchased development card
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

        // activating the production of the development card purchased
        developmentCard.getProduction().setAvailable(true);

        player.clearProductions();

        // signals that the player has completed an action
        player.setActionDone();

        // updating the view
        player.getGame().getEventBroker().post(new PrintDcBoardEvent(player.getGame()), false);
        player.getGame().getEventBroker().post(new PrintPlayerEvent(player), false);

        player.getGameClientHandler().sendEvent(new ActionDoneEvent("You bought a new development card!"));
    }
}