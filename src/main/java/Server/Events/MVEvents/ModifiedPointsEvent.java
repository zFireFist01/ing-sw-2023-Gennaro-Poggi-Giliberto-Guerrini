package Server.Events.MVEvents;

import Server.Model.LightMatch;

/**
 * This event is used to notify the client that someone has got new points.
 * @author Paolo Gennaro
 */
public class ModifiedPointsEvent extends MVEvent{
    private final String methodName;
    private final LightMatch match;

    public ModifiedPointsEvent(LightMatch match){
        this.methodName = "onModifiedPointsEvent";
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
        return "ModifiedPointsEvent";
    }
}
