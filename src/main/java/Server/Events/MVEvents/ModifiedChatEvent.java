package Server.Events.MVEvents;

import Server.Model.LightMatch;

public class ModifiedChatEvent extends MVEvent{
    private final String methodName;
    private final LightMatch match;

    public ModifiedChatEvent(LightMatch match){
        this.methodName = "onModifiedChatEvent";
        this.match = match;
    }

    public String getMethodName(){
        return this.methodName;
    }

    public Object getValue(){
        return null;
    }

    @Override
    public String getType() {
        return "ModifiedChatEvent";
    }
}
