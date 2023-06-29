package Server.Events.VCEvents;

/**
 * Class representing a CloseChat event, a type of VCEvent.
 * This event is triggered when a player closes the chat within the Command Line Interface (CLI) of the game.
 * It is not necessary for the Graphical User Interface (GUI) as the chat is always open.
 * It extends the abstract class VCEvent.
 * @author Valentino Guerrini & Patrick Poggi
 *
 * @see VCEvent
 */
public class CloseChat extends VCEvent{
    private final String secondaryType = "CloseChat";
    private final String methodName;

    /**
     * Constructor for the CloseChat event.
     * Initializes the event and sets the method name as "onCloseChatEvent".
     */
    public CloseChat() {
        this.methodName = "onCloseChatEvent";
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the CloseChat event.
     *
     * @return A String representing the name of the method, which is "onCloseChatEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Since closing chat in CLI does not involve a specific value, it returns null.
     *
     * @return null as there's no specific value associated with this event.
     * @see VCEvent#getValue()
     */
    public Object getValue() {
        return null;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the CloseChat event, which is "CloseChat".
     *
     * @return A String representing the type of the event, which is "CloseChat".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "CloseChat";
    }
}
