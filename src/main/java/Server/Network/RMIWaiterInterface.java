package Server.Network;

import Client.View.RemoteNetworkHandler;
import Utils.ConnectionInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The {@code RMIWaiterInterface} is an interface for handling RMI-based client connections.
 * It defines methods for providing connections to clients.
 * This interface extends the {@code Remote} interface, which allows objects implementing
 * this interface to be used in RMI (Remote Method Invocation) calls.
 *
 * @author Patrick Poggi
 */
public interface RMIWaiterInterface extends Remote {
    public RemoteVirtualView giveConnection(RemoteNetworkHandler requestingClient) throws RemoteException;

    public RemoteVirtualView reGiveConnection(RemoteNetworkHandler requestingClient,
                                             ConnectionInfo connectionInfo) throws RemoteException;
}
