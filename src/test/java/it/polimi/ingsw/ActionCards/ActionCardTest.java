package it.polimi.ingsw.ActionCards;

import it.polimi.ingsw.BadFormatException;
import it.polimi.ingsw.Development.TypeDevCards_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionCardTest {

    /**
     * Testing that is allowed a Effect.PLUS_TWO_FAITH with no devCardToDiscard
     */
    @Test
    public void isAllowedOnlyEffectPlusTwoFaith() {
        ActionCard actionCard = new ActionCard(Effect.PLUS_TWO_FAITH);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that is allowed a Effect.PLUS_ONE_FAITH_SHUFFLE with no devCardToDiscard
     */
    @Test
    public void isAllowedOnlyEffectPlusOneFaithShuffle() {
        ActionCard actionCard = new ActionCard(Effect.PLUS_ONE_FAITH_SHUFFLE);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that is NOT allowed a Effect.DISCARD_TWO_CARDS with no devCardToDiscard
     */
    @Test(expected = BadFormatException.class)
    public void isAllowedOnlyEffectDiscardTwoCards() {
        ActionCard actionCard = new ActionCard(Effect.DISCARD_TWO_CARDS);
        assert false;
    }

    /**
     * Testing that is allowed a Effect.PLUS_TWO_FAITH with devCardToDiscard
     */
    @Test
    public void isAllowedEffectPlusTwoFaithAndCard() {
        ActionCard actionCard = new ActionCard(Effect.PLUS_TWO_FAITH, TypeDevCards_Enum.BLUE);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that is allowed a Effect.PLUS_ONE_FAITH_SHUFFLE with devCardToDiscard
     */
    @Test
    public void isAllowedEffectPlusOneFaithShuffleAndCard() {
        ActionCard actionCard = new ActionCard(Effect.PLUS_ONE_FAITH_SHUFFLE, TypeDevCards_Enum.GREEN);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that is allowed a Effect.DISCARD_TWO_CARDS with devCardToDiscard
     */
    @Test
    public void isAllowedEffectDiscardTwoCardsAndCard() {
        ActionCard actionCard = new ActionCard(Effect.DISCARD_TWO_CARDS, TypeDevCards_Enum.YELLOW);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that calling getDevCardToDiscard on a PLUS_TWO_FAITH throws an exception
     */
    @Test(expected = NoCardFieldException.class)
    public void getDevCardToDiscardOnlyEffectPlusTwoFaith() throws NoCardFieldException {
        ActionCard actionCard = new ActionCard(Effect.PLUS_TWO_FAITH);
        assertNotNull(actionCard.getDevCardToDiscard());
        assert false;
    }

    /**
     * Testing that calling getDevCardToDiscard on a PLUS_ONE_FAITH_SHUFFLE throws an exception
     */
    @Test(expected = NoCardFieldException.class)
    public void getDevCardToDiscardOnlyEffectPlusOneFaithShuffle() throws NoCardFieldException {
        ActionCard actionCard = new ActionCard(Effect.PLUS_ONE_FAITH_SHUFFLE);
        assertNotNull(actionCard.getDevCardToDiscard());
        assert false;
    }

    /**
     * Testing that calling getDevCardToDiscard on a PLUS_ONE_FAITH_SHUFFLE throws an exception
     */
    @Test(expected = NoCardFieldException.class)
    public void getDevCardToDiscardEffectPlusTwoFaithAndCard() throws NoCardFieldException {
        ActionCard actionCard = new ActionCard(Effect.PLUS_TWO_FAITH, TypeDevCards_Enum.BLUE);
        assertNotNull(actionCard.getDevCardToDiscard());
        assert false;
    }

    /**
     * Testing that calling getDevCardToDiscard on a PLUS_ONE_FAITH_SHUFFLE throws an exception
     */
    @Test(expected = NoCardFieldException.class)
    public void getDevCardToDiscardEffectPlusOneFaithShuffleAndCard() throws NoCardFieldException {
        ActionCard actionCard = new ActionCard(Effect.PLUS_ONE_FAITH_SHUFFLE, TypeDevCards_Enum.GREEN);
        assertNotNull(actionCard.getDevCardToDiscard());
        assert false;
    }

    /**
     * Testing that calling getDevCardToDiscard on a Effect.DISCARD_TWO_CARDS with devCardToDiscard
     */
    @Test
    public void getDevCardToDiscardEffectDiscardTwoCardsAndCard() throws NoCardFieldException {
        ActionCard actionCard = new ActionCard(Effect.DISCARD_TWO_CARDS, TypeDevCards_Enum.YELLOW);
        assertNotNull(actionCard.getDevCardToDiscard());
    }
}