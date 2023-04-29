package Server.Listeners;

import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;

import java.rmi.RemoteException;

public interface MVEventListener {
    public void onMVEvent(MVEvent event);

}
