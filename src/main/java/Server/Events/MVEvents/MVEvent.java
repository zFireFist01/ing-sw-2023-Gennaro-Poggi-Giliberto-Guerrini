package Server.Events.MVEvents;

import Server.Model.LightMatch;

public abstract class MVEvent {
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
}
