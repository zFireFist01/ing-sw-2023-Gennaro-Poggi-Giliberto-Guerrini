package Server.Events.MVEvents;

import Server.Model.LightMatch;

public class ModifiedMatchEndedEvent extends MVEvent{
    private final String methodName;
    private final LightMatch match;

    public ModifiedMatchEndedEvent(LightMatch match){
        this.methodName = "onModifiedMatchEndedEvent";
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
        return "ModifiedMatchEndedEvent";
    }
}
