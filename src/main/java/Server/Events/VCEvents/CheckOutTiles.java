package Server.Events.VCEvents;

public class CheckOutTiles extends VCEvent{
    private final String secondaryType = "CheckOutTiles";
    private final String methodName;

    public CheckOutTiles(){
        this.methodName = "onCheckOutTiles";
    }

    public String getMethodName(){
        return this.methodName;
    }

    public Object getValue(){
        return null;
    }

    public String getType(){
        return "CheckOutTiles";
    }
}
