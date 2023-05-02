package Server.Network;

import Client.NetworkHandler;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIWaiterInterface extends Remote {
    public VirtualView giveConnection(NetworkHandler requestingClient) throws RemoteException;
}
