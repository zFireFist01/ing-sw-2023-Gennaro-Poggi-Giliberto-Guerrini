package Server.Events.MVEvents;

import Server.Events.Event;
import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * An abstract class that represents Model-View (MV) events. These events are sent by the model to the view to notify
 * a change in the state of the game. Extends the Event class.
 * @author Patrick Poggi
 * @see Event
 */
public abstract class MVEvent extends Event {
    @Expose
    private final String primaryType = "MVEvent";

    /**
     * Abstract method that must be implemented by any class that extends MVEvent.
     * The method should return the name of the method that the event will trigger.
     *
     * @return String containing the name of the method that the event should trigger.
     */
    public abstract String getMethodName();

    /**
     * Abstract method that must be implemented by any class that extends MVEvent.
     * The method should return the type of the event.
     *
     * @return String containing the type of the event.
     */
    public abstract String getType();

    /**
     * Method to get the value of the event. By default, it returns null.
     *
     * @return Object representing the value of the event. By default, it returns null.
     */
    public Object getValue() {
        return null;
    }

    /**
     * Abstract method that must be implemented by any class that extends MVEvent.
     * The method should return a LightMatch object, which represents a lightweight version of the match class.
     *
     * @return A LightMatch object representing a lightweight version of the match.
     */
    public abstract LightMatch getMatch();

    /**
     * Method to get the primary type of the event.
     *
     * @return String containing the primary type of the event.
     */
    public String getPrimaryType() {
        return "MVEvent";
    }
}
