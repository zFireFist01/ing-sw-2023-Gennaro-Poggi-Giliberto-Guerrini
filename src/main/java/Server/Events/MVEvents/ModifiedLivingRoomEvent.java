package Server.Events.MVEvents;

import Server.Model.LightMatch;

/**
 * This event is used to notify the client that the living room has been modified.
 * @author Paolo Gennaro
 */
public class ModifiedLivingRoomEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    private final String secondaryType = "ModifiedLivingRoomEvent";
    private final String methodName;
    private final LightMatch match;

    public ModifiedLivingRoomEvent(LightMatch match){
        this.methodName = "onModifiedLivingRoomEvent";
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
        return "ModifiedLivingRoomEvent";
    }
}
