package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * Class representing a ModifiedBookshelfEvent. This event is triggered when there's a change in the bookshelf of the game,
 * when a player insert one or more tiles in his bookshelf. This class extends MVEvent.
 * @author Paolo Gennaro
 *
 * @see MVEvent
 */
public class ModifiedBookshelfEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedBookshelfEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    /**
     * Constructor for the ModifiedBookshelfEvent. Initializes the event with the provided LightMatch object.
     *
     * @param match LightMatch object representing a lightweight version of a match.
     */
    public ModifiedBookshelfEvent(LightMatch match){
        this.methodName = "onModifiedBookshelfEvent";
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
     * Provides the type of the event, which is "ModifiedBookshelfEvent" for this type of event.
     * Overrides the method from the MVEvent class.
     *
     * @return A String containing the type of the event.
     * @see MVEvent#getType()
     */
    @Override
    public String getType() {
        return "ModifiedBookshelfEvent";
    }
}
