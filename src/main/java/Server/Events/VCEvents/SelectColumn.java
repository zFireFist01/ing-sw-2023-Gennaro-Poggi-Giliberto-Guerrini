package Server.Events.VCEvents;

public class SelectColumn extends VCEvent{
    private final String secondaryType = "SelectColumn";
    private final int column;
    private final String methodName;

    public SelectColumn(int column){
        this.column = column;
        this.methodName = "onSelectColumn";

    }
    public String getMethodName(){
        return this.methodName;
    }
    public Object getValue(){
        return this.column;
    }

    public String getType(){
        return "SelectColumn";
    }


}
