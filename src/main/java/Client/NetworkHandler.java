package Client;

import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;

import java.rmi.RemoteException;


public interface NetworkHandler extends VCEventListener, Runnable{
    public void sendMVEvent(String json) throws RemoteException;
    public void sendSelectViewEvent(String json) throws RemoteException;

    public void parseInput(String command);

}
