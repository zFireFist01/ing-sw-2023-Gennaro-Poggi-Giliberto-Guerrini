package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * This event is used to notify the client that the match just started
 * @author Valentino Guerrini
 */
public class MatchStartedEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "MatchStartedEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    public MatchStartedEvent(LightMatch match){
        this.methodName = "onMatchStartedEvent";
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
        return "MatchStartedEvent";
    }


}
