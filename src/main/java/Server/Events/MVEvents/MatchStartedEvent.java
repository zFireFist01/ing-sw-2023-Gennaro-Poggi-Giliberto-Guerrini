package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * Class representing a MatchStartedEvent. This event is triggered when a match starts.
 * This class extends MVEvent.
 * @author Valentino Guerrini
 *
 * @see MVEvent
 */
public class MatchStartedEvent extends MVEvent{
    @Expose
    private final String secondaryType = "MatchStartedEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    /**
     * Constructor for the MatchStartedEvent. Initializes the event with the provided LightMatch object.
     *
     * @param match LightMatch object representing a lightweight version of a match.
     */
    public MatchStartedEvent(LightMatch match){
        this.methodName = "onMatchStartedEvent";
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
     * Provides the type of the event, which is "MatchStartedEvent" for this type of event.
     * Overrides the method from the MVEvent class.
     *
     * @return A String containing the type of the event.
     * @see MVEvent#getType()
     */
    @Override
    public String getType() {
        return "MatchStartedEvent";
    }


}
