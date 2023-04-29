package Server.Listeners;

import Server.Events.MVEvents.MVEvent;


import java.rmi.RemoteException;

public interface MVEventListener {
    public void onMVEvent(MVEvent event);

}
