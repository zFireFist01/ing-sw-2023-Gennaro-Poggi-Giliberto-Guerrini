package Server.Network;

import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Listeners.VCEventListener;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteVirtualView extends Remote/*, VirtualView*/ {

    void receiveVCEvent(String json) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, RemoteException;
    void pong() throws RemoteException;

}
