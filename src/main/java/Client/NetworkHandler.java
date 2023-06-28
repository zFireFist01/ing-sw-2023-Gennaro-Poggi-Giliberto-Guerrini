package Client;

import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import Server.Network.VirtualView;
import Utils.ConnectionInfo;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * This interface is used to handle the network connection of a View with the server
 * @author Patrick Poggi
 */
public interface NetworkHandler extends VCEventListener, Runnable{
    public void receiveMVEvent(String json) throws RemoteException;
    public void receiveSelectViewEvent(String json) throws RemoteException;

    @Override
    void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    @Override
    void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException;

    @Override
    void run();


    void ping();
}
