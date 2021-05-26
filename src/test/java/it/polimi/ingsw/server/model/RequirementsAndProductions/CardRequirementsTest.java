package it.polimi.ingsw.server.model.RequirementsAndProductions;

import it.polimi.ingsw.server.model.Development.*;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.NoCardsInDeckException;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CardRequirementsTest {

    /**
     * checks if a card requirement is satisfiable
     */
    @Test
    public void isSatisfiableOnlyStrongBox() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        CardRequirements requirement = new CardRequirements(List.of(
                new Tuple(TypeDevCards_Enum.BLUE, 2),
                new Tuple(TypeDevCards_Enum.PURPLE, 1)
        ));

        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getDevelopmentBoard().addCard(0, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 1)));
        player.getDevelopmentBoard().addCard(0, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 2)));
        player.getDevelopmentBoard().addCard(1, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.PURPLE, 1)));

        assertTrue(requirement.isSatisfiable(player));
    }

    /**
     * checks if a card requirement is satisfiable
     */
    @Test
    public void isSatisfiableGenericCards() throws NoCardsInDeckException, BadSlotNumberException, BadCardPositionException {
        CardRequirements requirement = new CardRequirements(List.of(
                new Tuple(TypeDevCards_Enum.BLUE, 0),
                new Tuple(TypeDevCards_Enum.BLUE, 0),
                new Tuple(TypeDevCards_Enum.PURPLE, 1)
        ));

        Game game = new Game(2);
        DcBoard dcBoard = game.getDcBoard();

        HumanPlayer player = (HumanPlayer) game.getPlayers().get(0);

        player.getDevelopmentBoard().addCard(0, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 1)));
        player.getDevelopmentBoard().addCard(0, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.BLUE, 2)));
        player.getDevelopmentBoard().addCard(1, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.PURPLE, 1)));
        player.getDevelopmentBoard().addCard(1, dcBoard.getFirstCard(new Tuple(TypeDevCards_Enum.PURPLE, 2)));

        assertTrue(requirement.isSatisfiable(player));
    }

    //TODO test?
}