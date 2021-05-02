package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.server.model.Game;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrintMarketTrayEventTest {

    @Test
    public void marketTrayDraw() {
        Game game = new Game(1);
        game.getMarketTray().generateTray();
        PrintMarketTrayEvent evento = new PrintMarketTrayEvent(game);
        evento.handle(this);
    }

}