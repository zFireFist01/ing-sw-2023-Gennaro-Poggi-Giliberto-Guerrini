package Server.Listeners;

import Server.Events.MVEvents.MVEvent;


import java.rmi.RemoteException;

/**
 * The {@code MVEventListener} interface represents an event listener for MVEventListener.
 */
public interface MVEventListener {
    public void onMVEvent(MVEvent event);

}
