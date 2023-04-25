package Server.Listeners;
import Server.Events.VCEvents.VCEvent;

import java.lang.reflect.InvocationTargetException;

public interface VCEventListener {

    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException ;
}
