package it.polimi.ingsw.common.viewEvents;

import com.google.gson.Gson;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.networkCommunication.GsonSerializerDeserializer;

/**
 * Event sent by the server to the client in order to update the view
 *
 * @param <T> specifies the object that needs to be printed
 */
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
        Gson gson = GsonSerializerDeserializer.getGson();
        return gson.toJson(this);
    }
}