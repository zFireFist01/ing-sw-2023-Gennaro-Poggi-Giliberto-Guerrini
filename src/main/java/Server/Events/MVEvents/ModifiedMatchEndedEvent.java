package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * This event is used to notify the client that the match ended tile has been assigned to someone.
 * @author Paolo Gennaro
 */
public class ModifiedMatchEndedEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedMatchEndedEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    public ModifiedMatchEndedEvent(LightMatch match){
        this.methodName = "onModifiedMatchEndedEvent";
        this.match = match;
    }

    public LightMatch getMatch() {
        return this.match;
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
