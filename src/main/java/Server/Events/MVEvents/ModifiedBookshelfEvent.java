package Server.Events.MVEvents;

import Server.Model.LightMatch;
import com.google.gson.annotations.Expose;

/**
 * This event is used to notify the client that a bookshelf has been modified.
 * @author Paolo Gennaro
 */
public class ModifiedBookshelfEvent extends MVEvent{
    //private final String primaryType = "MVEvent";
    @Expose
    private final String secondaryType = "ModifiedBookshelfEvent";
    @Expose
    private final String methodName;
    @Expose
    private final LightMatch match;

    public ModifiedBookshelfEvent(LightMatch match){
        this.methodName = "onModifiedBookshelfEvent";
        this.match = match;
    }

    public LightMatch getMatch() {
        return this.match;
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
