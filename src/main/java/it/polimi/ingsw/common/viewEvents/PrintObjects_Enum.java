package it.polimi.ingsw.common.viewEvents;

import it.polimi.ingsw.client.PlayerRequest;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.server.model.Player.HumanPlayer;

// TODO add javadoc
public enum PrintObjects_Enum implements PlayerRequest {
    MARKET_TRAY("view market tray", PrintMarketTrayEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintMarketTrayEvent(player.getGame());
        }
    },
    DC_BOARD("view development board", PrintDcBoardEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintDcBoardEvent(player.getGame());
        }
    },
    PERSONAL_DC_BOARD("view personal development board", PrintDevelopmentCardsEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintDevelopmentCardsEvent(player);
        }
    },
    FAITH_TRACK("view faith track", PrintFaithtrackEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintFaithtrackEvent(player);
        }
    },
    LEADER_CARDS("view leader cards", PrintLeaderCardsEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintLeaderCardsEvent(player);
        }
    },
    WAREHOUSE("view your warehouse", PrintWarehouseEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintWarehouseEvent(player);
        }
    },
    STRONGBOX("view your strongbx", PrintStrongboxEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintStrongboxEvent(player);
        }
    },
    /*DEPOSITS("view all the resources", PrintResourcesEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintResourcesEvent(player);
        }
    },*/
    PRODUCTIONS_ADDED("view all the productions you have added", PrintProductionsAddedEvent.class) {
        @Override
        public PrintEvent<?> getRelativePrintEvent(HumanPlayer player) {
            return new PrintProductionsAddedEvent(player);
        }
    },
    PRODUCTIONS_AVAILABLE("view all the productions you can do", PrintProductionsAvailableEvent.class) {
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
