package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * Represents a ModifiedLivingRoomEvent, which is sent when a player pick a tiles from the living room and provides information about
 * the current state of the livingroom. This event extends the MVEvent class.
 * @author Patrick Poggi
 *
 * @see MVEvent
 */
public class ModifiedLivingRoomEvent extends MVEvent{
    @Expose
    private final String secondaryType = "ModifiedLivingRoomEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

/**
     * Constructs a ModifiedLivingRoomEvent object with the specified match.
     *
     * @param match The LightMatch object representing the match.
     */
    public ModifiedLivingRoomEvent(LightMatch match){
        this.methodName = "onModifiedLivingRoomEvent";
        this.match = match;
    }

    /**
     * Returns the lightweight version of the match object.
     *
     * @return The LightMatch object.
     */

    public LightMatch getMatch() {
        return this.match;
    }

    /**
     * Returns the name of the method that the event will trigger.
     *
     * @return The name of the method that the event will trigger.
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * Returns the value associated with this event.
     *
     * @return The value associated with this event.
     */
    public Object getValue(){
        return null;
    }

    /**
     * Returns the type of the event.
     *
     * @return The type of the event.
     */
    @Override
    public String getType() {
        return "ModifiedLivingRoomEvent";
    }
}
