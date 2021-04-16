package it.polimi.ingsw.server.model.Player;

import it.polimi.ingsw.server.model.Development.*;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.*;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class HumanPlayerTest {
    /**
     * counting points resources, faithtrack
     */
    @Test
    public void testCountPoints1() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.COIN, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SERVANT, 2, 2);

        player.getFaithTrack().increasePos(6);
        player.getFaithTrack().vaticanReport(1);
        assertEquals(4, player.countPoints());
    }

    /**
     * counting points resources, faithtrack
     */
    @Test
    public void testCountPoints2() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.COIN, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SERVANT, 2, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SHIELD, 2, 3);
        player.getFaithTrack().increasePos(6);
        player.getFaithTrack().vaticanReport(1);
        assertEquals(5, player.countPoints());
    }

    /**
     * counting points disabled leadercards
     */
    @Test
    public void testCountPoints1LeaderDisabled() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 1);

        LeaderCard leaderCard = new LeaderCard(
                new ResDiscount(Res_Enum.COIN, 1),
                null,
                new ResRequirements(List.of(Res_Enum.STONE)),
                5
        );

        player.addLeaderCard(leaderCard);

        assertEquals(0, player.countPoints());
    }

    /**
     * counting points leaderCards enabled
     */
    @Test
    public void testCountPoints1LeaderEnabled() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 1);

        LeaderCard leaderCard = new LeaderCard(
                new ResDiscount(Res_Enum.COIN, 1),
                null,
                new ResRequirements(List.of(Res_Enum.STONE)),
                5
        );

        player.addLeaderCard(leaderCard);
        player.getLeaderCards().get(0).enable(player);

        assertEquals(5, player.countPoints());
    }

    /**
     * counting points one development card
     */
    @Test
    public void testCountPointsDevelopment() throws BadSlotNumberException, BadCardPositionException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);

        player.getDevelopmentBoard().addCard(
                0,
                new DevelopmentCard(
                        tuple,
                        null,
                        null,
                        5
                )
        );

        assertEquals(5, player.countPoints());
    }

    /**
     * counting points one development card
     */
    @Test
    public void testCountPointsMoreDevelopment() throws BadSlotNumberException, BadCardPositionException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        Tuple tuple1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Tuple tuple2 = new Tuple(TypeDevCards_Enum.PURPLE, 2);

        player.getDevelopmentBoard().addCard(
                0,
                new DevelopmentCard(
                        tuple1,
                        null,
                        null,
                        5
                )
        );

        player.getDevelopmentBoard().addCard(
                0,
                new DevelopmentCard(
                        tuple2,
                        null,
                        null,
                        50
                )
        );

        assertEquals(55, player.countPoints());
    }

    /**
     * counting points only increasing the position
     */
    @Test
    public void testCountPointsFaithPosition() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        // loosing the first vatican report
        player.getFaithTrack().vaticanReport(1);

        // going to position 14
        player.getFaithTrack().increasePos(14);

        // pos: 14 -> 6 victory points
        assertEquals(6, player.countPoints());
    }

    /**
     * counting points increasing position and doing vatican reports
     */
    @Test
    public void testCountPointsVaticanReport() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getFaithTrack().increasePos(7);
        player.getFaithTrack().vaticanReport(1);

        player.getFaithTrack().increasePos(7);
        player.getFaithTrack().vaticanReport(2);

        // pos: 14 -> 6 victory points
        // vatican report: 1+2 -> 2 + 3 = 5 victory points
        assertEquals(11, player.countPoints());
    }

    /**
     * counting points increasing position and doing vatican reports
     */
    @Test
    public void testCountPointsAll() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException, IncorrectResourceException, SlotIsFullException, BadSlotNumberException, BadCardPositionException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        /* faithtrack */
        // vatican report 1: 2 VP
        // position 14: 6 VP
        player.getFaithTrack().increasePos(14);

        assertEquals(8, player.countPoints());

        /* leader cards */
        player.addLeaderCard(new LeaderCard(
                new PlusSlot(Res_Enum.STONE),
                null,
                null,
                5
        ));
        player.getLeaderCards().get(0).enable(player);

        assertEquals(13, player.countPoints());

        /* resources */
        // 10 resources: 2 VP
        // strongbox
        player.getStrongBox().putRes(Res_Enum.STONE, 2);
        player.getStrongBox().putRes(Res_Enum.COIN, 2);

        // warehouse
        player.getWarehouseDepots().add_dp(Res_Enum.STONE, 2, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.SHIELD, 2, 3);

        // plus slot
        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);
        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);

        assertEquals(15, player.countPoints());

        /* development card */
        Tuple tuple1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Tuple tuple2 = new Tuple(TypeDevCards_Enum.PURPLE, 2);

        player.getDevelopmentBoard().addCard(
                0,
                new DevelopmentCard(
                        tuple1,
                        null,
                        null,
                        5
                )
        );

        player.getDevelopmentBoard().addCard(
                0,
                new DevelopmentCard(
                        tuple2,
                        null,
                        null,
                        5
                )
        );
        assertEquals(25, player.countPoints());
    }

    /**
     * testing if the getTotalResources works well with no resources in the player
     */
    @Test
    public void getTotalResourcesEmpty() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        assertNotNull(player.getTotalResources());
    }

    /**
     * testing if the getTotalResources works well with resources in the warehouse
     */
    @Test
    public void getTotalResourcesWarehouse() throws MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getWarehouseDepots().add_dp(Res_Enum.STONE, 2, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.COIN, 1, 3);

        assertEquals(2, player.getTotalResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getTotalResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing if the getTotalResources works well with resources in the strongbox
     */
    @Test
    public void getTotalResourcesStrongbox() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 2);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        assertEquals(2, player.getTotalResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getTotalResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing if the getTotalResources works well with resources in a Plus Slot leader card
     */
    @Test
    public void getTotalResources1PlusSlot() throws IncorrectResourceException, SlotIsFullException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.addLeaderCard(new LeaderCard(
                new PlusSlot(Res_Enum.STONE),
                null,
                null,
                0
        ));

        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);
        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);

        assertEquals(2, player.getTotalResources().get(Res_Enum.STONE).intValue());
    }

    /**
     * testing if the getTotalResources works well with resources in a Plus Slot leader card
     */
    @Test
    public void getTotalResources2PlusSlot() throws IncorrectResourceException, SlotIsFullException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.addLeaderCard(new LeaderCard(
                new PlusSlot(Res_Enum.STONE),
                null,
                null,
                0
        ));

        player.addLeaderCard(new LeaderCard(
                new PlusSlot(Res_Enum.COIN),
                null,
                null,
                0
        ));

        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);
        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);

        ((PlusSlot) player.getLeaderCards().get(1).getCardAbility()).putRes(Res_Enum.COIN);

        assertEquals(2, player.getTotalResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getTotalResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing if the getTotalResources works well with resources in a Plus Slot leader card
     */
    @Test
    public void getTotalResourcesFromEverywhere() throws IncorrectResourceException, SlotIsFullException, MixedResourcesException, SameResInTwoShelvesException, NotEnoughSpaceException {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        // strongbox
        player.getStrongBox().putRes(Res_Enum.STONE, 2);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        // warehouse
        player.getWarehouseDepots().add_dp(Res_Enum.STONE, 2, 2);
        player.getWarehouseDepots().add_dp(Res_Enum.COIN, 1, 3);

        // plus slot
        player.addLeaderCard(new LeaderCard(
                new PlusSlot(Res_Enum.STONE),
                null,
                null,
                0
        ));

        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);
        ((PlusSlot) player.getLeaderCards().get(0).getCardAbility()).putRes(Res_Enum.STONE);

        assertEquals(6, player.getTotalResources().get(Res_Enum.STONE).intValue());
        assertEquals(2, player.getTotalResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing getAvailableResources if the player has no resources
     */
    @Test
    public void getAvailableResourcesEmpty() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        assertNotNull(player.getAvailableResources());
    }

    /**
     * testing getAvailableResources if the player has some resources
     */
    @Test
    public void getAvailableResourcesWithResources() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 3);

        assertEquals(3, player.getAvailableResources().get(Res_Enum.STONE).intValue());
    }

    /**
     * testing getAvailableResources if the player has already added a production
     */
    @Test
    public void getAvailableResources1Production() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 3);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        assertEquals(3, player.getAvailableResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getAvailableResources().get(Res_Enum.COIN).intValue());

        Production production = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE),
                List.of(),
                1
        );

        assertTrue(player.addProduction(production));

        assertEquals(1, player.getAvailableResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getAvailableResources().get(Res_Enum.COIN).intValue());

        // asserting that the actual resources hasn't been removed
        assertEquals(3, player.getTotalResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getTotalResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing getAvailableResources if the player has added two productions
     */
    @Test
    public void getAvailableResources1Production1Rejected() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 3);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        assertEquals(3, player.getAvailableResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getAvailableResources().get(Res_Enum.COIN).intValue());

        Production production1 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE),
                List.of(),
                1
        );

        Production production2 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE),
                List.of(),
                1
        );

        assertTrue(player.addProduction(production1));
        assertFalse(player.addProduction(production2));

        assertEquals(1, player.getAvailableResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getAvailableResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing getAvailableResources if the player has added two productions
     */
    @Test
    public void getAvailableResources2ProductionsOk() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 3);
        player.getStrongBox().putRes(Res_Enum.COIN, 1);

        assertEquals(3, player.getAvailableResources().get(Res_Enum.STONE).intValue());
        assertEquals(1, player.getAvailableResources().get(Res_Enum.COIN).intValue());

        Production production1 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.STONE),
                List.of(),
                1
        );

        Production production2 = new Production(
                List.of(Res_Enum.STONE, Res_Enum.COIN),
                List.of(),
                3
        );

        assertTrue(player.addProduction(production1));
        assertTrue(player.addProduction(production2));

        assertEquals(0, player.getAvailableResources().get(Res_Enum.STONE).intValue());
        assertEquals(0, player.getAvailableResources().get(Res_Enum.COIN).intValue());
    }

    /**
     * testing addProduction if the player has already added the production
     */
    @Test
    public void addProductionSameProduction() {
        Game game = new Game(2);
        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getStrongBox().putRes(Res_Enum.STONE, 3);

        assertEquals(3, player.getAvailableResources().get(Res_Enum.STONE).intValue());

        Production production1 = new Production(
                List.of(Res_Enum.STONE),
                List.of(),
                1
        );

        assertTrue(player.addProduction(production1));
        assertFalse(player.addProduction(production1));

        assertEquals(2, player.getAvailableResources().get(Res_Enum.STONE).intValue());
    }
}