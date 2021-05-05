package it.polimi.ingsw.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.Market.MarketMarble;

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

    public static <T> Message fromJson(String jsonMessage, Class<T> type) {
        Gson gson = new Gson();
        if (jsonMessage == null)
            return null;

        JsonObject messageObj = gson.fromJson(jsonMessage, JsonObject.class);
        Message message = null;
        try {
            message = new Message(messageObj.get("idMessage").getAsInt(), gson.fromJson(messageObj.get("message"), type));
        } catch (JsonSyntaxException | NullPointerException ignored) {
        }

        if (message == null || (message.getIdMessage() == 0 && message.getMessage() == null))
            return null;

        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "idMessage=" + idMessage +
                ", message=" + message +
                '}';
    }
}
