package it.polimi.ingsw.server.model.ActionCards;

import it.polimi.ingsw.server.model.BadFormatException;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionCardTest {

    /**
     * Testing that is allowed a Effect.PLUS_TWO_FAITH with no devCardToDiscard
     */
    @Test
    public void isAllowedPlusTwoFaithNoTypeDevCard() {
        ActionCard actionCard = new ActionCard(Effect.PLUS_TWO_FAITH);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that is allowed a Effect.PLUS_ONE_FAITH_SHUFFLE with no devCardToDiscard
     */
    @Test
    public void isAllowedPlusOneFaithShuffleNoTypeDevCard() {
        ActionCard actionCard = new ActionCard(Effect.PLUS_ONE_FAITH_SHUFFLE);
        assertTrue(actionCard.isAllowed());
    }

    /**
     * Testing that is NOT allowed a Effect.DISCARD_TWO_CARDS with no devCardToDiscard
     */
    @Test(expected = BadFormatException.class)
    public void isAllowedDiscardTwoCardsNoTypeDevCard() {
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
     * Testing that calling getDevCardToDiscard on a Effect.DISCARD_TWO_CARDS with devCardToDiscard
     */
    @Test
    public void getDevCardToDiscardEffectDiscardTwoCardsAndCard() {
        ActionCard actionCard = new ActionCard(Effect.DISCARD_TWO_CARDS, TypeDevCards_Enum.YELLOW);
        assertNotNull(actionCard.getDevCardToDiscard());
    }
}