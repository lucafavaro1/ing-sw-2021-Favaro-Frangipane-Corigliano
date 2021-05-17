package it.polimi.ingsw.common.viewEvents;

import com.google.gson.*;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.ActionDoneEvent;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Leader.Abil_Enum;
import it.polimi.ingsw.server.model.Leader.LeaderAbility;
import it.polimi.ingsw.server.model.Player.HumanPlayer;
import it.polimi.ingsw.server.model.Player.Player;

import java.lang.reflect.Type;

// TODO javadoc
public class PrintEvent<T> extends Event {
    protected PrintObjects_Enum printType;
    protected T toPrint;

    public PrintEvent() {
        eventType = Events_Enum.PRINT_MESSAGE;
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

    public static PrintEvent<?> getEventFromJson(String jsonPrintEvent) {
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

        PrintObjects_Enum printType = gson.fromJson(
                JsonParser.parseString(jsonPrintEvent).getAsJsonObject().get("printType"),
                PrintObjects_Enum.class
        );

        if(printType == null){
            return gson.fromJson(jsonPrintEvent, (Type) Events_Enum.PRINT_MESSAGE.getEventClass());
        }

        System.out.println("printEvent: " + jsonPrintEvent);
        return gson.fromJson(jsonPrintEvent, (Type) printType.getEquivalentClass());
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
}