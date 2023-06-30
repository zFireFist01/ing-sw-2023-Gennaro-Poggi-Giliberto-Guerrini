package Server.Events.VCEvents;

import Server.Events.Event;

/**
 * Abstract class representing events that are triggered from the view and are meant to be
 * handled by the controller. This class extends the Event class, adding functionality
 * specific to View-Controller (VC) events.
 * @author Patrick Poggi
 */
public abstract class VCEvent extends Event {
    private final String primaryType = "VCEvent";

    /**
     * Abstract method which must be implemented by subclasses to return the name of the
     * method that is associated with the event.
     *
     * @return a String representing the name of the method.
     */
    public abstract String getMethodName();

    /**
     * This method returns the value associated with the event. By default, it returns null.
     * Subclasses may override this method to return relevant event-specific values.
     *
     * @return an Object that represents the value of the event, null by default.
     */
    public Object getValue() {
        return null;
    }

    /**
     * Abstract method which must be implemented by subclasses to return the specific type
     * of the event (the name). This can be used to differentiate between different kinds of VCEvents.
     *
     * @return a String representing the specific type of the VCEvent.
     */
    public abstract String getType();

    /**
     * This method returns the primary type of the VCEvent, which is "VCEvent". It is the same
     * for all instances of this class and its subclasses.
     *
     * @return a String representing the primary type of the VCEvent, which is "VCEvent".
     */
    public String getPrimaryType() {
        return "VCEvent";
    }

}
