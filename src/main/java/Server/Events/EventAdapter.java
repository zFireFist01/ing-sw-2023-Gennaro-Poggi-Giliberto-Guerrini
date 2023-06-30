package Server.Events;
import Server.Events.MVEvents.MVEvent;
import Server.Events.MVEvents.MatchStartedEvent;
import Server.Events.MVEvents.*;
import Server.Events.VCEvents.*;
import Server.Events.SelectViewEvents.*;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;


/**
 * EventAdapter is a specific implementation of a TypeAdapter for the Event type.
 * It provides custom deserialization logic for reading Event objects from JSON input.
 * @author Valentino Guerrini
 */
public class EventAdapter extends TypeAdapter<Event> {
    private Gson gson;

    /**
     * Constructs a new EventAdapter with a provided Gson instance.
     *
     * @param gson the Gson instance to use for JSON processing
     */

    public EventAdapter(Gson gson) {
        this.gson = gson;
    }

    /**
     * Throws an UnsupportedOperationException, as write functionality is not implemented.
     *
     * @param out   the JSON writer
     * @param value the Event object to serialize
     * @throws UnsupportedOperationException as write functionality is not implemented
     */
    @Override
    public void write(JsonWriter out, Event value) throws IOException {
        throw new UnsupportedOperationException("Serializzazione non implementata");
    }

    /**
     * Reads an Event object from a JSONReader instance.
     * The object's type is determined by the 'primaryType', 'secondaryType' and 'thirdType' fields in the input JSON.
     *
     * @param in the JSON reader from which to read the Event object
     * @return the Event object read from the JSON input
     * @throws IOException if an error occurs during reading from the JSON reader, or if expected fields are missing in the input JSON
     */
    @Override
    public Event read(JsonReader in) throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardAdapter())
                .create();
        JsonParser jsonParser = new JsonParser();

        JsonElement fullElement = jsonParser.parse(in);
        JsonObject jsonObject = fullElement.getAsJsonObject();

        String primaryType = null;
        String secondaryType = null;
        String thirdType = null;
        if (jsonObject.has("primaryType")) {
            primaryType = jsonObject.get("primaryType").getAsString();
        } else {
            throw new IOException("Il campo 'primaryType' non è stato trovato");
        }

        if (jsonObject.has("secondaryType")) {
            secondaryType = jsonObject.get("secondaryType").getAsString();
        } else {
            throw new IOException("Il campo 'secondaryType' non è stato trovato");
        }

        if (jsonObject.has("thirdType"))
            thirdType = jsonObject.get("thirdType").getAsString();

        Class<?> eventClass = null;

        switch (primaryType) {
            case "MVEvent":
                switch(secondaryType) {
                    case "MatchStartedEvent":
                        eventClass = MatchStartedEvent.class;
                        break;
                    case "ModifiedBookshelfEvent":
                        eventClass = ModifiedBookshelfEvent.class;
                        break;
                    case "ModifiedChatEvent":
                        eventClass = ModifiedChatEvent.class;
                        break;
                    case "ModifiedLivingRoomEvent":
                        eventClass = ModifiedLivingRoomEvent.class;
                        break;
                    case "ModifiedMatchEndedEvent":
                        eventClass = ModifiedMatchEndedEvent.class;
                        break;
                    case "ModifiedPointsEvent":
                        eventClass = ModifiedPointsEvent.class;
                        break;
                    case "ModifiedTurnEvent":
                        eventClass = ModifiedTurnEvent.class;
                        break;

                    default:
                        throw new IOException("Unknown event type: " + secondaryType);
                }
                break;
            case "SelectViewEvent":
                switch(secondaryType){
                    case "ChatOFFView":
                        eventClass = ChatOFFView.class;
                        break;
                    case "ChatONView":
                        eventClass = ChatONView.class;
                        break;
                    case "EndedMatchView":
                        eventClass = EndedMatchView.class;
                        break;
                    case "GameView":
                        if(thirdType!=null){
                            switch(thirdType){
                                case "InsertingTilesGameView":
                                    eventClass = InsertingTilesGameView.class;
                                    break;
                                case "PickingTilesGameView":
                                    eventClass = PickingTilesGameView.class;
                                    break;
                            }
                        }else{
                            eventClass = GameView.class;
                        }
                        break;

                    case "LoginView":
                        eventClass = LoginView.class;
                        break;

                    default:
                        throw new IOException("Unknown secondary event type: " + secondaryType);

                }
                break;
            case "VCEvent":
                switch(secondaryType){
                    case "CheckOutTiles":
                        eventClass = CheckOutTiles.class;
                        break;
                    case "ClickOnTile":
                        eventClass = ClickOnTile.class;
                        break;
                    case "CloseChat":
                        eventClass = CloseChat.class;
                        break;
                    case "LoginEvent":
                        eventClass = LoginEvent.class;
                        break;
                    case "OpenChat":
                        eventClass = OpenChat.class;
                        break;
                    case "SelectColumn":
                        eventClass = SelectColumn.class;
                        break;
                    case "SendMessage":
                        eventClass = SendMessage.class;
                        break;
                }
                break;
            default:
                throw new IOException("Unknown primary event type: " + primaryType);
        }

        return (Event) gson.fromJson(fullElement, eventClass);
    }
}