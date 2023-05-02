package Server.Events.MVEvents;

import Server.Model.LightMatch;

/**
 * This event is used to notify the client that the match ended tile has been assigned to someone.
 * @author Paolo Gennaro
 */
public class ModifiedMatchEndedEvent extends MVEvent{
    private final String primaryType = "MVEvent";
    private final String secondaryType = "ModifiedMatchEndedEvent";
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
