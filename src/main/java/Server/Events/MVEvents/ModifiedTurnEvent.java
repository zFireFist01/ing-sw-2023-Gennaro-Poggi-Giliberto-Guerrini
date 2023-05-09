package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

public class ModifiedTurnEvent extends MVEvent{
    @Expose
    private final String secondaryType = "ModifiedTurnEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    public ModifiedTurnEvent(LightMatch match){
        this.methodName = "onModifiedTurnEvent";
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
        return "ModifiedTurnEvent";
    }
}
