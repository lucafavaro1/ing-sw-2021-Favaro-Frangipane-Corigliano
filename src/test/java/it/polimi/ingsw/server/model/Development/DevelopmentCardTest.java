package it.polimi.ingsw.server.model.Development;

import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.ResRequirements;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DevelopmentCardTest {
    /**
     * testing if a well formed card is accepted
     */
    @Test
    public void isAllowedRightCard0() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 3);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(Res_Enum.SHIELD), 1);
        ResRequirements requirements = new ResRequirements(List.of(Res_Enum.STONE));

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 5);
        assertTrue(developmentCard.isAllowed());
    }

    /**
     * testing if a well formed card is accepted (null production resources)
     */
    @Test
    public void isAllowedRightCard1() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 3);
        Production production = new Production(List.of(Res_Enum.COIN), null, 1);
        ResRequirements requirements = new ResRequirements(List.of(Res_Enum.STONE));

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 5);
        assertTrue(developmentCard.isAllowed());
    }

    /**
     * testing if a well formed card is accepted (0 faith)
     */
    @Test
    public void isAllowedRightCard2() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 3);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(Res_Enum.STONE), 0);
        ResRequirements requirements = new ResRequirements(List.of(Res_Enum.STONE));

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 5);
        assertTrue(developmentCard.isAllowed());
    }

    /**
     * testing if a well formed card is accepted (empty cardCost resRequirements)
     */
    @Test
    public void isAllowedRightCard3() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(Res_Enum.STONE), 1);
        ResRequirements requirements = new ResRequirements(List.of());

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 0);
        assertTrue(developmentCard.isAllowed());
    }

    /**
     * testing if a well formed card is accepted (empty production resRequirements)
     */
    @Test
    public void isAllowedRightCard4() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Production production = new Production(List.of(), List.of(Res_Enum.STONE), 1);
        ResRequirements requirements = new ResRequirements(List.of(Res_Enum.STONE));

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 0);
        assertTrue(developmentCard.isAllowed());
    }

    /**
     * testing if is not allowed a bad tuple
     */
    @Test
    public void isAllowedBadCardTuple() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 4);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(Res_Enum.STONE), 1);
        ResRequirements requirements = new ResRequirements(List.of());

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 0);
        assertFalse(developmentCard.isAllowed());
    }

    /**
     * testing if is not allowed a bad production (empty list of resProduction and 0 faith)
     */
    @Test
    public void isAllowedBadCardProduction1() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(), 0);
        ResRequirements requirements = new ResRequirements(List.of());

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 0);
        assertFalse(developmentCard.isAllowed());
    }

    /**
     * testing if is not allowed a bad production (null resProduction and 0 faith)
     */
    @Test
    public void isAllowedBadCardProduction2() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Production production = new Production(List.of(Res_Enum.COIN), null, 0);
        ResRequirements requirements = new ResRequirements(List.of());

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 0);
        assertFalse(developmentCard.isAllowed());
    }

    /**
     * testing if is not allowed a bad production (null cardCost requirements)
     */
    @Test
    public void isAllowedBadCardProduction3() {
        Tuple tuple = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(Res_Enum.STONE), 1);
        ResRequirements requirements = null;

        // asserting that a normal card is allowed
        DevelopmentCard developmentCard = new DevelopmentCard(tuple, production, requirements, 0);
        assertFalse(developmentCard.isAllowed());
    }

    /**
     * testing that cards' successors are well detected
     */
    @Test
    public void isSuccessorOf() {
        Tuple tuple1 = new Tuple(TypeDevCards_Enum.BLUE, 1);
        Tuple tuple2 = new Tuple(TypeDevCards_Enum.GREEN, 2);
        Tuple tuple3 = new Tuple(TypeDevCards_Enum.PURPLE, 3);
        Production production = new Production(List.of(Res_Enum.COIN), List.of(Res_Enum.SHIELD), 1);
        ResRequirements requirements = new ResRequirements(List.of(Res_Enum.STONE));

        // creating three cards, each of a different level
        DevelopmentCard developmentCard1 = new DevelopmentCard(tuple1, production, requirements, 5);
        DevelopmentCard developmentCard2 = new DevelopmentCard(tuple2, production, requirements, 5);
        DevelopmentCard developmentCard3 = new DevelopmentCard(tuple3, production, requirements, 5);

        // asserting the right successor hierarchy
        assertTrue(developmentCard1.isSuccessorOf((DevelopmentCard) null));
        assertTrue(developmentCard2.isSuccessorOf(developmentCard1));
        assertTrue(developmentCard3.isSuccessorOf(developmentCard2));

        // cards can't be successor of themselves
        assertFalse(developmentCard1.isSuccessorOf(developmentCard1));

        // asserting that two cards with non consecutive levels aren't successors
        assertFalse(developmentCard3.isSuccessorOf(developmentCard1));

        // asserting that a card of level 1 is not a successor of level 2
        assertFalse(developmentCard1.isSuccessorOf(developmentCard2));

        // asserting that a card of level 2 is not a successor of a null card
        assertFalse(developmentCard2.isSuccessorOf((DevelopmentCard) null));
    }
}