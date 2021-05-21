package it.polimi.ingsw.common.networkCommunication;

import com.google.gson.*;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.common.viewEvents.PrintEvent;
import it.polimi.ingsw.common.viewEvents.PrintObjects_Enum;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderAbility;
import it.polimi.ingsw.server.model.Player.CPUPlayer;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.lang.reflect.Type;

import static java.lang.reflect.Modifier.TRANSIENT;

public class GsonSerializerDeserializer {
    private static Gson gson = null;

    public static Gson getGson() {
        if (gson != null)
            return gson;

        Gson gson1 = new Gson();

        GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithModifiers(TRANSIENT);

        // Not deserializing recursive references (Player, HumanPlayer, CPUPlayer, Game)
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getDeclaredType().equals(Game.class) || field.getDeclaredType().equals(Player.class)
                        || field.getDeclaredType().equals(HumanPlayer.class) || field.getDeclaredType().equals(CPUPlayer.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
        gsonBuilder.setExclusionStrategies(strategy);

        // Deserializer for the LeaderAbility
        JsonDeserializer<?> leaderAbilityDeserializer = (JsonDeserializer<LeaderAbility>) (json, typeOfT, context) -> {
            Abil_Enum abilityType = gson1.fromJson(json.getAsJsonObject().get("abilityType").toString(), Abil_Enum.class);

            return gson1.fromJson(json, (Type) abilityType.getEventClass());
        };
        gsonBuilder.registerTypeAdapter(LeaderAbility.class, leaderAbilityDeserializer);

        // Serializer and Deserializer for Tuples
        gsonBuilder.registerTypeAdapter(Tuple.class, new TupleSerializerDeserializer());

        // Deserializer for the PrintEvents
        Gson gson2 = new GsonBuilder().registerTypeAdapter(Tuple.class, new TupleSerializerDeserializer())
                .registerTypeAdapter(LeaderAbility.class, leaderAbilityDeserializer).create();
        JsonDeserializer<?> printEventDeserializer = (JsonDeserializer<PrintEvent<?>>) (json, typeOfT, context) -> {
            PrintObjects_Enum printType = gson2.fromJson(json.getAsJsonObject().get("printType"), PrintObjects_Enum.class);
            if (printType == null) {
                return gson2.fromJson(json, (Type) Events_Enum.PRINT_MESSAGE.getEventClass());
            }

            return gson2.fromJson(json, (Type) printType.getEquivalentClass());
        };
        gsonBuilder.registerTypeAdapter(PrintEvent.class, printEventDeserializer);

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
