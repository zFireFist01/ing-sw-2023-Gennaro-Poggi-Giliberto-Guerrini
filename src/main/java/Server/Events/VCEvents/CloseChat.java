package Server.Events.VCEvents;

public class CloseChat extends VCEvent{
    private final String secondaryType = "CloseChat";
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

    public String getType(){
        return "CloseChat";
    }
}
