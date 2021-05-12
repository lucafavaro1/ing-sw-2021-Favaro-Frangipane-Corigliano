package it.polimi.ingsw.common.viewEvents;

import com.google.gson.*;
import it.polimi.ingsw.client.UserInterface;
import it.polimi.ingsw.common.Events.ActionDoneEvent;
import it.polimi.ingsw.common.Events.Event;
import it.polimi.ingsw.common.Events.Events_Enum;
import it.polimi.ingsw.server.model.Development.Tuple;
import it.polimi.ingsw.server.model.Development.TypeDevCards_Enum;
import it.polimi.ingsw.server.model.Game;
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
        userInterface.printMessage(toPrint.toString());
        userInterface.getEventBroker().post(new ActionDoneEvent(""), true);
    }

    public static PrintEvent<?> getEventFromJson(String jsonPrintEvent) {
        // creating a type adapter for the Tuple (keys of the Map, that were previously serialized with toString() )
        Gson gson = new GsonBuilder()
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
                ).create();

        PrintObjects_Enum printType = gson.fromJson(
                JsonParser.parseString(jsonPrintEvent).getAsJsonObject().get("printType"),
                PrintObjects_Enum.class
        );

        return gson.fromJson(jsonPrintEvent, (Type) printType.getEquivalentClass());
    }

    @Override
    public String getJsonFromEvent() {
        // not deserializing recursive references
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes field) {
                return field.getDeclaredType().equals(Game.class) || field.getDeclaredType().equals(Player.class);
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