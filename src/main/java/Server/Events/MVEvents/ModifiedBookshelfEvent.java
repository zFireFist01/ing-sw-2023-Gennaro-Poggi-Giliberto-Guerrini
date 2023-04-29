package Server.Events.MVEvents;

import Server.Model.LightMatch;

/**
 * This event is used to notify the client that a bookshelf has been modified.
 * @author Paolo Gennaro
 */
public class ModifiedBookshelfEvent extends MVEvent{
    private final String methodName;
    private final LightMatch match;

    public ModifiedBookshelfEvent(LightMatch match){
        this.methodName = "onModifiedBookshelfEvent";
        this.match = match;
    }

    public String getMethodName(){
        return this.methodName;
    }

    public Object getValue(){
        return null;
    }

    @Override
    public String getType() {
        return "ModifiedBookshelfEvent";
    }
}
