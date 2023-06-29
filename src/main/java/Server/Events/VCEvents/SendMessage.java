package Server.Events.VCEvents;

import Server.Model.Chat.Message;

/**
 * Class representing a SendMessage event which is a type of VCEvent.
 * This event is triggered when a chat message is to be sent within the game.
 * It extends the abstract class VCEvent.
 *
 * @see VCEvent
 */
public class SendMessage extends VCEvent{
    private final String secondaryType = "SendMessage";
    private final String methodname;
    private final Message message;

    /**
     * Constructor for SendMessage event.
     * Initializes the event with the provided message and sets the method name as "onSendMessageEvent".
     *
     * @param message The Message object that represents the chat message within the game.
     * @see Message
     */
    public SendMessage(Message message){
        this.message = message;
        this.methodname = "onSendMessageEvent";
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the SendMessage event.
     *
     * @return A String representing the name of the method, which is "onSendMessageEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName(){
        return this.methodname;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Provides the value associated with the SendMessage event, which is the chat message.
     *
     * @return The Message object that represents the chat message within the game.
     * @see VCEvent#getValue()
     */
    public Object getValue(){
        return this.message;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the SendMessage event, which is "SendMessage".
     *
     * @return A String representing the type of the event, which is "SendMessage".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "SendMessage";
    }

}
