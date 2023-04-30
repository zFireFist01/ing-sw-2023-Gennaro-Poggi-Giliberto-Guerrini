package Client;

import Server.Listeners.VCEventListener;

import java.rmi.RemoteException;

/**
 * This interface is used to handle the network connection of a View with the server
 * @author Patrick Poggi
 */
public interface NetworkHandler extends VCEventListener, Runnable{
    public void sendMVEvent(String json) throws RemoteException;
    public void sendSelectViewEvent(String json) throws RemoteException;

}
