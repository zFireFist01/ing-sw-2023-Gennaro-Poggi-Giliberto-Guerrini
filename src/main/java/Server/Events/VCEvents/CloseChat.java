package Server.Events.VCEvents;

public class CloseChat extends VCEvent{
    private final String methodName;

    public CloseChat() {
        this.methodName = "onCloseChat";
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Object getValue() {
        return null;
    }
}
