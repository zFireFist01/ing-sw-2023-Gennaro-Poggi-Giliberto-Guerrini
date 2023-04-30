package Server.Events.MVEvents;

import Server.Model.LightMatch;

/**
 * This event is used to notify the client that the match just started
 * @author Valentino Guerrini
 */
public class MatchStartedEvent extends MVEvent{

    private final String methodName;
    private final LightMatch match;

    public MatchStartedEvent(LightMatch match){
        this.methodName = "onMatchStartedEvent";
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
        return "MatchStartedEvent";
    }


}
