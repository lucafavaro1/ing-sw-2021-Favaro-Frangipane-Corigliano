package it.polimi.ingsw.common.networkCommunication;

import com.google.gson.*;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.viewEvents.PrintEvent;
import it.polimi.ingsw.common.viewEvents.PrintObjects_Enum;
import it.polimi.ingsw.server.GameClientHandler;
import it.polimi.ingsw.server.controller.MakePlayerChoose;
import it.polimi.ingsw.server.model.DeckOfCards;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderAbility;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Production;
import it.polimi.ingsw.server.model.RequirementsAndProductions.Res_Enum;
import it.polimi.ingsw.server.model.SerializationType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonSerializerDeserializer {
    private static Gson gson = null;

    public static Gson getGson() {
        if (gson != null)
            return gson;

        GsonBuilder gsonBuilder = new GsonBuilder();

        // Not serializing recursive references (Player, HumanPlayer, CPUPlayer, Game)
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getDeclaredType().equals(Game.class) || field.getDeclaredType().equals(Player.class)
                        || field.getDeclaredType().equals(HumanPlayer.class) || field.getDeclaredType().equals(CPUPlayer.class)
                        || field.getDeclaredType().equals(GameClientHandler.class) || field.getDeclaredType().equals(DeckOfCards.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        gsonBuilder.setExclusionStrategies(strategy);

        // Deserializer for the LeaderAbility
        gsonBuilder.registerTypeAdapter(LeaderAbility.class, new LeaderAbilitySerializerDeserializer());

        // Serializer and Deserializer for Tuples
        gsonBuilder.registerTypeAdapter(Tuple.class, new TupleSerializerDeserializer());

        Gson gson1 = gsonBuilder.create();

        // Serializer and Deserializer for Players
        gsonBuilder.registerTypeAdapter(Player.class, new PlayerSerializerDeserializer(gson1));

        Gson gson2 = gsonBuilder.create();

        // Deserializer for the PrintEvents
        JsonDeserializer<?> printEventDeserializer = (JsonDeserializer<PrintEvent<?>>) (json, typeOfT, context) -> {
            PrintObjects_Enum printType = gson2.fromJson(json.getAsJsonObject().get("printType"), PrintObjects_Enum.class);
            if (printType == null) {
                return gson2.fromJson(json, (Type) Events_Enum.PRINT_MESSAGE.getEventClass());
            }

            return gson2.fromJson(json, (Type) printType.getEquivalentClass());
        };
        gsonBuilder.registerTypeAdapter(PrintEvent.class, printEventDeserializer);

        // Deserializer for MakePlayerChoose
        JsonDeserializer<?> makePlayerChooseDeserializer = (JsonDeserializer<MakePlayerChoose<?>>) (json, typeOfT, context) -> {
            String message = json.getAsJsonObject().get("message").getAsString();

            JsonArray objects = json.getAsJsonObject().get("toBeChosen").getAsJsonArray();

            List<Object> toBeChosen = new ArrayList<>();
            for (JsonElement jsonElement : objects) {
                try {
                    // trying to deserialize with the serializationType
                    SerializationType type = gson2.fromJson(jsonElement.getAsJsonObject().get("serializationType"), SerializationType.class);
                    toBeChosen.add(gson2.fromJson(jsonElement, (Type) type.getType()));
                } catch (JsonSyntaxException | IllegalStateException | NullPointerException e) {
                    try {
                        // trying to deserialize as Production
                        Production production = gson2.fromJson(jsonElement, Production.class);
                        if (production == null)
                            throw new JsonSyntaxException("");
                        toBeChosen.add(production);
                    } catch (JsonSyntaxException | IllegalStateException | NullPointerException e1) {
                        try {
                            // trying to deserialize as Res_Enum
                            Res_Enum res_enum = gson2.fromJson(jsonElement, Res_Enum.class);
                            if (res_enum == null)
                                throw new JsonSyntaxException("");
                            toBeChosen.add(res_enum);
                        } catch (JsonSyntaxException | IllegalStateException | NullPointerException e2) {
                            // trying to deserialize as Strings
                            toBeChosen.add(gson2.fromJson(jsonElement, String.class));
                        }
                    }
                }
            }

            return new MakePlayerChoose<>(message, toBeChosen);
        };
        gsonBuilder.registerTypeAdapter(MakePlayerChoose.class, makePlayerChooseDeserializer);

        gson = gsonBuilder.create();

        return gson;
    }
}

class TupleSerializerDeserializer implements JsonSerializer<Tuple>, JsonDeserializer<Tuple> {

    @Override
    public Tuple deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Tuple tuple;
        Gson gson1 = new Gson();
        if (json.isJsonObject()) {
            tuple = new Tuple(
                    gson1.fromJson(json.getAsJsonObject().get("type").toString(), TypeDevCards_Enum.class),
                    json.getAsJsonObject().get("level").getAsInt()
            );
        } else {
            String[] s = json.getAsString().split(" level ", 2);  // split into two (and only two)
            tuple = new Tuple(
                    gson1.fromJson(s[0], TypeDevCards_Enum.class),
                    Integer.parseInt(s[1])
            );
        }

        return tuple;
    }

    @Override
    public JsonElement serialize(Tuple src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();
        jo.addProperty("type", src.getType().toString());
        jo.addProperty("level", src.getLevel());
        return jo;
    }
}

class LeaderAbilitySerializerDeserializer implements JsonSerializer<LeaderAbility>, JsonDeserializer<LeaderAbility> {
    Gson gson = new Gson();

    @Override
    public LeaderAbility deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Abil_Enum abilityType = gson.fromJson(json.getAsJsonObject().get("abilityType").toString(), Abil_Enum.class);

        return gson.fromJson(json, (Type) abilityType.getEventClass());
    }

    @Override
    public JsonElement serialize(LeaderAbility src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jo = new JsonObject();

        jo.addProperty("abilityType", src.getAbilityType().toString());
        String converted = gson.toJson(src.getAbilityType().getEventClass().cast(src));

        return gson.fromJson(converted, JsonElement.class);
    }
}

class PlayerSerializerDeserializer implements JsonSerializer<Player>, JsonDeserializer<Player> {

    private final Gson gson;

    public PlayerSerializerDeserializer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Player deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json.getAsJsonObject().get("nickname").getAsString().equals("Lorenzo (CPU)")) {
            return gson.fromJson(json, CPUPlayer.class);
        } else {
            return gson.fromJson(json, HumanPlayer.class);
        }
    }

    @Override
    public JsonElement serialize(Player src, Type typeOfSrc, JsonSerializationContext context) {
        String converted;
        if (src.getNickname().equals("Lorenzo (CPU)")) {
            converted = gson.toJson((CPUPlayer) src);
        } else {
            converted = gson.toJson((HumanPlayer) src);
        }

        return gson.fromJson(converted, JsonElement.class);
    }
}
