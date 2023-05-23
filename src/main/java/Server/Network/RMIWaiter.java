package Server.Network;

import Client.ConnectionType;
import Client.View.RemoteNetworkHandler;
import Server.Controller.Controller;
import Server.Events.SelectViewEvents.GameView;
import Server.Model.Match;
import Server.Model.Player.Player;
import Utils.ConnectionInfo;

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
        VirtualRMIView clientsVV;
        if(server.waitingMatch()){
            clientsVV = new VirtualRMIView(requestingClient, false);
            clientsVV.setConnectionInfo(new ConnectionInfo(requestingClient.getConnectionInfo().getIp(),
                    ConnectionType.RMI));
            //We don't need to istantiate a new match
            Match m = server.getWaitingMatch();
            clientsVV.run();
            m.addMVEventListener(clientsVV);
            Controller c = server.getMatchsController(m);
            clientsVV.addVCEventListener(c);
            c.addSelectViewEventListener(clientsVV);
            server.subscribeNewViewToExistingMatch(m, clientsVV);
            server.updateConnectionStatus(clientsVV.getConnectionInfo(), true);
        }else{
            //We need to create a new match
            clientsVV = new VirtualRMIView(requestingClient, true);
            clientsVV.setConnectionInfo(new ConnectionInfo(requestingClient.getConnectionInfo().getIp(),
                    ConnectionType.RMI));
            Match m = new Match();
            Controller c = new Controller(m, server);
            clientsVV.run();
            m.addMVEventListener(clientsVV);
            clientsVV.addVCEventListener(c);
            c.addSelectViewEventListener(clientsVV);
            server.subscribeNewMatch(m,c, clientsVV);
            server.updateConnectionStatus(clientsVV.getConnectionInfo(), true);
        }
        return (RemoteVirtualView)clientsVV;
    }

    public synchronized RemoteVirtualView reGiveConnection(RemoteNetworkHandler requestingClient,
                                                           ConnectionInfo connectionInfo) throws RemoteException{
        VirtualView clientsVV = null;
        boolean found = false;
        //TODO: Maybe the problem is in the containsKey method
        if(server.wasConnectedAndHasDisconnected(connectionInfo)){
            //The client had lost connection and is now legitimately trying to reconnect
            for(Match m : server.getMacthesControllers().keySet()){
                for(Integer i: server.getMacthesControllers().get(m).getPlayerViews().keySet()){
                    if(server.getMacthesControllers().get(m).getPlayerViews()
                            .get(i).getConnectionInfo().getSignature().equals(connectionInfo.getSignature())){
                        //We found the player that is trying to reconnect
                        //We have to return the already existing VirtualView
                        clientsVV = server.getMacthesControllers().get(m).getPlayerViews().get(i);
                        if(clientsVV instanceof VirtualRMIView){
                            ((VirtualRMIView)clientsVV).setClient(requestingClient);
                            clientsVV.onSelectViewEvent(new GameView("Wait for your turn"));
                        }else{
                            throw new UnsupportedOperationException("The client didn't choose RMI as a re-connection type, " +
                                    "while the first time he connected he did");
                        }

                        clientsVV.setPongReceived();
                        //server.updateConnectionStatus(connectionInfo, true);
                        //synchronized (server){
                            server.updateConnectionStatus(connectionInfo, true);
                        //}
                        server.getMacthesControllers().get(m).playerConnected(clientsVV);
                        System.out.println("Hello world!");
                        found = true;
                        break;
                    }
                }
                if(found){
                    break;
                }
            }
        }else{
            //The client has never been connected or he has and has already re-established the connection
            throw new UnsupportedOperationException("The client has never been connected " +
                    "(or he has and has already re-established the connection)");
        }
        return (RemoteVirtualView)clientsVV;
    }
}
