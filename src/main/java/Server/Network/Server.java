package Server.Network;

import Server.Controller.Controller;
import Server.Model.Match;
import Server.Model.MatchStatus.NotRunning;
import Server.Model.MatchStatus.WaitingForPlayers;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is the main class of the Server and is used to istantiate the RMI waiter and the Socket waiter,
 * which will wait for clients to connect to the server.
 * @see Server.Network.RMIWaiter
 * @see Server.Network.SocketWaiter
 * @author patrickpoggi
 */
public class Server implements Runnable{
    SocketWaiter socketWaiter;
    RMIWaiter rmiWaiter;
    Registry rmiRegistry;
    List<Match> matches;
    Map<Match, Controller> macthesControllers;
    Map<Match, List<VirtualView>> matchesViews;


    public Server() throws IOException{
        boolean done = false;
        this.matches = new ArrayList<>();
        matchesViews = new HashMap<>();
        this.macthesControllers = new HashMap<>();
        this.socketWaiter = new SocketWaiter(this,1098);
        while(!done){
            try{
                this.rmiWaiter = new RMIWaiter(this);
                done = true;
            }catch (RemoteException e){
                System.err.println("Impossible to istantiate the RMI waiter");
                System.err.println(e.getStackTrace());
            }
        }

    }

    public static void main(String[] args) {
        try{
            Server server = new Server();
            server.run();
        }catch (IOException e){
            System.err.println("Error occurred when trying to istantiate the socket waiter");
            System.err.println(e.getStackTrace());
        }

    }

    /**
     * This method is used to start the server. It will bind the RMI waiter to the RMI registry and start the socket
     * waiter in a new thread
     */
    @Override
    public void run() {
        boolean done = false;
        new Thread(socketWaiter).start();
        done = false;
        while(!done){
            try{
                this.rmiRegistry = LocateRegistry.createRegistry(1099);
                System.setProperty("java.rmi.server.hostname","localhost");
                done = true;
            }catch (RemoteException e){
                System.err.println(e.getStackTrace());
            }
        }

        done = false;
        while(!done){
            try{
                rmiRegistry.bind("RMIWaiter", rmiWaiter);
                done = true;
            }catch (AccessException e){
                System.err.println(e.getStackTrace());
            }catch (RemoteException e){
                System.err.println(e.getStackTrace());
            }catch (AlreadyBoundException e){
                System.err.println(e.getStackTrace());
            }
        }
        System.out.println("Server started");
    }

    /**
     * This method is used from both the RMI waiter and the Socket waiter to see if there is a match which is waiting
     * for players to join.
     * @return true if and only if there is a match which is waiting for players to join. If that's the case it must be
     * the last match in the list of matches.
     */
    protected synchronized boolean waitingMatch(){
        if(matches.size() == 0)
            return false;
        return (matches.get(matches.size()-1).getMatchStatus() instanceof WaitingForPlayers) || (matches.get(matches.size()-1).getMatchStatus() instanceof NotRunning) ;
    }

    /**
     * This method is used from both the RMI waiter and the Socket waiter to get the match which is waiting for players
     * @return the match which is waiting for players.
     */
    protected synchronized Match getWaitingMatch(){
        return matches.get(matches.size()-1);
    }

    /**
     * This method is used from both the RMI waiter and the Socket waiter to get the controller of a given
     * @return the controller of the given match
     */
    protected synchronized Controller getMatchsController(Match m){
        return macthesControllers.get(m);
    }

    /**
     * This method is used from both the RMI waiter and the Socket waiter to tell the server that a new match has been
     * created and that it must be added to the list of matches.
     * @param m is the new match
     * @param c is the controller of the new match
     * @param vv is the first virtual view we have istantiated for the new match, since if we had to create a new match
     *           there must have been one client connected to the server.
     */
    protected synchronized void subscribeNewMatch(Match m, Controller c, VirtualView vv){
        matches.add(m);
        macthesControllers.put(m,c);
        matchesViews.put(m, new ArrayList<>());
        matchesViews.get(m).add(vv);
    }

    protected synchronized void subscribeNewViewToExistingMatch(Match m, VirtualView vv){
        //matchesViews.put(m,vv);
        matchesViews.get(m).add(vv);
    }

}
