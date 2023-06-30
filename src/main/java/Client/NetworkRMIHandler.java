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
import java.util.concurrent.*;


/**
 * A class that handles network communications for the Remote Method Invocation (RMI) server.
 * It extends UnicastRemoteObject and implements RemoteNetworkHandler and NetworkHandler interfaces.
 * This handler provides a connection to the RMI server and processes incoming events and interactions.
 *
 * @author Patrick Poggi
 *
 */
public class NetworkRMIHandler extends UnicastRemoteObject implements RemoteNetworkHandler, NetworkHandler {

    String host;
    int port;
    View view;
    RemoteVirtualView virtualRMIView;
    private boolean connected;
    Timer timer;

    /**
     * Constructs a new NetworkRMIHandler.
     *
     * @param host The host IP address or name.
     * @param port The port number for the RMI connection.
     * @param view The view representing the current view.
     * @throws RemoteException If failed to export this object.
     */
    public NetworkRMIHandler(String host, int port, View view) throws RemoteException {
        super();
        this.host = host;
        this.port = port;
        this.view = view;
        connected = false;
        timer = new Timer();
    }

    /**
     * The main loop for managing RMI connection.
     * This function attempts to connect to the RMI registry, find the RMIWaiter object, and handle connection issues.
     * If the connection is successful, it will try to obtain a connection from the RMIWaiter.
     * If the view is currently reconnecting, it requests to re-establish the connection, otherwise, it requests a new connection.
     * After successfully obtaining a connection, it schedules a task that periodically sends a ping message to the remote server to check if the connection is still alive.
     * If it fails to receive a pong response within the specified timeout period, it will assume that the connection is lost and will attempt to reset the connection.
     */
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
       timer.schedule(new TimerTask() {
            @Override
            public void run() {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                Future<?> future = executor.submit(() -> {
                    try {
                        if(virtualRMIView!=null){
                            virtualRMIView.pong();
                            connected = true;
                        }else{
                            connected = false;
                        }
                    } catch (RemoteException e) {
                        if(connected){
                            connected = false;
                            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
                            try {
                                view.resetConnection();
                            }catch(IOException ex){
                                System.out.println("Error while resetting connection");
                            }
                        }
                        return;
                    }
                });
                try {
                    future.get(3, TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    if(connected){
                        connected = false;
                        System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                                "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
                        try {
                            view.resetConnection();
                            unexportObject(NetworkRMIHandler.this, true);
                            timer.cancel();
                        }catch(IOException ex){
                            System.out.println("Error while resetting connection");
                        }
                    }
                }catch (InterruptedException | ExecutionException e) {
                    System.out.println(e.getMessage());
                }
            }
        }, 5000, 3000);
    }

    /**
     * Process and dispatch the received MVEvent.
     * This function converts the received JSON to a MVEvent and passes it to the view for handling.
     *
     * @param json The received JSON string.
     * @throws RemoteException If the remote execution failed.
     */
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
    /**
     * Process and dispatch the received SelectViewEvent.
     * This function converts the received JSON to a SelectViewEvent and passes it to the view for handling.
     *
     * @param json The received JSON string.
     * @throws RemoteException If the remote execution failed.
     */
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

    /**
     * Handle the VCEvent. The event is converted to JSON and passed to the remote VirtualView.
     * If a connection error occurs, it tries to reset the connection and notifies the user about the lost connection.
     *
     * @param event VCEvent to be handled.
     * @throws NoSuchMethodException If a method requested to invoke does not exist.
     * @throws InvocationTargetException If an exception is thrown by the method invoked.
     * @throws IllegalAccessException If this Method object is enforcing Java language access control and the underlying method is inaccessible.
     */
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
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
            return;
        } catch (RemoteException e) {
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
            return;
        }
    }

    /**
     * An overload of the onVCEvent method that throws an IllegalAccessError when called.
     * This method is not intended to be used in this context.
     *
     * @param event VCEvent to be handled.
     * @param view The VirtualView associated with the event.
     * @throws NoSuchMethodException If a method requested to invoke does not exist.
     * @throws InvocationTargetException If an exception is thrown by the method invoked.
     * @throws IllegalAccessException If this Method object is enforcing Java language access control and the underlying method is inaccessible.
     */
    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessError("This method should not be called");
    }

    /**
     * Sends a ping to the remote virtual view to check the connection.
     * If the connection is lost, it tries to reset the connection and notifies the user about the lost connection.
     */
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

    /**
     * Respond to a ping request. This is used to check if the server-client connection is alive.
     *
     * @throws RemoteException If the remote execution failed.
     */
    @Override
    public void pong() throws RemoteException{
        return;
    }

    @Override
    public ConnectionInfo getConnectionInfo() throws RemoteException{
        return this.view.getConnectionInfo();
    }

}
