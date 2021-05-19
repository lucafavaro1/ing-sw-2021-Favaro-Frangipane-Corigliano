package it.polimi.ingsw.common;

import com.google.gson.*;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.viewEvents.PrintEvent;
import it.polimi.ingsw.common.viewEvents.PrintObjects_Enum;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderAbility;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.lang.reflect.Type;

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

        Gson gson = new GsonBuilder().setExclusionStrategies(strategy)
                .registerTypeAdapter(
                        Tuple.class,
                        new JsonSerializer<Tuple>() {
                            @Override
                            public JsonElement serialize(Tuple src, Type typeOfSrc, JsonSerializationContext context) {
                                JsonObject jo = new JsonObject();
                                jo.addProperty("type", src.getType().toString());
                                jo.addProperty("level", src.getLevel());
                                return jo;
                            }
                        }
                ).create();
        return gson.toJson(this);
    }

    public static <T> Message fromJson(String jsonMessage, Class<T> type) {
        if (jsonMessage == null)
            return null;

        // creating a type adapter for the Tuple
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(
                        Tuple.class,
                        new JsonDeserializer<Tuple>() {
                            @Override
                            public Tuple deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                Tuple tuple;
                                if (json.isJsonObject()) {
                                    tuple = new Tuple(
                                            (new Gson()).fromJson(json.getAsJsonObject().get("type").toString(), TypeDevCards_Enum.class),
                                            json.getAsJsonObject().get("level").getAsInt()
                                    );
                                } else {
                                    String[] s = json.getAsString().split(" level ", 2);  // split into two (and only two)
                                    tuple = new Tuple(
                                            (new Gson()).fromJson(s[0], TypeDevCards_Enum.class),
                                            Integer.parseInt(s[1])
                                    );
                                }

                                return tuple;
                            }
                        }
                );

        // creating a type adapter for the ability type
        gsonBuilder.registerTypeAdapter(
                LeaderAbility.class,
                new JsonDeserializer<LeaderAbility>() {

                    @Override
                    public LeaderAbility deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        Gson gson1 = new Gson();
                        Abil_Enum abilityType = gson1.fromJson(json.getAsJsonObject().get("abilityType").toString(), Abil_Enum.class);

                        return gson1.fromJson(json, (Type) abilityType.getEventClass());
                    }
                }
        );

        // creating a type adapter for the dcBoard
        gsonBuilder.registerTypeAdapter(
                PrintEvent.class,
                new JsonDeserializer<PrintEvent<?>>() {
                    @Override
                    public PrintEvent<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        Gson gson1 = new Gson();
                        PrintObjects_Enum printType = gson1.fromJson(json.getAsJsonObject().get("printType"), PrintObjects_Enum.class);
                        if(printType == null){
                            return gson1.fromJson(json, (Type) Events_Enum.PRINT_MESSAGE.getEventClass());
                        }

                        return gson1.fromJson(json, (Type) printType.getEquivalentClass());
                    }
                }
        );

        Gson gson = gsonBuilder.create();


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
