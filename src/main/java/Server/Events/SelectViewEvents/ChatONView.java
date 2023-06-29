package Server.Events.SelectViewEvents;

/**
 * Class representing a ChatONView, a type of SelectViewEvent.
 * This view is shown when the chat is opened in the Command Line Interface (CLI).
 * In the Graphical User Interface (GUI), the chat is always open, so this view is not used.
 * It extends the SelectViewEvent class.
 * @author Paolo Gennaro
 * @see SelectViewEvent
 */
public class ChatONView extends SelectViewEvent {
    private final String secondaryType = "ChatONView";
    private final String message;

    /**
     * Default constructor for the ChatONView.
     * Initializes the view with a default message of "Chat is now on".
     */
    public ChatONView() {
        this.message = "Chat is now on";
    }

    /**
     * Provides the message associated with the ChatONView (Chat is now on).
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the message of the view.
     * @see SelectViewEvent#getMessage()
     */
    public String getMessage() {
        return this.message;
    }

    /**
     * Provides the type of this specific SelectViewEvent, which is "ChatONView".
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the type of the view, which is "ChatONView".
     * @see SelectViewEvent#getType()
     */
    public String getType() {
        return "ChatONView";
    }
}
