package Server.Network;

import Client.NetworkRMIHandler;
import Server.Events.Event;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class VirtualRMIView extends UnicastRemoteObject implements VirtualView, Remote {
    NetworkRMIHandler client;
    List<VCEventListener> vcEventListeners;
    public VirtualRMIView(NetworkRMIHandler networkHandler) throws RemoteException {
        super();
        client = networkHandler;
        vcEventListeners = new ArrayList<>();
    }

    @Override
    public void run() {
        /*boolean done = false;
        while(!done){
            try{
                client.onSelectViewEvent(new SelectViewEvent(new LoginView()));
            }catch (RemoteException e){
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println(e.getStackTrace());
            }
        }*/
        try {
            client.sendSelectViewEvent(
                    new Gson().toJson(
                            new SelectViewEvent(new LoginView())
                    )
            );
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    /*
    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String type = event.getMethodName();
        Method method = this.getClass().getDeclaredMethod(type);
        method.invoke(...);
    }
    */

    public void sendVCEvent(String json) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Gson gson = new Gson();
        Event event = gson.fromJson(json,Event.class);
        switch (event.getPrimaryType()){
            case "VCEvent":
                event = gson.fromJson(json,VCEvent.class);
                for(VCEventListener listener : vcEventListeners){
                    listener.onVCEvent((VCEvent)event,this);
                }
                break;
            case "MVEvent":
                throw new IllegalAccessException("MVEvent not allowed in this context");
            case "SelectViewEvent":
                throw new IllegalAccessException("SelectViewEvent not allowed in this context");
        }
    }


    public void onMVEvent(MVEvent event) {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        try {
            client.sendMVEvent(json);
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println(e.getStackTrace());
        }
    }


    public void onSelectViewEvent(SelectViewEvent event) {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        try {
            client.sendSelectViewEvent(json);
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println(e.getStackTrace());
        }
    }

    @Override
    public void addVCEventListener(VCEventListener listener){
        vcEventListeners.add(listener);
    }

    public void removeVCEventListener(VCEventListener listener){
        vcEventListeners.remove(listener);
    }

}
