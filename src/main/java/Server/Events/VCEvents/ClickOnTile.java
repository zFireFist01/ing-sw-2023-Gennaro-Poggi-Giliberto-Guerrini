package Server.Events.VCEvents;

public class ClickOnTile extends VCEvent{
    private final String secondaryType = "ClickOnTile";

    private final int[] coordinates;
    private final String methodName;

    public ClickOnTile(int[] coordinates){
        this.coordinates = coordinates;
        this.methodName = "onClickOnTileEvent";
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
