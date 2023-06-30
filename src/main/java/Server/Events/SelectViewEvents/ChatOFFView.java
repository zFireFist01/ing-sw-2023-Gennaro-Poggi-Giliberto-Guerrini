package Server.Events.SelectViewEvents;

/**
 * Class representing a ChatOFFView, a type of SelectViewEvent.
 * This view is shown when the chat is closed in the Command Line Interface (CLI).
 * It extends the SelectViewEvent class.
 * @author Valentino Guerrini
 * @see SelectViewEvent
 */

public class ChatOFFView extends SelectViewEvent{
    private final String secondaryType = "ChatOFFView";

    private final String message;

    /**
     * Default constructor for the ChatOFFView.
     * Initializes the view with a default message of "Chat is now off".
     */
    public ChatOFFView(){
        this.message = "Chat is now off";
    }

    /**
     * Provides the message associated with the ChatOFFView.
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the message of the view.
     * @see SelectViewEvent#getMessage()
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * Provides the type of this specific SelectViewEvent, which is "ChatOFFView".
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the type of the view, which is "ChatOFFView".
     * @see SelectViewEvent#getType()
     */
    public String getType(){
        return "ChatOFFView";
    }
}
