package Server.Network;
import java.io.OutputStream;
import java.net.*;
import java.util.Enumeration;

import Client.ConnectionType;
import Server.Controller.Controller;
import Server.Events.SelectViewEvents.GameView;
import Server.Events.SelectViewEvents.LoginView;
import Server.Model.Match;
import Utils.ConnectionInfo;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is used to wait for new connections and create a new VirtualSocketView for each one.
 * @author Marta Giliberto
 */
public class SocketWaiter implements Runnable{

    private final Server server;
    private final ServerSocket serverSocket;
    private ArrayList<Socket> clients;

    public SocketWaiter(Server server, int port) throws IOException {
        this.server = server;
        this.serverSocket=new ServerSocket(1098);
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                            System.out.println("Indirizzo IP del server: " + inetAddress.getHostAddress());
                            break;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("Impossibile ottenere l'indirizzo IP: " + e.getMessage());
        }
    }

    /**
     * This method is used to wait for new connection requests (A) and create a new VirtualSocketView for each one.
     * It also handles the reconnection of a client (B).
     *
     * (A):
     *  One of the following cases may happen:
     *   (1): there is a match waiting for players and the waiting queue is not empty
     *   (2): there is a match waiting for players and the waiting queue is empty
     *   (3): there is a match waiting for initialization
     *   (4): there is no match waiting for players, nor waiting for initialization
     *  How do we deal with them:
     *   (1): We instantiate the virtual view for the client and we enqueue it to the waiting queue, sending a proper view
     *        to the client
     *   (2): We instantiate the virtual view for the client and if it may overload the last match, we enqueue it to the
     *        waiting queue, sending a proper view to the client; otherwise we add it to the last match.
     *   (3): We instantiate the virtual view for the client and we enqueue it to the waiting queue, sending a proper view
     *        to the client
     *   (4): We instantiate the virtual view for the client, a new match, a new controller and we link them through
     *        their listener-listenable relationship.
     *
     * (B):
     *  We retrieve the already existing virtual view corresponding to the client using its connection info object.
     */
    @Override
    public void run(){
        //TODO: Deal with the connection and reconnection using a thread pool because the function has become a bit too long
        while(true){
            Socket socket;
            //VirtualSocketView clientVV;
            VirtualView clientVV;
            try {
                socket = this.serverSocket.accept();
                Scanner localIn = new Scanner(socket.getInputStream());
                OutputStream localOut = socket.getOutputStream();
                String action = localIn.nextLine();
                if(action.equals("Connecting")){
                    System.out.println("HELLOOOO\n");
                    //clients.add(client);
                    ConnectionInfo connectionInfo =
                            new Gson().fromJson(localIn.nextLine(), ConnectionInfo.class);
                    if(server.waitingMatch()){
                        if(!server.isWaitingQueueEmpty()){
                            /*There is someone that was waiting to log in as a consequence to someone being too slow logging in: we must
                            enqueue this client to it*/
                            clientVV = new VirtualSocketView(socket,false);
                            clientVV.setConnectionInfo(new ConnectionInfo(connectionInfo.getIp(),ConnectionType.SOCKET));
                            server.subscribeNewWaitingClient(clientVV);
                            (clientVV).onSelectViewEvent(
                                    new LoginView(false, "Waiting for a player to login in order to insert you " +
                                            "in the right match, please wait... "));
                        }else{
                            clientVV = new VirtualSocketView(socket,false);
                            (clientVV).setConnectionInfo(connectionInfo);
                            /*It is possible that this client is trying to connect to a match where an already connected
                            client hasn't logged in yet*/
                            if(server.mayOverloadLastMatch(clientVV)){
                                /*This is the case of a client being waiting for an already connected client to login,
                                since he has a "spot" reserved in the last match. We must enqueue this client to the
                                waiting queue, to make it wait for the other client to login (or quit).
                                */
                                server.subscribeNewWaitingClient(clientVV);
                                clientVV.onSelectViewEvent(new LoginView(false, "Waiting for a player to login in order to insert you " +
                                        "in the right match, please wait... "));
                            }else{
                                new Thread(clientVV).start();
                                Match m = server.getWaitingMatch();
                                m.addMVEventListener(clientVV);
                                Controller c = server.getMatchsController(m);
                                clientVV.addVCEventListener(c);
                                c.addSelectViewEventListener(clientVV);
                                server.subscribeNewViewToExistingMatch(m, clientVV);
                            }
                        }
                    }else{
                        /*No match waiting for players means: (1) there is a match waiting for initialization,
                        but already instantiated or (2) there is no match instantiated*/
                        if(server.matchWaitingForInit()){
                            //(1)
                            //This means that the current connection request must wait for the match opener to decide the number of players
                            clientVV = new VirtualSocketView(socket, false);
                            clientVV.setConnectionInfo(connectionInfo);
                            server.subscribeNewWaitingClient(clientVV);
                            (clientVV).onSelectViewEvent(
                                    new LoginView(false, "Waiting for the match opener to decide the number of " +
                                            "players, please wait...")
                            );
                        }else{
                            //(2)
                            //We need to create a new match
                            clientVV = new VirtualSocketView(socket,true);
                            (clientVV).setConnectionInfo(connectionInfo);
                            new Thread(clientVV).start();
                            Match m = new Match();
                            Controller c = new Controller(m, server);
                            m.addMVEventListener(clientVV);
                            clientVV.addVCEventListener(c);
                            c.addSelectViewEventListener(clientVV);
                            server.subscribeNewMatch(m,c, clientVV);
                        }
                    }
                    server.updateConnectionStatus(clientVV.getConnectionInfo(), true);
                }else{
                    //The client is trying to reconnect
                    ConnectionInfo connectionInfo =
                            new Gson().fromJson(localIn.nextLine(), ConnectionInfo.class);

                    boolean found = false;
                    for(Match m : server.getmatchesControllers().keySet()){
                        for(Integer i: server.getmatchesControllers().get(m).getPlayerViews().keySet()){
                            if(server.getmatchesControllers().get(m).getPlayerViews()
                                    .get(i).getConnectionInfo().getSignature().equals(connectionInfo.getSignature())){
                                //We found the player that is trying to reconnect
                                //We have to use the already existing VirtualView
                                clientVV = server.getmatchesControllers().get(m).getPlayerViews().get(i);
                                clientVV.setPongReceived();
                                if(clientVV instanceof VirtualSocketView){
                                    System.err.println("BLAH BLAH BLAH");
                                    ((VirtualSocketView)clientVV).setSocket(socket, localIn);
                                    //clientVV.setPongReceived(); //Dovrebbe essere inutile...
                                    clientVV.onSelectViewEvent(new GameView("Wait for your turn"));
                                    clientVV.setConnected(true);
                                }else{
                                    throw new UnsupportedOperationException("The client didn't choose Socket as a re-connection type, " +
                                            "while the first time he connected he did");
                                }
                                //server.updateConnectionStatus(connectionInfo, true);

                                server.updateConnectionStatus(connectionInfo, true);

                                server.getmatchesControllers().get(m).playerConnected(clientVV);
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
                        localOut.write(("The match in which you were playing has been deleted. " +
                                "Please restart the game and login as a new player.\n").getBytes());
                        localOut.flush();
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }
}
