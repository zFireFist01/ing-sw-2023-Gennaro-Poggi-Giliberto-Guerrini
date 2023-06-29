package Server.Events;


/**
 * The abstract base class for all events. Each specific event type extends this class and provides
 */
public abstract class Event {

    /**
     * Retrieves the primary type of the event. This method must be overridden by all subclasses
     * to return the appropriate primary type.
     *
     * @return A string representing the primary type of the event.
     */
    public abstract String getPrimaryType();
}
