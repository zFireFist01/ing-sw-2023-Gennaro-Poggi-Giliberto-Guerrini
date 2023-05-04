package Server.Network;

import Client.View.RemoteNetworkHandler;
import Server.Controller.Controller;
import Server.Model.Match;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is used to give a connection to a client that is waiting for one
 * using the RMI protocol
 * @see Server.Network.Server
 * @author patrickpoggi
 */
public class RMIWaiter extends UnicastRemoteObject implements RMIWaiterInterface {
    Server server;
    public RMIWaiter(Server server) throws RemoteException {
        super();
        this.server = server;
    }

    /**
     * This method is used to give a connection to a client that is waiting for one
     *
     * @param requestingClient the NetworkHandler interface, which we know to be a NetworkRMIHandler, that we will
     *                         use to communicate with the client
     * @return the VirtualView that we have istantiated for the client
     * @throws RemoteException if the connection with the client goes wrong, see {@link RemoteException}
     */
    public synchronized RemoteVirtualView giveConnection(RemoteNetworkHandler requestingClient) throws RemoteException{
        //VirtualView clientsVV = new VirtualRMIView((NetworkRMIHandler) requestingClient);
        VirtualView clientsVV = new VirtualRMIView(requestingClient);
        if(server.waitingMatch()){
            //We don't need to istantiate a new match
            Match m = server.getWaitingMatch();
            clientsVV.run();
            m.addMVEventListener(clientsVV);
            Controller c = server.getMatchsController(m);
            clientsVV.addVCEventListener(c);
            c.addSelectViewEventListener(clientsVV);
            server.subscribeNewViewToExistingMatch(m, clientsVV);
        }else{
            //We need to create a new match
            Match m = new Match();
            Controller c = new Controller(m);
            clientsVV.run();
            m.addMVEventListener(clientsVV);
            clientsVV.addVCEventListener(c);
            c.addSelectViewEventListener(clientsVV);
            server.subscribeNewMatch(m,c, clientsVV);
        }
        return (RemoteVirtualView)clientsVV;
    }
}
