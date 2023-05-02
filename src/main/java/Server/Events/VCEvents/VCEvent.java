package Server.Events.VCEvents;

import Server.Events.Event;

public abstract class VCEvent extends Event {
    private final String primaryType = "VCEvent";



    public abstract String getMethodName();

    public Object getValue() {
        return null;
    }

    public abstract String getType();

    public String getPrimaryType() {
        return "VCEvent";
    }
}
