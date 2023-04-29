package Server.Events.VCEvents;

public class ClickOnTile extends VCEvent{

    private final int[] coordinates;
    private final String methodName;

    public ClickOnTile(int[] coordinates){
        this.coordinates = coordinates;
        this.methodName = "onClickOnTile";
    }

    public String getMethodName(){
        return this.methodName;
    }

    public Object getValue(){
        return this.coordinates;
    }

    public String getType(){
        return "ClickOnTile";
    }
}
