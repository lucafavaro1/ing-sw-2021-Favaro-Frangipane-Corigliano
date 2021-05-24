package it.polimi.ingsw.common.viewEvents;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.networkCommunication.GsonSerializerDeserializer;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

// TODO javadoc
public class PrintEvent<T> extends Event {
    protected String nickname;
    protected PrintObjects_Enum printType;
    protected T toPrint;

    public PrintEvent(String nickname, T toPrint) {
        this(toPrint);
        this.nickname = nickname;
    }

    public PrintEvent(T toPrint) {
        eventType = Events_Enum.PRINT_MESSAGE;
        this.toPrint = toPrint;
    }

    @Override
    public void handle(Object userInterfaceObj) {
        UserInterface userInterface = ((UserInterface) userInterfaceObj);

        userInterface.printMessage(toPrint);
    }

    @Override
    public String getJsonFromEvent() {
        // not deserializing recursive references
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getDeclaredType().equals(Game.class) || field.getDeclaredType().equals(Player.class)
                        || field.getDeclaredType().equals(HumanPlayer.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        Gson gson = GsonSerializerDeserializer.getGson();
        return gson.toJson(this);
    }
}