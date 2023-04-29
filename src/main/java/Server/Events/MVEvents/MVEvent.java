package Server.Events.MVEvents;

import Server.Events.Event;
import Server.Model.LightMatch;

/**
 * This abstract class is used to notify the client that something has been modified in the model.
 * @author Paolo Gennaro
 */
public abstract class MVEvent extends Event {
    private String methodName;
    private LightMatch match;

    public String getMethodName() {
        return this.methodName;
    }


    /**
     * Getter for the type of event
     * @return the type of event
     */
    public abstract String getType();

    public Object getValue() {
        return null;
    }

    public LightMatch getMatch() {
        return this.match;
    }

    public String getPrimaryType() {
        return "MVEvent";
    }
}
