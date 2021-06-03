package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.PlayerRequest;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Enumerations of the possible print messages that the server can send to the client
 * in order to update the view
 */
public enum PrintObjects_Enum implements PlayerRequest {
    PLAYER("View other players PunchBoard", PrintPlayerEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            List<String> nicks = player.getGame().getPlayers().stream().map(Player::getNickname).collect(Collectors.toList());
            return new PrintPlayerEvent(
                    player.getGame().getPlayers().get(nicks.indexOf(
                            (new MakePlayerChoose<>("Choose a player :", nicks)).choose(player))
                    )
            );
        }
    },
    MARKET_TRAY("View the MarketTray", PrintMarketTrayEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintMarketTrayEvent(player.getGame());
        }
    },
    DC_BOARD("View the Development Card Board", PrintDcBoardEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintDcBoardEvent(player.getGame());
        }
    },
    PERSONAL_DC_BOARD("View your Development Cards", PrintDevelopmentCardsEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintDevelopmentCardsEvent(player);
        }
    },
    FAITH_TRACK("View your FaithTrack", PrintFaithtrackEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintFaithtrackEvent(player);
        }
    },
    LEADER_CARDS("View your Leader Cards", PrintLeaderCardsEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintLeaderCardsEvent(player);
        }
    },
    WAREHOUSE("View your Deposits", PrintWarehouseEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintWarehouseEvent(player);
        }
    },
    STRONGBOX("View your StrongBox", PrintStrongboxEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintStrongboxEvent(player);
        }
    },
    PRODUCTIONS_ADDED("View all the productions you have added", PrintProductionsAddedEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintProductionsAddedEvent(player);
        }
    },
    PRODUCTIONS_AVAILABLE("View the Productions you can activate", PrintProductionsAvailableEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintProductionsAvailableEvent(player);
        }
    },
    ACTION_CARD("View the last Action Card drawn by Lorenzo", PrintActionCardEvent.class){
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintActionCardEvent(null);
        }
    };

    private final String text;
    private final Class<?> equivalentClass;

    PrintObjects_Enum(String text, Class<?> equivalentClass) {
        this.text = text;
        this.equivalentClass = equivalentClass;
    }

    public abstract PrintEvent<?> getRelativePrintEvent(HumanPlayer player);

    public Class<?> getEquivalentClass() {
        return equivalentClass;
    }

    @Override
    public Event getRelativeEvent(UserInterface userInterface) {
        return new GetPrintEvent(this);
    }

    @Override
    public String toString() {
        return text;
    }
}
