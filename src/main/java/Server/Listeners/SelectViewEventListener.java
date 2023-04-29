package Server.Listeners;

import Server.Events.SelectViewEvents.SelectViewEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SelectViewEventListener {
    public void onSelectViewEvent(SelectViewEvent event);
}
