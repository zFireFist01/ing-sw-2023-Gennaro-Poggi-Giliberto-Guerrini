package Server.Events.SelectViewEvents;

/*
    * This class is used to send the SelectView event to the client based on the current state of the players turn
    * @Author Valentino Guerrini
 */

import Server.Events.Event;


/**
 * Abstract class representing a SelectViewEvent, a type of Event.
 * This event is used to communicate from the controller to the view in order to instruct the view
 * on which views needs to be displayed.
 * It extends the Event class.
 * @author Valentino Guerrini
 * @see Event
 */
public abstract class SelectViewEvent extends Event {
    private final String primaryType = "SelectViewEvent";

    /**
     * Abstract method that should provide the type associated with the specific implementation
     * of the SelectViewEvent when implemented in a subclass.
     *
     * @return A String representing the type of the event.
     */
    public abstract String getType();

    /**
     * Abstract method that should provide the message associated with the specific implementation
     * of the SelectViewEvent when implemented in a subclass.
     *
     * @return A String representing the message of the event.
     */
    public abstract String getMessage();

    /**
     * Provides the primary type of the event, which is "SelectViewEvent".
     * Overrides the method from the Event class.
     *
     * @return A String representing the primary type of the event, which is "SelectViewEvent".
     * @see Event#getPrimaryType()
     */
    @Override
    public String getPrimaryType() {
        return "SelectViewEvent";
    }
}
