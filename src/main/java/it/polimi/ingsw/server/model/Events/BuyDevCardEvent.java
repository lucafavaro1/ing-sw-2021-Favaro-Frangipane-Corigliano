package it.polimi.ingsw.server.model.Events;

import it.polimi.ingsw.MakePlayerChoose;
import it.polimi.ingsw.server.model.Development.*;
import it.polimi.ingsw.server.model.Leader.ResDiscount;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: Test BuyDevCardEvent
public class BuyDevCardEvent extends Event {
    private final Tuple tuple;
    private List<ResDiscount> resDiscounts = null;

    public BuyDevCardEvent(Tuple tuple) throws IllegalArgumentException {
        this.tuple = tuple;
        eventType = Events_Enum.BUY_DEV_CARD;

        // checking if the parameters are legit
        if (tuple == null || tuple.getLevel() < 0 || tuple.getLevel() > 3) {
            throw new IllegalArgumentException();
        }
    }

    public BuyDevCardEvent(Tuple tuple, List<ResDiscount> resDiscounts) throws IllegalArgumentException {
        this(tuple);
        this.resDiscounts = resDiscounts;
    }

    @Override
    public void handle(Object playerObj) {
        HumanPlayer player = (HumanPlayer) playerObj;
        DcPersonalBoard dcPersonalBoard = player.getDevelopmentBoard();
        DevelopmentCard developmentCard;

        try {
            developmentCard = player.getGame().getDcBoard().getFirstCard(tuple);
        } catch (NoCardsInDeckException e) {
            System.out.println(e.getMessage());
            // TODO gestire l'eccezione passando il controllo al player?
            return;
        }

        // if the card can be bought and placed, buys and places the card
        // TODO: to be improved to be more robust
        if (dcPersonalBoard.isPlaceable(developmentCard) && developmentCard.getCardCost().isSatisfiable(player, resDiscounts)) {
            try {

                player.getDevelopmentBoard().addCard(
                        (new MakePlayerChoose<>(List.of(1, 2, 3))).choose(player),
                        developmentCard
                );

                // creating the map of all the resources that has to be payed
                Map<Res_Enum, Integer> resToPay = new HashMap<>(
                        Res_Enum.getFrequencies(developmentCard.getCardCost().getResourcesReq())
                );

                // if the cost for the resource type of the discount is greter than 0, applies the discount
                for (ResDiscount resDiscount : resDiscounts) {
                    if (resToPay.get(resDiscount.getResourceType()) > 0)
                        resToPay.merge(resDiscount.getResourceType(), -resDiscount.getDiscountValue(), Integer::sum);
                }

                // make the player pay the resources
                player.pay(resToPay);
            } catch (BadCardPositionException | BadSlotNumberException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
