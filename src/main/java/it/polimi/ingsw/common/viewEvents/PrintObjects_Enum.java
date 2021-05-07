package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.PlayerRequest;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

// TODO add javadoc
public enum PrintObjects_Enum implements PlayerRequest {
    MARKET_TRAY("view market tray") {
        @Override
        public PrintEvent getRelativePrintEvent(HumanPlayer player) {
            return new PrintMarketTrayEvent(player.getGame());
        }
    },
    DC_BOARD("view development board") {
        @Override
        public PrintEvent getRelativePrintEvent(HumanPlayer player) {
            return new PrintDcBoardEvent(player.getGame());
        }
    },
    PERSONAL_DC_BOARD("view personal development board") {
        @Override
        public PrintEvent getRelativePrintEvent(HumanPlayer player) {
            return new PrintDevelopmentCardsEvent(player);
        }
    },
    FAITH_TRACK("view faith track") {
        @Override
        public PrintEvent getRelativePrintEvent(HumanPlayer player) {
            return new PrintFaithtrackEvent(player);
        }
    },
    LEADER_CARDS("view leader cards") {
        @Override
        public PrintEvent getRelativePrintEvent(HumanPlayer player) {
            return new PrintLeaderCardsEvent(player);
        }
    },
    DEPOSITS("view all the resources") {
        @Override
        public PrintEvent getRelativePrintEvent(HumanPlayer player) {
            return new PrintResourcesEvent(player);
        }
    };

    private final String text;

    PrintObjects_Enum(String text) {
        this.text = text;
    }

    public abstract PrintEvent getRelativePrintEvent(HumanPlayer player);

    @Override
    public Event getRelativeEvent(UserInterface userInterface) {
        return new GetPrintEvent(this);
    }

    @Override
    public String toString() {
        return text;
    }
}
