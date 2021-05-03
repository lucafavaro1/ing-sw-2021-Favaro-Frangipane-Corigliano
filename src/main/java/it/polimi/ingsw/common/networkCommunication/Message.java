package it.polimi.ingsw.common.networkCommunication;

import com.google.gson.Gson;

/**
 * Class representing messages send between client and server (ID + message)
 */
public class Message {
    private final int idMessage;
    private final Object message;

    public Message(int idMessage, Object message) {
        this.idMessage = idMessage;
        this.message = message;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public Object getMessage() {
        return message;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static Message fromJson(String jsonMessage) {
        Gson gson = new Gson();
        if (jsonMessage == null)
            return null;
        
        Message message = gson.fromJson(jsonMessage, Message.class);
        if (message == null || (message.getIdMessage() == 0 && message.getMessage() == null))
            return null;

        return message;
    }


}
