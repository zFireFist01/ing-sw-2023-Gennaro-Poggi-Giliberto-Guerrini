package Server.Events.MVEvents;

import Server.Model.LightMatch;

public class ModifiedLivingRoomEvent extends MVEvent{
    private final String methodName;
    private final LightMatch match;

    public ModifiedLivingRoomEvent(LightMatch match){
        this.methodName = "onModifiedLivingRoomEvent";
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
        return "ModifiedLivingRoomEvent";
    }
}
