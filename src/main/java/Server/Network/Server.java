package Server.Network;

import Server.Controller.Controller;
import Server.Model.Match;
import Server.Model.MatchStatus.WaitingForPlayers;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.Map;


public class Server {
    SocketWaiter socketWaiter;
    RMIWaiter rmiWaiter;

    Registry rmiRegistry;

    List<Match> matches;
    Map<Match, Controller> macthesControllers;
    Map<Match, VirtualView> matchesViews;


    public Server() {
        boolean done = false;
        this.matches = new ArrayList<>();
        this.macthesControllers = new HashMap<>();
        this.socketWaiter = new SocketWaiter();
        while(!done){
            try{
                this.rmiWaiter = new RMIWaiter(this);
                done = true;
            }catch (RemoteException e){
                System.err.println(e.getStackTrace());
            }
        }

        done = false;
        while(!done){
            try{
                this.rmiRegistry = LocateRegistry.getRegistry();
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
    }

    protected boolean waitingMatch(){
        return matches.get(matches.size()-1).getMatchStatus() instanceof WaitingForPlayers;
    }

    protected Match getWaitingMatch(){
        return matches.get(matches.size()-1);
    }

    protected Controller getMatchsController(Match m){
        return macthesControllers.get(m);
    }

    protected void subscribeNewMatch(Match m, Controller c, VirtualView vv){
        matches.add(m);
        macthesControllers.put(m,c);
        matchesViews.put(m, vv);
    }

    protected void subscribeNewViewToExistingMatch(Match m, VirtualView vv){
        matchesViews.put(m,vv);
    }
}
