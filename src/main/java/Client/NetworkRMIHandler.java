package Client;

import Client.View.View;
import Server.Events.Event;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.RMIWaiter;
import Server.Network.VirtualRMIView;
import Server.Network.VirtualView;
import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NetworkRMIHandler extends UnicastRemoteObject implements NetworkHandler, Remote {

    View view;
    VirtualRMIView virtualRMIView;

    public NetworkRMIHandler(View view) throws RemoteException {
        super();
        this.view = view;
    }

    @Override
    public void run() {
        Registry registry;
        RMIWaiter rmiWaiter;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            System.err.println("Error while getting registry");
            throw new RuntimeException(e);
        }

        try {
            rmiWaiter = (RMIWaiter) registry.lookup("RMIWaiter");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            System.err.println("Failed to bind with RMIWaiter (RMIWaiter not bound)");
            throw new RuntimeException(e);
        }

        try {
            virtualRMIView = (VirtualRMIView) rmiWaiter.giveConnection(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void sendMVEvent(String json) throws RemoteException{
        Gson gson = new Gson();
        Event event = gson.fromJson(json, Event.class);
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
    public void sendSelectViewEvent(String json) throws RemoteException{
        Gson gson = new Gson();
        Event event = gson.fromJson(json, Event.class);
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
        virtualRMIView.sendVCEvent(json);
    }

    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessError("This method should not be called");
    }

}
