package Server.Events.VCEvents;

import Server.Events.Event;

public abstract class VCEvent extends Event {

    private String methodName;

    public String getMethodName() {
        return this.methodName;
    }

    public Object getValue() {
        return null;
    }

    public abstract String getType();

    public String getPrimaryType() {
        return "VCEvent";
    }
}
