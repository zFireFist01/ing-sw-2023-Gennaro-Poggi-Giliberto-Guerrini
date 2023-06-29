package Server.Events.VCEvents;


/**
 * Class representing an OpenChat event, a type of VCEvent.
 * This event is triggered when a player opens the chat within the Command Line Interface (CLI) of the game.
 * It is not necessary for the Graphical User Interface (GUI) as the chat is always open.
 * It extends the abstract class VCEvent.
 * @author Valentino Guerrini & Patrick Poggi
 * @see VCEvent
 */
public class OpenChat extends VCEvent{
    private final String secondaryType = "OpenChat";
    private final String methodname;

    /**
     * Constructor for the OpenChat event.
     * Initializes the event and sets the method name as "onOpenChatEvent".
     */
    public OpenChat(){
        this.methodname = "onOpenChatEvent";
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the OpenChat event.
     *
     * @return A String representing the name of the method, which is "onOpenChatEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName(){
        return this.methodname;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Since opening chat in CLI does not involve a specific value, it returns null.
     *
     * @return null as there's no specific value associated with this event.
     * @see VCEvent#getValue()
     */
    public Object getValue(){
        return null;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the OpenChat event, which is "OpenChat".
     *
     * @return A String representing the type of the event, which is "OpenChat".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "OpenChat";
    }

}
