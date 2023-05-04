package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * This event is used to notify the client that someone has got new points.
 * @author Paolo Gennaro
 */
public class ModifiedPointsEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedPointsEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    public ModifiedPointsEvent(LightMatch match){
        this.methodName = "onModifiedPointsEvent";
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
        return "ModifiedPointsEvent";
    }
}
