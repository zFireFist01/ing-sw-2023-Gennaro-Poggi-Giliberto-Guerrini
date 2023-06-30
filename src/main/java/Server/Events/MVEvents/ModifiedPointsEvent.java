package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * Class representing a ModifiedPointsEvent. This event is triggered when there's a change in the points of a player,
 * typically due to the completion of a common goal card or being the first to complete a bookshelf.
 * This class extends MVEvent.
 * @author Paolo Gennaro
 *
 * @see MVEvent
 */
public class ModifiedPointsEvent extends MVEvent{
    @Expose
    private final String secondaryType = "ModifiedPointsEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    /**
     * Constructor for the ModifiedPointsEvent. Initializes the event with the provided LightMatch object.
     *
     * @param match LightMatch object representing a lightweight version of a match.
     */
    public ModifiedPointsEvent(LightMatch match){
        this.methodName = "onModifiedPointsEvent";
        this.match = match;
    }

    /**
     * Provides the LightMatch object associated with this event.
     * Overrides the abstract method from the MVEvent class.
     *
     * @return A LightMatch object representing a lightweight version of a match.
     * @see MVEvent#getMatch()
     */
    public LightMatch getMatch() {
        return this.match;
    }

    /**
     * Provides the name of the method that the event will trigger.
     * Overrides the abstract method from the MVEvent class.
     *
     * @return A String containing the name of the method that the event will trigger.
     * @see MVEvent#getMethodName()
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * Provides the value associated with this event, which is null for this type of event.
     * Overrides the method from the MVEvent class.
     *
     * @return null, as there's no value associated with this event.
     * @see MVEvent#getValue()
     */
    public Object getValue(){
        return null;
    }

    /**
     * Provides the type of this specific MVEvent, which is "ModifiedPointsEvent".
     * Overrides the abstract method from the MVEvent class.
     *
     * @return A String representing the type of the event, which is "ModifiedPointsEvent".
     * @see MVEvent#getType()
     */
    @Override
    public String getType() {
        return "ModifiedPointsEvent";
    }
}
