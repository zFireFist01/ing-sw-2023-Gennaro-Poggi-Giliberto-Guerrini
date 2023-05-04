package Client;

import Client.View.RemoteNetworkHandler;
import Client.View.View;
import Server.Events.Event;
import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import Server.Network.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class NetworkRMIHandler extends UnicastRemoteObject implements RemoteNetworkHandler, NetworkHandler {

    View view;
    RemoteVirtualView virtualRMIView;

    public NetworkRMIHandler(View view) throws RemoteException {
        super();
        this.view = view;
    }

    @Override
    public void run(){
        Registry registry;
        RMIWaiterInterface rmiWaiter;
        try {
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            System.err.println("Error while getting registry");
            throw new RuntimeException(e);
        }

        try {
            rmiWaiter = (RMIWaiterInterface) registry.lookup("RMIWaiter");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            System.err.println("Failed to bind with RMIWaiter (RMIWaiter not bound)");
            throw new RuntimeException(e);
        }

        try {
            virtualRMIView = rmiWaiter.giveConnection(this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void receiveMVEvent(String json) throws RemoteException{
        /*Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardAdapter())
                .create();*/
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
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessError("This method should not be called");
    }

}
