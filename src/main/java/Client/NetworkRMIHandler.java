package Client;

import Client.View.CLI.ANSIParameters;
import Client.View.RemoteNetworkHandler;
import Client.View.View;
import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.*;
import Utils.ConnectionInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AccessException;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;

public class NetworkRMIHandler extends UnicastRemoteObject implements RemoteNetworkHandler, NetworkHandler {

    String host;
    int port;
    View view;
    RemoteVirtualView virtualRMIView;
    private boolean connected;
    Timer timer;

    public NetworkRMIHandler(String host, int port, View view) throws RemoteException {
        super();
        this.host = host;
        this.port = port;
        this.view = view;
        connected = false;
        timer = new Timer();
    }

    @Override
    public void run(){
        Registry registry = null;
        RMIWaiterInterface rmiWaiter = null;
        try {
            registry = LocateRegistry.getRegistry(host, port);
        } catch (RemoteException e) {
            System.err.println("Error while getting registry");
            throw new RuntimeException(e);
        }catch (Exception e){
            System.out.println("Error while getting the RMI registry, please wait and try again.");
            return;
        }

        if(registry == null){
            System.out.println("Error while getting the RMI registry, please wait and try again.");
            return;
        }

        int tries = 0;
        boolean flag = false;
        while(!flag && tries <2){
            try {
                tries++;
                rmiWaiter = (RMIWaiterInterface)registry.lookup("RMIWaiter");
                System.out.println("RMIWaiter found");
                flag = true;
            } catch (RemoteException e) {
                System.out.println("[Try #"+(tries)+"]: Error while getting the RMI waiter, please wait and try again.");
            } catch (NotBoundException e) {
                System.out.println("Failed to bind with RMIWaiter (RMIWaiter not bound)");
            }
        }

        if(rmiWaiter == null){
            System.out.print("Error while connecting to the server, please wait and try again.\n> ");
            return;
        }

        try {
            if(view.isReconnecting()){
                try{
                    virtualRMIView = rmiWaiter.reGiveConnection(this, view.getConnectionInfo());
                }catch(UnsupportedOperationException e){
                    System.out.println(e.getMessage());
                    System.exit(0);
                }
            }else{
                virtualRMIView = rmiWaiter.giveConnection(this);
            }
        } catch (RemoteException e) {
            System.err.print("Error while getting connection through RMI, please wait and try again.\n> ");
            return;
        }
        connected = true;

    }


    @Override
    public void receiveMVEvent(String json) throws RemoteException{
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        MVEvent event = gson.fromJson(json, MVEvent.class);
        String type = event.getPrimaryType();
        switch (type) {
            case "MVEvent":
                view.onMVEvent((MVEvent) event);
                break;
            case "VCEvent":
                throw new IllegalArgumentException("VCEvent should not be sent to the client");
            default:
                throw new IllegalArgumentException("Event type not recognized");
        }
    }


    @Override
    public void receiveSelectViewEvent(String json) throws RemoteException{
        /*Gson gson = new Gson();*/
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        SelectViewEvent event = gson.fromJson(json,SelectViewEvent.class);
        String type = event.getPrimaryType();
        switch (type) {
            case "MVEvent":
                throw new IllegalArgumentException("MVEvent should not be sent to the client");
            case "VCEvent":
                throw new IllegalArgumentException("VCEvent should not be sent to the client");
            case "SelectViewEvent":
                view.onSelectViewEvent((SelectViewEvent) event);
                break;
            default:
                throw new IllegalArgumentException("Event type not recognized");
        }
    }

    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        json+="\n";
        try {
            virtualRMIView.receiveVCEvent(json);
        } catch(ConnectException e){
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            //view.setConnectionToReset();
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
            return;
        } catch (RemoteException e) {
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            //view.setConnectionToReset();
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
            return;
        }
    }

    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessError("This method should not be called");
    }

    @Override
    public void ping(){
        try {
            if(virtualRMIView!=null) {
                virtualRMIView.pong();
            }
        } catch (RemoteException e) {
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
        }
    }
    @Override
    public void pong() throws RemoteException{
        return;
    }

    @Override
    public ConnectionInfo getConnectionInfo() throws RemoteException{
        return this.view.getConnectionInfo();
    }

}
