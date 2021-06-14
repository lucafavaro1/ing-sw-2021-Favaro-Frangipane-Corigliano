package it.polimi.ingsw.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import it.polimi.ingsw.common.networkCommunication.GsonSerializerDeserializer;
import it.polimi.ingsw.server.controller.MakePlayerChoose;

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

    /**
     * converts the Message to a string in the Json format
     *
     * @return the json serialization of the Message
     */
    public String toJson() {
        Gson gson = GsonSerializerDeserializer.getGson();

        return gson.toJson(this);
    }

    /**
     * converts a string to a Message object
     *
     * @return the Message object deserialized from the string in the json format
     */
    public static <T> Message fromJson(String jsonMessage, Class<T> type) {
        if (jsonMessage == null)
            return null;

        Gson gson = GsonSerializerDeserializer.getGson();
        JsonObject messageObj = gson.fromJson(jsonMessage, JsonObject.class);

        Message message = null;
        try {
            // if it's a MakePlayerChoose
            message = new Message(
                    messageObj.get("idMessage").getAsInt(),
                    gson.fromJson(messageObj.get("message"), MakePlayerChoose.class)
            );
        } catch (JsonSyntaxException | NullPointerException ignored) {
            try {
                // if it's a response of the MakePlayerChoose
                message = new Message(messageObj.get("idMessage").getAsInt(), messageObj.get("message").getAsInt());
            } catch (JsonSyntaxException | NullPointerException ignored1) {
            }
        }

        if (message == null || (message.getIdMessage() == 0 && message.getMessage() == null))
            return null;

        return message;
    }

    @Override
    public String toString() {
        return "Message {" +
                "idMessage=" + idMessage +
                ", message=" + message +
                '}';
    }
}
