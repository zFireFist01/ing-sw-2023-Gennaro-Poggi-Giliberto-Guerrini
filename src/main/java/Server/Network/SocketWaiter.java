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
                        clientVV = new VirtualSocketView(socket,false);
                        (clientVV).setConnectionInfo(connectionInfo);
                        new Thread(clientVV).start();
                        Match m = server.getWaitingMatch();
                        m.addMVEventListener(clientVV);
                        Controller c = server.getMatchsController(m);
                        clientVV.addVCEventListener(c);
                        c.addSelectViewEventListener(clientVV);
                        server.subscribeNewViewToExistingMatch(m, clientVV);
                    }else{
                        if(server.matchWaitingForInit()){
                            //This means that the current connection request must wait for the match opener to decide the number of players
                            clientVV = new VirtualSocketView(socket, false);
                            clientVV.setConnectionInfo(connectionInfo);
                            server.subscribeNewWaitingClient(clientVV);
                            (clientVV).onSelectViewEvent(
                                    new LoginView(false, "Waiting for the match opener to decide the number of " +
                                            "players, please wait...")
                            );
                        }else{
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
                    for(Match m : server.getMacthesControllers().keySet()){
                        for(Integer i: server.getMacthesControllers().get(m).getPlayerViews().keySet()){
                            if(server.getMacthesControllers().get(m).getPlayerViews()
                                    .get(i).getConnectionInfo().getSignature().equals(connectionInfo.getSignature())){
                                //We found the player that is trying to reconnect
                                //We have to use the already existing VirtualView
                                clientVV = server.getMacthesControllers().get(m).getPlayerViews().get(i);
                                clientVV.setPongReceived();
                                if(clientVV instanceof VirtualSocketView){
                                    System.err.println("BLAH BLAH BLAH");
                                    ((VirtualSocketView)clientVV).setSocket(socket, localIn);
                                    //clientVV.setPongReceived(); //Dovrebbe essere inutile...
                                    clientVV.onSelectViewEvent(new GameView("Wait for your turn"));
                                }else{
                                    throw new UnsupportedOperationException("The client didn't choose Socket as a re-connection type, " +
                                            "while the first time he connected he did");
                                }
                                //server.updateConnectionStatus(connectionInfo, true);

                                server.updateConnectionStatus(connectionInfo, true);

                                server.getMacthesControllers().get(m).playerConnected(clientVV);
                                System.out.println("Hello world!");
                                found = true;
                                break;
                            }
                        }
                        if(found){
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }
}
