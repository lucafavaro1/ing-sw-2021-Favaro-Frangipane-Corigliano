package it.polimi.ingsw.common.Events;

import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.controller.MakePlayerPay;
import it.polimi.ingsw.server.model.Development.*;
import it.polimi.ingsw.server.model.Leader.ResDiscount;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.List;
import java.util.Map;

/**
 * Event to signal that the player wants to buy a development card
 * TODO: Test
 */
public class BuyDevCardEvent extends Event {
    private final Tuple tuple;
    private List<ResDiscount> resDiscounts = null;

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
     * Constructor with the reference to the development card to buy and the Leader cards in order to have a discount
     *
     * @param tuple        the tuple of the development card to buy from the board
     * @param resDiscounts list of the leader cards that offer discounts for the purchase of development cards
     * @throws IllegalArgumentException if the tuple passed is invalid
     */
    public BuyDevCardEvent(Tuple tuple, List<ResDiscount> resDiscounts) throws IllegalArgumentException {
        this(tuple);
        this.resDiscounts = resDiscounts;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;
        DcPersonalBoard dcPersonalBoard = player.getDevelopmentBoard();
        DevelopmentCard developmentCard;

        // getting the card that the player wants
        try {
            developmentCard = player.getGame().getDcBoard().getFirstCard(tuple);
        } catch (NoCardsInDeckException e) {
            System.out.println(e.getMessage());
            return;
        }

        // if the card is purchasable and placeable buys and places the card
        if (dcPersonalBoard.isPlaceable(developmentCard) && developmentCard.getCardCost().isSatisfiable(player, resDiscounts)) {
            // creating the map of all the resources that has to be payed
            Map<Res_Enum, Integer> resToPay = Res_Enum.getFrequencies(developmentCard.getCardCost().getResourcesReq());

            // if the cost for the resource type of the discount is greater than 0, applies the discount
            for (ResDiscount resDiscount : resDiscounts) {
                if (resToPay.get(resDiscount.getResourceType()) > 0)
                    resToPay.merge(resDiscount.getResourceType(), resDiscount.getDiscountValue(), (a, b) -> a - b);
            }

            try {
                // make the player choose the slot in which place the card purchased
                player.getDevelopmentBoard().addCard(
                        (new MakePlayerChoose<>(List.of(1, 2, 3))).choose(player),
                        developmentCard
                );

                // removes the card from the board
                player.getGame().getDcBoard().removeFirstCard(tuple);

            } catch (BadCardPositionException | BadSlotNumberException | NoCardsInDeckException e) {
                e.printStackTrace();
            }

            // for every resource required makes the player choose from which deposit take the resource
            MakePlayerPay.payRequirements(player, developmentCard.getCardCost());

            // signals that the player has completed an action
            player.setActionDone();
        }
    }
}
