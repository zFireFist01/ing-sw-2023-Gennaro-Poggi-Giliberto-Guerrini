package Server.Events.MVEvents;

import Server.Model.Chat.Message;
import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;


/**
 * This event is used to notify the client that the chat has been modified.
 * This event extends the MVEvent class.
 * @author Marta Giliberto
 */
public class ModifiedChatEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedChatEvent";
    @Expose
    private final String methodName;
    @Expose
    private final Message message;

    /**
     * Constructor for the ModifiedChatEvent. Initializes the event with the provided Message object.
     *
     * @param message Message object representing a message sent in the chat.
     */
    public ModifiedChatEvent(Message message){
        this.methodName = "onModifiedChatEvent";
        this.message = message;
    }

    /**
     * Provides the name of the method that the event will trigger.
     * Overrides the abstract method from the MVEvent class.
     *
     * @return null beacuse there's no match associated with this event.
     * @see MVEvent#getMethodName()
     */

    public LightMatch getMatch() {
        return null;
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
     * Provides the Message object associated with this event.
     * Overrides the abstract method from the MVEvent class.
     *
     * @return A Message object representing a message sent in the chat.
     * @see MVEvent#getValue()
     */
    public Object getValue(){
        return message;
    }

    /**
     * Provides the type of the event.
     * Overrides the abstract method from the MVEvent class.
     *
     * @return A String containing the type of the event.
     * @see MVEvent#getType()
     */
    @Override
    public String getType() {
        return "ModifiedChatEvent";
    }
}
