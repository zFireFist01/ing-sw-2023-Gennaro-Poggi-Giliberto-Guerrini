package Server.Events.MVEvents;

import Server.Model.Chat.Message;
import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;


/**
 * This event is used to notify the client that the chat has been modified.
 * @author Paolo Gennaro
 */
public class ModifiedChatEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedChatEvent";
    @Expose
    private final String methodName;
    @Expose
    private final Message message;

    public ModifiedChatEvent(Message message){
        this.methodName = "onModifiedChatEvent";
        this.message = message;
    }

    public LightMatch getMatch() {
        return null;
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
