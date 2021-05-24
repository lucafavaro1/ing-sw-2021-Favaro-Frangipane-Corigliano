package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.PlayerRequest;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.util.List;
import java.util.stream.Collectors;

// TODO add javadoc
public enum PrintObjects_Enum implements PlayerRequest {
    PLAYER("Visualizza la situazione dei giocatori", PrintPlayerEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            List<String> nicks = player.getGame().getPlayers().stream().map(Player::getNickname).collect(Collectors.toList());
            return new PrintPlayerEvent(
                    player.getGame().getPlayers().get(nicks.indexOf(
                            (new MakePlayerChoose<>("Scegli il giocatore di cui vuoi vedere la plancia", nicks)).choose(player))
                    )
            );
        }
    },
    MARKET_TRAY("Visualizza la Plancia Mercato", PrintMarketTrayEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintMarketTrayEvent(player.getGame());
        }
    },
    DC_BOARD("Visualizza la Plancia delle Carte Sviluppo", PrintDcBoardEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintDcBoardEvent(player.getGame());
        }
    },
    PERSONAL_DC_BOARD("Visualizza le tue Carte Sviluppo", PrintDevelopmentCardsEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintDevelopmentCardsEvent(player);
        }
    },
    FAITH_TRACK("Visualizza il tuo Tracciato Fede", PrintFaithtrackEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintFaithtrackEvent(player);
        }
    },
    LEADER_CARDS("Visualizza le tue Carte Leader", PrintLeaderCardsEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintLeaderCardsEvent(player);
        }
    },
    WAREHOUSE("Visualizza il tuo Deposito", PrintWarehouseEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintWarehouseEvent(player);
        }
    },
    STRONGBOX("Visualizza il tuo Forziere", PrintStrongboxEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintStrongboxEvent(player);
        }
    },
    PRODUCTIONS_ADDED("view all the productions you have added", PrintProductionsAddedEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintProductionsAddedEvent(player);
        }
    },
    PRODUCTIONS_AVAILABLE("Visualizza tutte le Produzioni Attivabili", PrintProductionsAvailableEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintProductionsAvailableEvent(player);
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
