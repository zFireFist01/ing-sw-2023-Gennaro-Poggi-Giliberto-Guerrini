package Server.Events.VCEvents;

public abstract class VCEvent {

    private String methodName;

    public String getMethodName() {
        return this.methodName;
    }

    public Object getValue() {
        return null;
    }
}
