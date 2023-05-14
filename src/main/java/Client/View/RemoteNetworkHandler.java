package Client.View;

import Client.NetworkHandler;
import Server.Events.VCEvents.VCEvent;
import Server.Network.RemoteVirtualView;
import Server.Network.VirtualView;
import Utils.ConnectionInfo;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteNetworkHandler extends /*NetworkHandler,*/ Remote {
    void receiveMVEvent(String json) throws RemoteException;

    void receiveSelectViewEvent(String json) throws RemoteException;

    void pong() throws RemoteException;

    ConnectionInfo getConnectionInfo() throws RemoteException;
}
