package Client;

import Client.View.CLI.View;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.RMIWaiter;
import Server.Network.VirtualRMIView;
import Server.Network.VirtualView;
import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        MVEvent event = gson.fromJson(json, MVEvent.class);
        view.onMVEvent(event);
    }

    @Override
    public void sendSelectViewEvent(String json) throws RemoteException{
        Gson gson = new Gson();
        SelectViewEvent event = gson.fromJson(json, SelectViewEvent.class);
        view.onSelectViewEvent(event);
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
