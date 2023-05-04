package Server.Network;


import Server.Controller.Controller;
import Server.Model.Match;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
    }

    @Override
    public void run(){
        while(true){
            Socket socket;
            VirtualSocketView clientVV;
            try {
                socket = this.serverSocket.accept();
                //clients.add(client);

                if(server.waitingMatch()){
                    clientVV = new VirtualSocketView(socket,false);
                    new Thread(clientVV).start();
                    Match m = server.getWaitingMatch();
                    m.addMVEventListener(clientVV);
                    Controller c = server.getMatchsController(m);
                    clientVV.addVCEventListener(c);
                    c.addSelectViewEventListener(clientVV);
                    server.subscribeNewViewToExistingMatch(m, clientVV);
                }else{
                    clientVV = new VirtualSocketView(socket,true);
                    new Thread(clientVV).start();
                    Match m = new Match();
                    Controller c = new Controller(m);
                    m.addMVEventListener(clientVV);
                    clientVV.addVCEventListener(c);
                    c.addSelectViewEventListener(clientVV);
                    server.subscribeNewMatch(m,c, clientVV);
                }
            } catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }
}
