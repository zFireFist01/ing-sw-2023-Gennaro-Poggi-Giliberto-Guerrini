package Server.Listeners;
import Server.Events.VCEvents.VCEvent;
import Server.Network.VirtualView;

import java.lang.reflect.InvocationTargetException;


public interface VCEventListener {
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
