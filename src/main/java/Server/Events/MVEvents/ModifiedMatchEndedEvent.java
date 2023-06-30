package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * Represents a ModifiedMatchEndedEvent, which is sent when a match is finished and provides information about the total scores
 * reached by each player and displays the scoreboard. This event extends the MVEvent class.
 * @author Valentino Guerrini
 *
 * @see MVEvent
 */
 public class ModifiedMatchEndedEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedMatchEndedEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;


    /**
     * Constructs a ModifiedMatchEndedEvent object with the specified match.
     *
     * @param match The LightMatch object representing the match.
     */
    public ModifiedMatchEndedEvent(LightMatch match){
        this.methodName = "onModifiedMatchEndedEvent";
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
     * Returns the primary type of the event.
     *
     * @return The primary type of the event.
     * @see MVEvent#getType()
     */
    @Override
    public String getType() {
        return "ModifiedMatchEndedEvent";
    }
}
