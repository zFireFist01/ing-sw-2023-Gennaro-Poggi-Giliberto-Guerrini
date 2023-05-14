package Server.Network;

import Client.View.RemoteNetworkHandler;
import Utils.ConnectionInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIWaiterInterface extends Remote {
    public RemoteVirtualView giveConnection(RemoteNetworkHandler requestingClient) throws RemoteException;

    public RemoteVirtualView reGiveConnection(RemoteNetworkHandler requestingClient,
                                             ConnectionInfo connectionInfo) throws RemoteException;
}
