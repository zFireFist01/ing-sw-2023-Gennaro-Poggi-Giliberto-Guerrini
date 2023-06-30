package Server.Listeners;
import Server.Events.VCEvents.VCEvent;
import Server.Network.VirtualView;

import java.lang.reflect.InvocationTargetException;

/**
 * The {@code VCEventListener} interface represents an event listener for VCEvents.
 * Objects implementing this interface can receive and handle VCEvents either in the context of a {@code VirtualView}
 * or standalone.
 */
public interface VCEventListener {
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
