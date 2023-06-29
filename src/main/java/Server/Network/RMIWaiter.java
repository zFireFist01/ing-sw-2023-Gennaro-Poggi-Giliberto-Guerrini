package Server.Network;

import Client.ConnectionType;
import Client.View.RemoteNetworkHandler;
import Server.Controller.Controller;
import Server.Events.SelectViewEvents.GameView;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Model.Match;
import Server.Model.Player.Player;
import Utils.ConnectionInfo;
import com.google.gson.Gson;

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
     * This method is used to give a connection to a client that is waiting for one.
     * One of the following cases may happen:
     *  (1): there is a match waiting for players and the waiting queue is not empty
     *  (2): there is a match waiting for players and the waiting queue is empty
     *  (3): there is a match waiting for initialization
     *  (4): there is no match waiting for players, nor waiting for initialization
     * How do we deal with them:
     *  (1): We instantiate the virtual view for the client and we enqueue it to the waiting queue, sending a proper view
     *     to the client
     *  (2): We instantiate the virtual view for the client and if it may overload the last match, we enqueue it to the
     *      waiting queue, sending a proper view to the client; otherwise we add it to the last match.
     *  (3): We instantiate the virtual view for the client and we enqueue it to the waiting queue, sending a proper view
     *      to the client
     *  (4): We instantiate the virtual view for the client, a new match, a new controller and we link them through
     *      their listener-listenable relationship.
     * @param requestingClient the NetworkHandler interface, which we know to be a NetworkRMIHandler, that we will
     *                         use to communicate with the client
     * @return the VirtualView that we have instantiated for the client
     * @throws RemoteException if the connection with the client goes wrong, see {@link RemoteException}
     */
    public synchronized RemoteVirtualView giveConnection(RemoteNetworkHandler requestingClient) throws RemoteException{
        //VirtualView clientsVV = new VirtualRMIView((NetworkRMIHandler) requestingClient);
        VirtualRMIView clientsVV = null;
        if(server.waitingMatch()){
            //We don't need to istantiate a new match
            if(!server.isWaitingQueueEmpty()){
                /*There is someone that was waiting to log in as a consequence to someone being too slow loggin in: we must
                enqueue this client to it*/
                clientsVV = new VirtualRMIView(requestingClient,false);
                clientsVV.setConnectionInfo(new ConnectionInfo(requestingClient.getConnectionInfo().getIp(),
                        ConnectionType.RMI));
                server.subscribeNewWaitingClient(clientsVV);
                (clientsVV).onSelectViewEvent(
                        new LoginView(false, "Waiting for a player to login in order to insert you " +
                                "in the right match, please wait... "));
            }else{
                clientsVV = new VirtualRMIView(requestingClient, false);
                clientsVV.setConnectionInfo(new ConnectionInfo(requestingClient.getConnectionInfo().getIp(),
                        ConnectionType.RMI));
                /*It is possible that this client is trying to connect to a match where an already connected
                client hasn't logged in yet*/
                if(server.mayOverloadLastMatch(clientsVV)){
                    /*This is the case of a client being waiting for an already connected client to login, since he has
                    a "spot" reserved in the last match. We must enqueue this client to the waiting queue, to make it wait
                    for the other client to login (or quit).
                    */
                    server.subscribeNewWaitingClient(clientsVV);
                    clientsVV.onSelectViewEvent(new LoginView(false, "Waiting for a player to login in order to insert you " +
                            "in the right match, please wait... "));
                }else{
                    Match m = server.getWaitingMatch();
                    clientsVV.run();
                    m.addMVEventListener(clientsVV);
                    Controller c = server.getMatchsController(m);
                    clientsVV.addVCEventListener(c);
                    c.addSelectViewEventListener(clientsVV);
                    server.subscribeNewViewToExistingMatch(m, clientsVV);
                    server.updateConnectionStatus(clientsVV.getConnectionInfo(), true);
                }
            }
        }else{
            /*No match waiting for players means: (1) there is a match waiting for initialization, but already instantiated
            or (2) there is no match instantiated*/
            if(server.matchWaitingForInit()){
                //(1)
                //This means that the current connection request must wait for the match opener to decide the number of players
                clientsVV = new VirtualRMIView(requestingClient, false);
                clientsVV.setConnectionInfo(new ConnectionInfo(requestingClient.getConnectionInfo().getIp(),
                        ConnectionType.RMI));
                server.subscribeNewWaitingClient(clientsVV);
                (clientsVV).onSelectViewEvent(
                        new LoginView(false, "Waiting for the match opener to decide the number of " +
                                "players, please wait...")
                );
            }else{
                //(2)
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
        }
        return (RemoteVirtualView)clientsVV;
    }

    /**
     * This method is used to give a connection to a client that is reconnecting, by retrieving its already existing
     * virtual view through its connection info object.
     * @param requestingClient the client waiting for the connection
     * @param connectionInfo the connectionInfo object of the client that is trying to reconnect
     * @return the VirtualView that we have instantiated for the client. Note that this VirtualView has been instantiated 
     * at the first connection of the client, now the only thing we do is retrieving it.
     * @throws RemoteException
     */
    public synchronized RemoteVirtualView reGiveConnection(RemoteNetworkHandler requestingClient,
                                                           ConnectionInfo connectionInfo) throws RemoteException{
        VirtualView clientsVV = null;
        boolean found = false;
        //TODO: Maybe the problem is in the containsKey method
        //if(server.wasConnectedAndHasDisconnected(connectionInfo)){
            //The client had lost connection and is now legitimately trying to reconnect
            for(Match m : server.getmatchesControllers().keySet()){
                for(Integer i: server.getmatchesControllers().get(m).getPlayerViews().keySet()){
                    if(server.getmatchesControllers().get(m).getPlayerViews()
                            .get(i).getConnectionInfo().getSignature().equals(connectionInfo.getSignature())){
                        //We found the player that is trying to reconnect
                        //We have to return the already existing VirtualView
                        clientsVV = server.getmatchesControllers().get(m).getPlayerViews().get(i);
                        if(clientsVV instanceof VirtualRMIView){
                            ((VirtualRMIView)clientsVV).setClient(requestingClient);
                            clientsVV.onSelectViewEvent(new GameView("Wait for your turn"));
                            clientsVV.setConnected(true);
                        }else{
                            throw new UnsupportedOperationException("The client didn't choose RMI as a re-connection type, " +
                                    "while the first time he connected he did");
                        }

                        clientsVV.setPongReceived();
                        server.updateConnectionStatus(connectionInfo, true);
                        server.getmatchesControllers().get(m).playerConnected(clientsVV);
                        System.out.println("Hello world!");
                        found = true;
                        break;
                    }
                }
                if(found){
                    break;
                }
            }
            if(!found){
                throw new UnsupportedOperationException("The match in which the client was playing " +
                        "has been deleted.\nPlease restart the game and login as a new player");
            }
        /*}else{
            //The client has never been connected or he has and has already re-established the connection
            throw new UnsupportedOperationException("The client has never been connected " +
                    "(or he has and has already re-established the connection)");
        }*/
        return (RemoteVirtualView)clientsVV;
    }
}
