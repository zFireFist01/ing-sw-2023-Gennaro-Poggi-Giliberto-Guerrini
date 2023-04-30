package Server.Events.MVEvents;

import Server.Model.Chat.Message;
import Server.Model.LightMatch;


/**
 * This event is used to notify the client that the chat has been modified.
 * @author Paolo Gennaro
 */
public class ModifiedChatEvent extends MVEvent{
    private final String methodName;
    private final Message message;

    public ModifiedChatEvent(Message message){
        this.methodName = "onModifiedChatEvent";
        this.message = message;
    }

    public String getMethodName(){
        return this.methodName;
    }

    public Object getValue(){
        return message;
    }

    @Override
    public String getType() {
        return "ModifiedChatEvent";
    }
}
