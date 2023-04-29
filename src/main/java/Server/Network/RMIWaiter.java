package Server.Network;

import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Server.Controller.Controller;
import Server.Model.Match;
import com.beust.ah.A;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class RMIWaiter extends UnicastRemoteObject implements Remote {
    Server server;
    public RMIWaiter(Server server) throws RemoteException {
        super();
        this.server = server;
    }

    public synchronized VirtualView giveConnection(NetworkHandler requestingClient) throws RemoteException{
        VirtualView clientsVV = new VirtualRMIView((NetworkRMIHandler) requestingClient);
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
            Controller c = new Controller(clientsVV, m);
            m.addMVEventListener(clientsVV);
            clientsVV.addVCEventListener(c);
            c.addSelectViewEventListener(clientsVV);
            server.subscribeNewMatch(m,c, clientsVV);
        }
        return clientsVV;
    }
}
