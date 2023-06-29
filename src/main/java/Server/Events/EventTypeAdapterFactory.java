package Server.Events;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * EventTypeAdapterFactory is a TypeAdapterFactory which produces TypeAdapter instances for handling Event objects.
 * It is used by Gson to serialize and deserialize Event objects.
 * @author Valentino Guerrini
 */
public class EventTypeAdapterFactory implements TypeAdapterFactory {

    /**
     * Returns a TypeAdapter that can process Event objects.
     * If the provided type does not match Event class, null is returned.
     *
     * @param gson The Gson instance to use for processing.
     * @param type The type of object for which a TypeAdapter is desired.
     * @return A TypeAdapter that can process Event objects, or null if the type does not match.
     */
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Event.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        TypeAdapter<T> tTypeAdapter = (TypeAdapter<T>) new EventAdapter(gson);

        return tTypeAdapter;
    }
}
